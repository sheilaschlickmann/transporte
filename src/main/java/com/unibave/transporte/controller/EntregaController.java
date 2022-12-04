package com.unibave.transporte.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.unibave.transporte.controller.converter.MapConverter;
import com.unibave.transporte.dtos.Location;
import com.unibave.transporte.dtos.Response;
import com.unibave.transporte.dtos.ResponseDistance;
import com.unibave.transporte.entity.Entregas;
import com.unibave.transporte.entity.Frete;
import com.unibave.transporte.enums.Status;
import com.unibave.transporte.service.EntregaService;
import com.unibave.transporte.service.EntregadorService;
import com.unibave.transporte.service.FreteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.net.URI;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/entregas")
public class EntregaController {

    private static final String API_KEY = "AIzaSyA408XQGcwvcyZQ0DLZEPsWkZhDtcczTFE";
    private static final String BASE_URI = "https://maps.googleapis.com/maps/api";
    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private MapConverter mapConverter;

    final EntregaService entregaService;

    final FreteService freteService;

    final EntregadorService entregadorService;

    public EntregaController(EntregaService entregaService, FreteService freteService, EntregadorService entregadorService) {
        this.entregaService = entregaService;
        this.freteService = freteService;
        this.entregadorService = entregadorService;
    }



    @PostMapping
    @Transactional
    public ResponseEntity<?> inserir(@RequestBody Map<String, Object> entregas/*,@RequestParam String address_origin, String address_destiny, Integer id_frete, Integer id_entregador*/){
        Entregas entregaConvertida = mapper.convertValue(entregas, Entregas.class);

        if (entregaService.existsByDsCarga(entregaConvertida.getDsCarga())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: carga ja existe");
        }

            ResponseEntity<Response> responseOrigin = new RestTemplate().getForEntity(BASE_URI + "/geocode/json?address=" + entregaConvertida.getCepOrigem() + "&key=" + API_KEY,
                    Response.class);

            Location coordinatesOrigin = responseOrigin.getBody().getResult()[0].getGeometry().getLocation();

            double latOrigin = coordinatesOrigin.getLat();
            double lngOrigin = coordinatesOrigin.getLng();

            ResponseEntity<Response> responseDestiny = new RestTemplate().getForEntity(BASE_URI + "/geocode/json?address=" + entregaConvertida.getCepDestino() + "&key=" + API_KEY,
                    Response.class);

            Location coordinatesDestiny = responseDestiny.getBody().getResult()[0].getGeometry().getLocation();

            double latDestiny = coordinatesDestiny.getLat();
            double lngDestiny = coordinatesDestiny.getLng();

            String originAddress = latOrigin + "," + lngOrigin;
            String destinationAddress = latDestiny + "," + lngDestiny;

            ResponseEntity<ResponseDistance> responseDistance = new RestTemplate().getForEntity(BASE_URI + "/distancematrix/json?origins=" + originAddress + "&destinations=" + destinationAddress +"&key=" + API_KEY,
                    ResponseDistance.class);

            int value = responseDistance.getBody().getRows()[0].getElements()[0].getDistance().getValue();

            Frete tabela_Frete = freteService.findById(entregaConvertida.getIdTabelaFrete());

            double valor_frete = (value * tabela_Frete.getValorKm()) + tabela_Frete.getTaxaAdministracao();

            entregaConvertida.setIdEntregador(entregaConvertida.getIdEntregador());
            entregaConvertida.setIdTabelaFrete(entregaConvertida.getIdTabelaFrete());
            entregaConvertida.setStatus(Status.C);

            entregaConvertida.setDistancia(value);

            entregaConvertida.setTotal_frete(valor_frete);

            Entregas entregaSalva = entregaService.save(entregaConvertida);
            return  ResponseEntity.created(URI.create("/entregas/id/" + entregaSalva.getId())).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable("id") Integer id) {
        Optional<?> entregasOptional =  entregaService.findById(id);
        if (!entregasOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Entrega não cadastrada!");
        }
        return ResponseEntity.ok(mapConverter.toJsonMap(entregaService.findById(id)));
    }

    @GetMapping("/entregador/{id_entregador}")
    public ResponseEntity<?> buscarPorIdEntregador(@PathVariable("id_entregador") Integer id_entregador) {
        Optional<?> entregadorOptional =  entregadorService.findById(id_entregador);
        if (!entregadorOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Entregador não cadastrado!");
        }
        return ResponseEntity.ok(mapConverter.toJsonMap(entregadorService.findById(id_entregador)));
    }

    @GetMapping
    public ResponseEntity<?> listarTodas() {
        return ResponseEntity.ok(mapConverter.toJsonList(entregaService.findAll()));
    }



}

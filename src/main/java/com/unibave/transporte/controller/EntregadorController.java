package com.unibave.transporte.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.unibave.transporte.controller.converter.MapConverter;
import com.unibave.transporte.dtos.Location;
import com.unibave.transporte.dtos.Response;
import com.unibave.transporte.dtos.ResponseDistance;
import com.unibave.transporte.entity.Entregador;
import com.unibave.transporte.entity.Entregas;
import com.unibave.transporte.entity.Frete;
import com.unibave.transporte.service.EntregadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.net.URI;
import java.util.Map;
import java.util.Optional;
@RestController
@RequestMapping("/entregador")
public class EntregadorController  {

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private MapConverter mapConverter;

    final EntregadorService entregadorService;

    public EntregadorController(EntregadorService entregadorService) {
        this.entregadorService = entregadorService;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    @Transactional
    public ResponseEntity<?> inserir(@RequestBody Map<String, Object> entregador){
        Entregador entregadorConvertido = mapper.convertValue(entregador, Entregador.class);

        Entregador entregadorSalvo = entregadorService.save(entregadorConvertido);
        return  ResponseEntity.created(URI.create("/entregador/id/" + entregadorSalvo.getId())).build();
        // throw new ConverterException("A Entrega enviada para inserção não existe!");
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable("id") Integer id) {
        Optional<?> entregadorOptional =  entregadorService.findById(id);
        if (!entregadorOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Entregador não cadastrado!");
        }
        return ResponseEntity.ok(mapConverter.toJsonMap(entregadorService.findById(id)));
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping
    public ResponseEntity<?> listarTodas() {
        return ResponseEntity.ok(mapConverter.toJsonList(entregadorService.findAll()));
    }

}

package com.unibave.transporte.controller;

import com.unibave.transporte.dtos.Entregas;
import com.unibave.transporte.dtos.Location;
import com.unibave.transporte.dtos.Response;
import com.unibave.transporte.dtos.ResponseDistance;
import com.unibave.transporte.entity.Frete;
import com.unibave.transporte.service.EntregaService;
import com.unibave.transporte.service.FreteService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/custos/frete")
public class CustoController {

        private static final String API_KEY = "AIzaSyA408XQGcwvcyZQ0DLZEPsWkZhDtcczTFE";
        private static final String BASE_URI = "https://maps.googleapis.com/maps/api";

        final FreteService freteService;
        public CustoController(FreteService freteService) {
                this.freteService = freteService;
        }

        @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
        @GetMapping
        public Entregas getGeoCode(@RequestParam String address_origin, String address_destiny, Integer id_frete) {
                ResponseEntity<Response> responseOrigin = new RestTemplate().getForEntity(BASE_URI + "/geocode/json?address=" + address_origin + "&key=" + API_KEY,
                        Response.class);

                Location coordinatesOrigin = responseOrigin.getBody().getResult()[0].getGeometry().getLocation();

                double latOrigin = coordinatesOrigin.getLat();
                double lngOrigin = coordinatesOrigin.getLng();

                ResponseEntity<Response> responseDestiny = new RestTemplate().getForEntity(BASE_URI + "/geocode/json?address=" + address_destiny + "&key=" + API_KEY,
                        Response.class);

                Location coordinatesDestiny = responseDestiny.getBody().getResult()[0].getGeometry().getLocation();

                double latDestiny = coordinatesDestiny.getLat();
                double lngDestiny = coordinatesDestiny.getLng();

                String originAddress = latOrigin + "," + lngOrigin;
                String destinationAddress = latDestiny + "," + lngDestiny;


                ResponseEntity<ResponseDistance> responseDistance = new RestTemplate().getForEntity(BASE_URI + "/distancematrix/json?origins=" + originAddress + "&destinations=" + destinationAddress +"&key=" + API_KEY,
                        ResponseDistance.class);

                int value = responseDistance.getBody().getRows()[0].getElements()[0].getDistance().getValue();

                Frete tabela_Frete = freteService.findById(id_frete);

                double valor_frete = (value * tabela_Frete.getValorKm()) + tabela_Frete.getTaxaAdministracao();

                String rua_origem = responseOrigin.getBody().getResult()[0].getAddress_components()[1].getLong_name();

                String bairro_origem = responseOrigin.getBody().getResult()[0].getAddress_components()[2].getLong_name();

                String cidade_origem = responseOrigin.getBody().getResult()[0].getAddress_components()[3].getLong_name();

                String estado_origem = responseOrigin.getBody().getResult()[0].getAddress_components()[4].getShort_name();

                String pais_origem = responseOrigin.getBody().getResult()[0].getAddress_components()[5].getShort_name();


                String rua_destino = responseDestiny.getBody().getResult()[0].getAddress_components()[1].getLong_name();

                String bairro_destino = responseDestiny.getBody().getResult()[0].getAddress_components()[2].getLong_name();

                String cidade_destino = responseDestiny.getBody().getResult()[0].getAddress_components()[3].getLong_name();

                String estado_destino = responseDestiny.getBody().getResult()[0].getAddress_components()[4].getShort_name();

                String pais_destino = responseDestiny.getBody().getResult()[0].getAddress_components()[5].getShort_name();

                Entregas entregas = new Entregas();

                entregas.setCep_origem(address_origin);
                entregas.setLogradouro_origem(rua_origem);
                entregas.setBairro_origem(bairro_origem);
                entregas.setCidade_origem(cidade_origem);
                entregas.setUf_origem(estado_origem);
                entregas.setPais_origem(pais_origem);

                entregas.setCep_destino(address_destiny);
                entregas.setLogradouro_destino(rua_destino);
                entregas.setBairro_destino(bairro_destino);
                entregas.setCidade_destino(cidade_destino);
                entregas.setUf_destino(estado_destino);
                entregas.setPais_destino(pais_destino);
                entregas.setDistancia_destino(value);
                entregas.setValor_km(tabela_Frete.getValorKm());
                entregas.setTaxa_administracao(tabela_Frete.getTaxaAdministracao());
                entregas.setValor_frete(valor_frete);

               return  entregas;

        }
}



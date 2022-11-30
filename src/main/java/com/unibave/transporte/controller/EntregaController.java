package com.unibave.transporte.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.unibave.transporte.controller.converter.MapConverter;
import com.unibave.transporte.entity.Entregas;
import com.unibave.transporte.exception.ConverterException;
import com.unibave.transporte.service.EntregaService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/entregas")
public class EntregaController {

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private MapConverter mapConverter;

    final EntregaService entregaService;

    public EntregaController(EntregaService entregaService) {
        this.entregaService = entregaService;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> inserir(@RequestBody Map<String, Object> entregas){

        if (entregas != null) {
            Entregas entregaConvertida = mapper.convertValue(entregas, Entregas.class);
            Entregas entregaSalva = entregaService.save(entregaConvertida);
            return  ResponseEntity.created(URI.create("/entregas/id/" + entregaSalva.getId())).build();
        }
        throw new ConverterException("A Entrega enviada para inserção não existe!");
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> buscar(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(mapConverter.toJsonMap(entregaService.findById(id)));
    }

    @GetMapping
    public ResponseEntity<?> listarTodas() {
        return ResponseEntity.ok(mapConverter.toJsonList(entregaService.findAll()));
    }



}

package com.unibave.transporte.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.unibave.transporte.controller.converter.MapConverter;
import com.unibave.transporte.service.EntregadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;

@RestController
@RequestMapping("/entregador")
public class EntregadorController implements Serializable {
    private static final long serialVersionUID = 1L;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private MapConverter mapConverter;

    @Autowired
    @Qualifier("entregadorServiceProxy")
    private EntregadorService service;

    @GetMapping("/id/{id}")
    public ResponseEntity<?> buscar(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(mapConverter.toJsonMap((service.buscarPor(id))));
    }

    @GetMapping
    public ResponseEntity<?> listarTodas() {
        return ResponseEntity.ok(mapConverter.toJsonList(service.listarTodas()));
    }

}

package com.unibave.transporte.controller;

import com.unibave.transporte.entity.Entregas;
import com.unibave.transporte.service.EntregaService;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/entregas")
public class EntregaController {

    final EntregaService entregaService;

    public EntregaController(EntregaService entregaService) {
        this.entregaService = entregaService;
    }

    @Transactional
    @PostMapping
    public ResponseEntity<Object> saveParkingSpot(@RequestBody @Valid Entregas entregas) {
        return ResponseEntity.status(HttpStatus.CREATED).body(entregaService.save(entregas));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getEntregas(@PathVariable(value = "id") Integer id) {
        Optional<Entregas> entregasOptional = entregaService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(entregasOptional.get());
    }



}

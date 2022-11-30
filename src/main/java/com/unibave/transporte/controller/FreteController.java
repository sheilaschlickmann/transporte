package com.unibave.transporte.controller;

import com.unibave.transporte.entity.Frete;
import com.unibave.transporte.service.FreteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/tabelas")
public class FreteController {

    final FreteService freteService;

    public FreteController(FreteService freteService) {
        this.freteService = freteService;
    }

    @Transactional
    @PostMapping
    public ResponseEntity<Object> saveParkingSpot(@RequestBody @Valid Frete fretes) {
        return ResponseEntity.status(HttpStatus.CREATED).body(freteService.save(fretes));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getFrete(@PathVariable(value = "id") Integer id) {
        Optional<Frete> fretesOptional = freteService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(fretesOptional.get());
    }

    @DeleteMapping("/id/{id}")
    @Transactional
    public ResponseEntity<?> delete(@PathVariable("id") Integer id){
        Optional<Frete> fretesOptional = freteService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(fretesOptional.get());
    }
}

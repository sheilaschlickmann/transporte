package com.unibave.transporte.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.unibave.transporte.controller.converter.MapConverter;
import com.unibave.transporte.entity.Frete;
import com.unibave.transporte.exception.ConverterException;
import com.unibave.transporte.exception.RegistroNaoEncontradoException;
import com.unibave.transporte.service.FreteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/tabelas")
public class FreteController {

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private MapConverter mapConverter;
    final FreteService freteService;

    public FreteController(FreteService freteService) {
        this.freteService = freteService;
    }

    @PostMapping
    public ResponseEntity<Object> saveParkingSpot(@RequestBody @Valid Frete fretes) {
        if (fretes != null) {
            Frete freteConvertida = mapper.convertValue(fretes, Frete.class);
            Frete freteSalva = freteService.save(freteConvertida);
            return  ResponseEntity.created(URI.create("/tabelas/id/" + freteSalva.getId())).build();
        }
        throw new ConverterException("Não foi possivel criar uma tabela de frete!");
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getFrete(@PathVariable(value = "id") Integer id) {
        return ResponseEntity.ok(mapConverter.toJsonMap(freteService.findById(id)));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> delete(@PathVariable("id") Integer id){
        Optional<Frete> freteDeletado = freteService.findById(id);
        if (freteDeletado != null) {
            freteService.delete(id);
            return ResponseEntity.status(HttpStatus.OK).body(freteDeletado.get());
        }
        throw new RegistroNaoEncontradoException("Registro não encontrado para exclusão!");
    }
}

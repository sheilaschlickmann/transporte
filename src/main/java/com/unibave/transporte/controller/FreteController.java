package com.unibave.transporte.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.unibave.transporte.controller.converter.MapConverter;
import com.unibave.transporte.entity.Frete;
import com.unibave.transporte.exception.ConverterException;
import com.unibave.transporte.service.FreteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.Map;
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
    public ResponseEntity<?> saveFrete(@RequestBody Map<String, Object> freteMap) {
        if (freteMap != null) {
            Frete freteConvertida = mapper.convertValue(freteMap, Frete.class);
            Frete freteSalva = freteService.save(freteConvertida);
            return  ResponseEntity.status(HttpStatus.OK).body("Tabela criada com sucesso! ID: "+freteSalva.getId()+
                                                              ", Descrição: "+freteSalva.getDesc_short()+
                                                              ", Valor do KM: "+freteSalva.getValor_km()+
                                                              ", Taxa de Administração: "+freteSalva.getTaxa_administracao());
        }
        throw new ConverterException("Não foi possivel criar uma tabela de frete!");
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getFrete(@PathVariable(value = "id") Integer id) {
        return ResponseEntity.ok(mapConverter.toJsonMap(freteService.findById(id)));
    }

    @GetMapping("/descricao/{description}")
    public ResponseEntity<?> searchByDescrip(@PathVariable(value = "description") String description){
        return ResponseEntity.ok(mapConverter.toJsonList(freteService.searchByDescrip(description)));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> delete(@PathVariable(value = "id") Integer id){
        Frete freteDeletado = freteService.findById(id);
        freteService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body("Tabela removida com sucesso!");
    }
}

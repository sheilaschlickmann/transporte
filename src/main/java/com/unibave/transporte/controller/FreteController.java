package com.unibave.transporte.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.unibave.transporte.controller.converter.MapConverter;
import com.unibave.transporte.entity.Frete;
import com.unibave.transporte.exception.ConverterException;
import com.unibave.transporte.service.FreteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.Map;


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

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<?> saveFrete(@RequestBody Map<String, Object> freteMap) {
        if (freteMap != null) {
            Frete freteConvertido = mapper.convertValue(freteMap, Frete.class);

            if (freteService.existsBydescShort(freteConvertido.getDescShort())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("CONFLITO! Já existe tabela com esssa descrição!");
            }

            if (freteConvertido.getTaxaAdministracao() > freteConvertido.getValorKm() * 2) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("O valor da taxa Administrativa não pode ser o dobro do valor do KM!");
            }

            Frete freteSalva = freteService.save(freteConvertido);
            return  ResponseEntity.status(HttpStatus.OK).body("Tabela criada com sucesso! ID: "+freteSalva.getId()+
                                                              ", Descrição: "+freteSalva.getDescShort()+
                                                              ", Valor do KM: "+freteSalva.getValorKm()+
                                                              ", Taxa de Administração: "+freteSalva.getTaxaAdministracao());

        }
        throw new ConverterException("Não foi possivel criar uma tabela de frete!");
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getFrete(@PathVariable(value = "id") Integer id) {
        return ResponseEntity.ok(mapConverter.toJsonMap(freteService.findById(id)));
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping("/descricao/{description}")
    public ResponseEntity<?> searchByDescrip(@PathVariable(value = "description") String description){
        return ResponseEntity.ok(mapConverter.toJsonList(freteService.searchByDescrip(description)));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> delete(@PathVariable(value = "id") Integer id){
        Frete freteDeletado = freteService.findById(id);
        freteService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body("Tabela removida com sucesso!");
    }
}

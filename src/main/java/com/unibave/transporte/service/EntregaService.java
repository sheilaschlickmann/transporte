package com.unibave.transporte.service;

import com.unibave.transporte.entity.Entregas;
import com.unibave.transporte.repository.EntregaRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.constraints.Positive;
import java.util.List;
import java.util.Optional;

@Service
public class EntregaService {

    final EntregaRepository entregaRepository;

    public EntregaService(EntregaRepository entregaRepository) {
        this.entregaRepository = entregaRepository;
    }

    public Optional<Entregas> findById(Integer id) {
        return entregaRepository.findById(id);
    }

    @Transactional
    public Entregas save(Entregas entregas) {
        return entregaRepository.save(entregas);
    }

    public List<Entregas> findAll() {
        return entregaRepository.findAll();
    }

    public boolean existsByDsCarga(String ds_carga) {
        return entregaRepository.existsByDsCarga(ds_carga);
    }




}

package com.unibave.transporte.service;

import com.unibave.transporte.entity.Entregador;
import com.unibave.transporte.entity.Entregas;
import com.unibave.transporte.repository.EntregadorRepository;
import org.springframework.stereotype.Service;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;


import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class EntregadorService {

    final EntregadorRepository entregadorRepository;

    public EntregadorService(EntregadorRepository entregadorRepository) {
        this.entregadorRepository = entregadorRepository;
    }
    public Optional<Entregador> findById(Integer id) {
        return entregadorRepository.findById(id);
    }

    @Transactional
    public Entregador save(Entregador entregador) {
        return entregadorRepository.save(entregador);
    }

    public List<Entregador> findAll() {
        return entregadorRepository.findAll();
    }
}

package com.unibave.transporte.service;

import com.unibave.transporte.entity.Frete;
import com.unibave.transporte.repository.FreteRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class FreteService {

    final FreteRepository freteRepository;


    public FreteService(FreteRepository freteRepository) {
        this.freteRepository = freteRepository;
    }

    public Optional<Frete> findById(Integer id) {
        return freteRepository.findById(id);
    }

    @Transactional
    public Frete save(Frete fretes) {
        return freteRepository.save(fretes);
    }

    @Transactional
    public Frete delete(Integer id) {
        Frete fretes = freteRepository.findById(id).get();
        freteRepository.deleteById(id);
        return fretes;
    }


}




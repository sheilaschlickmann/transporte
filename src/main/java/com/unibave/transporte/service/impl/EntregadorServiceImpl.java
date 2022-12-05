package com.unibave.transporte.service.impl;

import com.unibave.transporte.exception.RegistroNaoEncontradoException;
import com.unibave.transporte.entity.Entregador;
import com.unibave.transporte.repository.EntregadorRepository;
import com.unibave.transporte.service.EntregadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Qualifier("entregadorServiceImpl")
public class EntregadorServiceImpl implements EntregadorService {

    @Autowired
    private EntregadorRepository repository;

    @Override
    public Entregador buscarPor(Integer id) {
        Entregador bandeira = repository.buscarPor(id);
        if (bandeira != null) {
            return bandeira;
        }
        throw new RegistroNaoEncontradoException("Bandeira não encontrada com o id informado!");
    }

    @Override
    public List<Entregador> listarTodas() {
        return repository.listarTodas();
    }

}

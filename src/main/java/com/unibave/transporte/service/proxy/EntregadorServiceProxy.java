package com.unibave.transporte.service.proxy;

import com.unibave.transporte.entity.Entregador;
import com.unibave.transporte.service.EntregadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Qualifier("entregadorServiceProxy")
public class EntregadorServiceProxy implements EntregadorService {

    @Autowired
    @Qualifier("entregadorServiceImpl")
    private EntregadorService service;

    @Override
    public Entregador buscarPor(Integer id) {
        return service.buscarPor(id);
    }

    @Override
    public List<Entregador> listarTodas() {
        return service.listarTodas();
    }
}

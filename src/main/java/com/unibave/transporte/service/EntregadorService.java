package com.unibave.transporte.service;

import com.unibave.transporte.entity.Entregador;

import javax.validation.constraints.Positive;
import java.util.List;

public interface EntregadorService {
    Entregador buscarPor(
            @Positive(message = "Não pode ser menos que 0!")
            Integer id);

    List<Entregador> listarTodas();
}

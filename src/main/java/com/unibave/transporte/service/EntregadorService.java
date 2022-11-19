package com.unibave.transporte.service;

import com.unibave.transporte.entity.Entregador;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Positive;
import java.util.List;

@Service
@Validated
public interface EntregadorService {
    Entregador buscarPor(
            @Positive(message = "Não pode ser menos que 0!")
            Integer id);

    List<Entregador> listarTodas();
}

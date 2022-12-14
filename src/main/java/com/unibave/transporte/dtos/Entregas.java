package com.unibave.transporte.dtos;

import com.unibave.transporte.enums.Status;
import lombok.Data;

@Data
public class Entregas {

    private String cep_origem;

    private String cep_destino;

    private String logradouro_origem;

    private String logradouro_destino;

    private String bairro_origem;

    private String bairro_destino;

    private String cidade_origem;

    private String cidade_destino;

    private String uf_origem;

    private String pais_origem;

    private String pais_destino;

    private String uf_destino;

    private double distancia_destino;

    private double valor_km ;

    private double taxa_administracao;

    private double valor_frete;

}

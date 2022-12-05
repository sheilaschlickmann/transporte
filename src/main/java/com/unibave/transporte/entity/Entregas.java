package com.unibave.transporte.entity;

import com.unibave.transporte.enums.Status;
import lombok.Data;


import javax.persistence.*;
import javax.validation.constraints.NotEmpty;


@Data
@Entity
@Table(name = "entregas")
public class Entregas {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "ds_carga")
    @NotEmpty(message = "A descrição da Carga é obrigatória")
    private String dsCarga;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;


    @Column(name = "cep_origem")
    @NotEmpty(message = "O CEP de origem é obrigatório")
    private String cepOrigem;


    @Column(name = "cep_destino")
    @NotEmpty(message = "O CEP de destino é obrigatório")
    private String cepDestino;

    @Column(name = "distancia")
    private double distancia;

    @Column(name = "total_frete")
    private double total_frete;

    @Column(name = "id_entregador")
    //@NotEmpty(message = "O id do entregador é obrigatório")
    private Integer idEntregador;

    @Column(name = "id_tabela_frete")
    //@NotEmpty(message = "O id da tabela de frete é obrigatório")
    private Integer idTabelaFrete;

}

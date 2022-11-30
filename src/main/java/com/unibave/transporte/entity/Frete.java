package com.unibave.transporte.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NegativeOrZero;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;


@Entity
@Table(name = "frete")
@JsonIgnoreProperties(ignoreUnknown = true, value = {"hibernateLazyInitializer", "handler"})
@ToString
public class Frete implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "desc_short")
    @NotEmpty(message = "O Descrição da tabela é obrigatório!")
    @Size(max = 50, message = "O tamanho do nome da bandeira não pode passar de 50 caracteres!")
    private String desc_short;

    @Column(name = "valor_km")
    @NotEmpty(message = "O valor do km percorrido é obrigatório!")
    @NegativeOrZero(message = "O valor não pode ser do km percorrido não pode ser zero ou negativo!")
    private double valor_km;

    @Column(name = "taxa_administracao")
    @NotEmpty(message = "O valor da Taxa de Administração é obrigatório!")
    @NegativeOrZero(message = "O valor não pode ser da Taxa de Administração não pode ser zero ou negativo!")
    private double taxa_administracao;

}

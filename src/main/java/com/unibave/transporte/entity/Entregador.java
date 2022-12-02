package com.unibave.transporte.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import javax.persistence.*;

@Entity
@Table(name = "entregador")
@JsonIgnoreProperties(ignoreUnknown = true, value = {"hibernateLazyInitializer", "handler"})
@Data
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

public class Entregador {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @EqualsAndHashCode.Include
    private Integer id;

    @Column(name = "nome")
    @NotEmpty(message = "O nome da bandeira é obrigatório")
    @Size(max = 100, message = "O tamanho do nome da bandeira não pode passar de 100 caracteres")
    private String nome;



}

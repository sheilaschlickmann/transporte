package com.unibave.transporte.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.List;


@Entity
@Table(name = "frete")
@JsonIgnoreProperties(ignoreUnknown = true, value = {"hibernateLazyInitializer", "handler"})
@ToString
@Data
@Validated
public class Frete implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "desc_short")
    @NotEmpty(message = "O Descrição da tabela é obrigatório!")
    @Size(max = 50, message = "O tamanho do nome da bandeira não pode passar de 50 caracteres!")
    private String descShort;

    @Column(name = "valor_km")
    @NotNull(message = "O valor do km percorrido é obrigatório!")
    @Positive(message = "O valor não pode ser do km percorrido não pode ser zero ou negativo!")
    private double valorKm;

    @Column(name = "taxa_administracao")
    @NotNull(message = "O valor da Taxa de Administração é obrigatório!")
    @Positive(message = "O valor não pode ser da Taxa de Administração não pode ser zero ou negativo!")
    private double taxaAdministracao;



    /*@OneToMany
    private List<Entregas> entregas;*/

}

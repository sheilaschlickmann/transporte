package com.unibave.transporte.enums;

import lombok.Data;
import lombok.Getter;

@Getter
public enum Status {

    C("Coletado"),
    E("Entregue");
    private String descricao;

    Status(String descricao) {
        this.descricao = descricao;
    }
}

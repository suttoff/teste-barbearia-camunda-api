package com.example.workflow.domain;


public enum StatusEnum {

    A("Active"),
    C("Canceled"),
    E("Error"),
    P("Pending");

    final String description;

    StatusEnum(String descricao) {
        this.description = descricao;
    }

    public String getDescricao() {
        return description;
    }
}

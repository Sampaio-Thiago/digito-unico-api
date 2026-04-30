package com.inter.digitounico.dto;

import jakarta.validation.constraints.NotBlank;

public class ChavePublicaRequest {

    @NotBlank(message = "Chave pública é obrigatória")
    private String chavePublica;

    public ChavePublicaRequest() {}

    public ChavePublicaRequest(String chavePublica) {
        this.chavePublica = chavePublica;
    }

    public String getChavePublica() {
        return chavePublica;
    }

    public void setChavePublica(String chavePublica) {
        this.chavePublica = chavePublica;
    }
}

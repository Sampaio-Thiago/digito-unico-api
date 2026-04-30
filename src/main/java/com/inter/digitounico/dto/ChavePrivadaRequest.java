package com.inter.digitounico.dto;

import jakarta.validation.constraints.NotBlank;

public class ChavePrivadaRequest {

    @NotBlank(message = "Chave privada é obrigatória")
    private String chavePrivada;

    public ChavePrivadaRequest() {}

    public ChavePrivadaRequest(String chavePrivada) {
        this.chavePrivada = chavePrivada;
    }

    public String getChavePrivada() {
        return chavePrivada;
    }

    public void setChavePrivada(String chavePrivada) {
        this.chavePrivada = chavePrivada;
    }
}

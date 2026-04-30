package com.inter.digitounico.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class CalculoRequest {

    @NotBlank(message = "Parâmetro n é obrigatório")
    @Pattern(regexp = "\\d+", message = "Parâmetro n deve conter apenas dígitos")
    private String n;

    @NotNull(message = "Parâmetro k é obrigatório")
    @Min(value = 1, message = "Parâmetro k deve ser maior que 0")
    private Integer k;

    private Long usuarioId;

    public CalculoRequest() {}

    public CalculoRequest(String n, Integer k, Long usuarioId) {
        this.n = n;
        this.k = k;
        this.usuarioId = usuarioId;
    }

    // Getters and Setters
    public String getN() {
        return n;
    }

    public void setN(String n) {
        this.n = n;
    }

    public Integer getK() {
        return k;
    }

    public void setK(Integer k) {
        this.k = k;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }
}

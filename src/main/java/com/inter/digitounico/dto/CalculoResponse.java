package com.inter.digitounico.dto;

import java.time.LocalDateTime;

public class CalculoResponse {

    private String n;
    private Integer k;
    private Integer digitoUnico;
    private LocalDateTime dataCalculo;
    private boolean fromCache;

    public CalculoResponse() {}

    public CalculoResponse(String n, Integer k, Integer digitoUnico, LocalDateTime dataCalculo, boolean fromCache) {
        this.n = n;
        this.k = k;
        this.digitoUnico = digitoUnico;
        this.dataCalculo = dataCalculo;
        this.fromCache = fromCache;
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

    public Integer getDigitoUnico() {
        return digitoUnico;
    }

    public void setDigitoUnico(Integer digitoUnico) {
        this.digitoUnico = digitoUnico;
    }

    public LocalDateTime getDataCalculo() {
        return dataCalculo;
    }

    public void setDataCalculo(LocalDateTime dataCalculo) {
        this.dataCalculo = dataCalculo;
    }

    public boolean isFromCache() {
        return fromCache;
    }

    public void setFromCache(boolean fromCache) {
        this.fromCache = fromCache;
    }
}

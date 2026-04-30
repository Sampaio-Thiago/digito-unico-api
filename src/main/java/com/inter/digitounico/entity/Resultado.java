package com.inter.digitounico.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "resultados")
public class Resultado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String n;

    @Column(nullable = false)
    private Integer k;

    @Column(nullable = false)
    private Integer digitoUnico;

    @Column(nullable = false)
    private LocalDateTime dataCalculo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    public Resultado() {
        this.dataCalculo = LocalDateTime.now();
    }

    public Resultado(String n, Integer k, Integer digitoUnico, Usuario usuario) {
        this.n = n;
        this.k = k;
        this.digitoUnico = digitoUnico;
        this.usuario = usuario;
        this.dataCalculo = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}

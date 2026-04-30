package com.inter.digitounico.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome_criptografado", columnDefinition = "TEXT")
    private String nomeCriptografado;

    @Column(name = "email_criptografado", columnDefinition = "TEXT")
    private String emailCriptografado;

    @Column(name = "chave_publica", columnDefinition = "TEXT")
    private String chavePublica;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Resultado> resultados = new ArrayList<>();

    public Usuario() {}

    public Usuario(String nomeCriptografado, String emailCriptografado, String chavePublica) {
        this.nomeCriptografado = nomeCriptografado;
        this.emailCriptografado = emailCriptografado;
        this.chavePublica = chavePublica;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeCriptografado() {
        return nomeCriptografado;
    }

    public void setNomeCriptografado(String nomeCriptografado) {
        this.nomeCriptografado = nomeCriptografado;
    }

    public String getEmailCriptografado() {
        return emailCriptografado;
    }

    public void setEmailCriptografado(String emailCriptografado) {
        this.emailCriptografado = emailCriptografado;
    }

    public String getChavePublica() {
        return chavePublica;
    }

    public void setChavePublica(String chavePublica) {
        this.chavePublica = chavePublica;
    }

    public List<Resultado> getResultados() {
        return resultados;
    }

    public void setResultados(List<Resultado> resultados) {
        this.resultados = resultados;
    }
}

package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "colecoes")
@Inheritance(strategy= InheritanceType.JOINED)
public class Colecao {
    @Id
    @GeneratedValue
    @JsonProperty
    private Integer id;

    @Column(length=1024)
    @JsonProperty
    private String titulo;

    @OneToMany(mappedBy="colecao")
    @JsonIgnore
    private Set<Recurso> recursos = new HashSet<>();

    public Colecao() {}

    public Colecao(Integer id, String titulo) {
        this.id = id;
        this.titulo = titulo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Set<Recurso> getRecursos() {
        return recursos;
    }

    public void setRecursos(Set<Recurso> recursos) {
        this.recursos = recursos;
    }

    public void appendRecursos(Recurso recurso) {
        this.recursos.add(recurso);
    }
}

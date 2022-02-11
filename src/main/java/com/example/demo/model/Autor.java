package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "autores")
public class Autor {

    @Id
    @GeneratedValue
    @JsonProperty
    private Integer id;

    @JsonProperty
    private String email;

    @Column(length=64)
    @JsonProperty
    private String nome;

    @Column(length=64)
    @JsonProperty
    private String sobrenome;

    @Column(length=256)
    @JsonProperty
    private String afiliacao;

    @JsonProperty
    private String orcid;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name="autores_recursos",
            joinColumns=@JoinColumn(name="autor_id"),
            inverseJoinColumns=@JoinColumn(name="recurso_id"))
    private Set<Recurso> recursos = new HashSet<>();

    public Autor() {}

    public Autor(Integer id, String email, String nome, String sobrenome, String afiliacao, String orcid, Set<Recurso> recurso) {
        this.id = id;
        this.email = email;
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.afiliacao = afiliacao;
        this.orcid = orcid;
        this.recursos = recurso;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getAfiliacao() {
        return afiliacao;
    }

    public void setAfiliacao(String afiliacao) {
        this.afiliacao = afiliacao;
    }

    public String getOrcid() {
        return orcid;
    }

    public void setOrcid(String orcid) {
        this.orcid = orcid;
    }

    public Set<Recurso> getRecurso() {
        return recursos;
    }

    public void setRecurso(Set<Recurso> recursos) {
        this.recursos = recursos;
    }

    public void appendRecurso(Recurso recurso) {
        this.recursos.add(recurso);
    }
}
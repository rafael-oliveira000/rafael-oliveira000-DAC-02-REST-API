package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "eventos")
@PrimaryKeyJoinColumn(name="id")
public class Evento extends Colecao {
    @Id
    @GeneratedValue
    private Integer id;
    @JsonProperty("data_de_inicio")
    private Date dataDeInicio;
    @JsonProperty("data_de_fim")
    private Date dataDeFim;

    public Evento() {}

    public Evento(Integer id, Date dataDeInicio, Date dataDeFim) {
        this.id = id;
        this.dataDeInicio = dataDeInicio;
        this.dataDeFim = dataDeFim;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDataDeInicio() {
        return dataDeInicio;
    }

    public void setDataDeInicio(Date dataDeInicio) {
        this.dataDeInicio = dataDeInicio;
    }

    public Date getDataDeFim() {
        return dataDeFim;
    }

    public void setDataDeFim(Date dataDeFim) {
        this.dataDeFim = dataDeFim;
    }
}

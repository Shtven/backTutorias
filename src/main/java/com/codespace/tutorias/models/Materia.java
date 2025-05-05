package com.codespace.tutorias.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Materia {
    @Id
    private int nrc;

    private String nombreMateria;

    public Materia(){}

    public int getNrc() {
        return nrc;
    }

    public void setNrc(int nrc) {
        this.nrc = nrc;
    }

    public String getNombreMateria() {
        return nombreMateria;
    }

    public void setNombreMateria(String nombreMateria) {
        this.nombreMateria = nombreMateria;
    }
}

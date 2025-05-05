package com.codespace.tutorias.DTO;

public class MateriaDTO {
    private int nrc;
    private String nombreMateria;

    public MateriaDTO(){}

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

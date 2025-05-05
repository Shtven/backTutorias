package com.codespace.tutorias.models;


import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Tutoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idTutoria;

    @ManyToOne
    @JoinColumn(name= "idHorario")
    private Horario horario;

    @ManyToMany
    @JoinTable(
            name = "tutorias_tutorados",
            joinColumns = @JoinColumn(name = "id_tutoria"),
            inverseJoinColumns = @JoinColumn(name = "matricula")
    )
    private List<Tutorado> tutorados = new ArrayList<>();

    private LocalDate fecha;
    private int edificio;
    private int aula;
    private String estado;

    public Tutoria(){}

    public int getIdTutoria() {
        return idTutoria;
    }

    public void setIdTutoria(int idTutoria) {
        this.idTutoria = idTutoria;
    }

    public Horario getHorario() {
        return horario;
    }

    public void setHorario(Horario horario) {
        this.horario = horario;
    }

    public List<Tutorado> getTutorados() {
        return tutorados;
    }

    public void setTutorados(List<Tutorado> tutorados) {
        this.tutorados = tutorados;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public int getEdificio() {
        return edificio;
    }

    public void setEdificio(int edificio) {
        this.edificio = edificio;
    }

    public int getAula() {
        return aula;
    }

    public void setAula(int aula) {
        this.aula = aula;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}

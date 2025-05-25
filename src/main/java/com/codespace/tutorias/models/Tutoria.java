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

    @OneToMany(mappedBy = "tutoria")
    private List<TutoriaTutorado> tutoriasTutorados = new ArrayList<>();


    @ManyToOne
    @JoinColumn(name= "nrc")
    private Materia materia;

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

    public List<TutoriaTutorado> getTutoriasTutorados() {
        return tutoriasTutorados;
    }

    public void setTutoriasTutorados(List<TutoriaTutorado> tutoriasTutorados) {
        this.tutoriasTutorados = tutoriasTutorados;
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

    public Materia getMateria() {
        return materia;
    }

    public void setMateria(Materia materia) {
        this.materia = materia;
    }
}

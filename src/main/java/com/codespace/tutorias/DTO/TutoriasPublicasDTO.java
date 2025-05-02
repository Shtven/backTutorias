package com.codespace.tutorias.DTO;

import java.time.LocalDate;

public class TutoriasPublicasDTO {
    private int idTutoria;
    private HorariosPublicosDTO horario;
    private LocalDate fecha;
    private int edificio;
    private int aula;
    private TutoradosPublicosDTO tutorados;

    public TutoriasPublicasDTO(){}

    public int getIdTutoria() {
        return idTutoria;
    }

    public void setIdTutoria(int idTutoria) {
        this.idTutoria = idTutoria;
    }

    public HorariosPublicosDTO getHorario() {
        return horario;
    }

    public void setHorario(HorariosPublicosDTO horario) {
        this.horario = horario;
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

    public TutoradosPublicosDTO getTutorados() {
        return tutorados;
    }

    public void setTutorados(TutoradosPublicosDTO tutorados) {
        this.tutorados = tutorados;
    }
}

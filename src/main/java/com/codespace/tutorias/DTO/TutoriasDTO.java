package com.codespace.tutorias.DTO;

import java.time.LocalDate;

public class TutoriasDTO {
    private int idTutoria;
    private HorariosDTO horario;
    private LocalDate fecha;
    private int edificio;
    private int aula;
    private TutoradoDTO tutorados;

    public TutoriasDTO(){}

    public int getIdTutoria() {
        return idTutoria;
    }

    public void setIdTutoria(int idTutoria) {
        this.idTutoria = idTutoria;
    }

    public HorariosDTO getHorario() {
        return horario;
    }

    public void setHorario(HorariosDTO horario) {
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

    public TutoradoDTO getTutorados() {
        return tutorados;
    }

    public void setTutorados(TutoradoDTO tutorados) {
        this.tutorados = tutorados;
    }
}

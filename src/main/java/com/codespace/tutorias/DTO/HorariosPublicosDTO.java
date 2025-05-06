package com.codespace.tutorias.DTO;

import java.time.LocalTime;

public class HorariosPublicosDTO {
    private int idHorario;
    private String dia;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private TutoresPublicosDTO tutor;
    private MateriaDTO materia;

    public HorariosPublicosDTO(){}

    public int getIdHorario() {
        return idHorario;
    }

    public void setIdHorario(int idHorario) {
        this.idHorario = idHorario;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public LocalTime getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(LocalTime horaFin) {
        this.horaFin = horaFin;
    }

    public TutoresPublicosDTO getTutor() {
        return tutor;
    }

    public void setTutor(TutoresPublicosDTO tutor) {
        this.tutor = tutor;
    }

    public MateriaDTO getMateria() {
        return materia;
    }

    public void setMateria(MateriaDTO materia) {
        this.materia = materia;
    }
}

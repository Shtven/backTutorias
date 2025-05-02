package com.codespace.tutorias.DTO;

import java.time.LocalTime;

public class HorariosDTO {
    private int idHorario;
    private String dia;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private TutorDTO tutor;
    private MateriasDTO materia;

    public HorariosDTO(){}

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

    public TutorDTO getTutor() {
        return tutor;
    }

    public void setTutor(TutorDTO tutor) {
        this.tutor = tutor;
    }

    public MateriasDTO getMateria() {
        return materia;
    }

    public void setMateria(MateriasDTO materia) {
        this.materia = materia;
    }
}



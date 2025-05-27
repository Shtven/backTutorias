package com.codespace.tutorias.DTO;

import java.time.LocalTime;

public class HorariosMostrarDTO {
    private String dia;
    private LocalTime horaInicio;
    private LocalTime horaFin;

    public HorariosMostrarDTO(){}

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

}
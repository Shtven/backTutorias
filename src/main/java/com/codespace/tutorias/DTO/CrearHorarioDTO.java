package com.codespace.tutorias.DTO;

import com.codespace.tutorias.exceptions.HorarioValido;
import jakarta.validation.constraints.NotNull;

import java.time.LocalTime;

@HorarioValido
public class CrearHorarioDTO {
    private int idHorario;
    @NotNull(message = "Tienes que insertar un dia para tu horario.")
    private DiaSemana dia;
    @NotNull(message = "Tienes que ingresar una hora de inicio para tu horario.")
    private LocalTime horaInicio;
    @NotNull(message = "Tienes que ingresar un hora de finalización para tu horario.")
    private LocalTime horaFin;

    public CrearHorarioDTO(){}

    public int getIdHorario() {
        return idHorario;
    }

    public void setIdHorario(int idHorario) {
        this.idHorario = idHorario;
    }

    public DiaSemana getDia() {
        return dia;
    }

    public void setDia(DiaSemana dia) {
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

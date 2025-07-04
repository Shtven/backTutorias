package com.codespace.tutorias.DTO;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class ActualizarTutoriaDTO {
    @NotNull(message = "Debes especificar el horario.")
    private int idHorario;

    @NotNull(message = "Debes ingresar la fecha de la tutoría.")
    @FutureOrPresent(message = "La fecha no puede estar en el pasado.")
    private LocalDate fecha;

    @NotNull(message = "Debes ingresar el número de edificio.")
    private Integer edificio;

    @NotNull(message = "Debes ingresar el número de aula.")
    private Integer aula;

    @NotNull(message = "Debes ingresar un estado valido. \n Ejmplo: ACTIVO o COMPLETADO.")
    private String estado;

    @NotNull(message = "Debes especificar una materia.")
    private int nrcMateria;


    public ActualizarTutoriaDTO(){}

    public int getIdHorario() {
        return idHorario;
    }

    public void setIdHorario(int idHorario) {
        this.idHorario = idHorario;
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

    public int getNrcMateria() {
        return nrcMateria;
    }

    public void setNrcMateria(int nrcMateria) {
        this.nrcMateria = nrcMateria;
    }
}

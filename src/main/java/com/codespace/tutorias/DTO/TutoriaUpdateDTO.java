package com.codespace.tutorias.DTO;

import java.time.LocalDate;
import jakarta.validation.constraints.NotNull;

public class TutoriaUpdateDTO {
    @NotNull
    private LocalDate fecha;
    @NotNull
    private Integer edificio;
    @NotNull
    private Integer aula;
    private String estado;
    private boolean emergencia;

    public TutoriaUpdateDTO() {}

    public LocalDate getFecha() {
        return fecha;
    }
    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
    public Integer getEdificio() {
        return edificio;
    }
    public void setEdificio(Integer edificio) {
        this.edificio = edificio;
    }
    public Integer getAula() {
        return aula;
    }
    public void setAula(Integer aula) {
        this.aula = aula;
    }
    public String getEstado() {
        return estado;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }
    public boolean isEmergencia() {
        return emergencia;
    }
    public void setEmergencia(boolean emergencia) {
        this.emergencia = emergencia;
    }
}

package com.codespace.tutorias.DTO;

import java.time.LocalDate;
import java.util.List;

public class TutoriasPublicasDTO {
    private int idTutoria;
    private HorariosPublicosDTO horario;
    private LocalDate fecha;
    private int edificio;
    private int aula;
    private List<TutoradosPublicosDTO> tutorados;
    private String estado;
    private MateriaDTO materia;

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

    public List<TutoradosPublicosDTO> getTutorados() {
        return tutorados;
    }

    public void setTutorados(List<TutoradosPublicosDTO> tutorados) {
        this.tutorados = tutorados;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public MateriaDTO getMateria() {
        return materia;
    }

    public void setMateria(MateriaDTO materia) {
        this.materia = materia;
    }
}

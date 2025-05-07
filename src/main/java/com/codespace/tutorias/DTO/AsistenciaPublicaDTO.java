package com.codespace.tutorias.DTO;

public class AsistenciaPublicaDTO {
    private int idAsistencia;
    private int asistencia;
    private TutoriasPublicasDTO tutoria;

    public AsistenciaPublicaDTO() {}

    public int getIdAsistencia() {
        return idAsistencia;
    }

    public void setIdAsistencia(int idAsistencia) {
        this.idAsistencia = idAsistencia;
    }

    public int getAsistencia() {
        return asistencia;
    }

    public void setAsistencia(int asistencia) {
        this.asistencia = asistencia;
    }

    public TutoriasPublicasDTO getTutoria() {
        return tutoria;
    }

    public void setTutoria(TutoriasPublicasDTO tutoria) {
        this.tutoria = tutoria;
    }
}
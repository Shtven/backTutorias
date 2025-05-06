package com.codespace.tutorias.DTO;

public class AsistenciaDTO {
    private int idAsistencia;
    private TutoriasDTO tutoria;
    private int asistencia;

    public AsistenciaDTO(){}

    public int getIdAsistencia() {
        return idAsistencia;
    }

    public void setIdAsistencia(int idAsistencia) {
        this.idAsistencia = idAsistencia;
    }

    public TutoriasDTO getTutoria() {
        return tutoria;
    }

    public void setTutoria(TutoriasDTO tutoria) {
        this.tutoria = tutoria;
    }

    public int getAsistencia() {
        return asistencia;
    }

    public void setAsistencia(int asistencia) {
        this.asistencia = asistencia;
    }
}

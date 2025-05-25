package com.codespace.tutorias.models;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class TutoriaTutoradoId implements Serializable {

    private int idTutoria;
    private String matricula;

    public TutoriaTutoradoId() {}

    public TutoriaTutoradoId(int idTutoria, String matricula) {
        this.idTutoria = idTutoria;
        this.matricula = matricula;
    }

    public int getIdTutoria() {
        return idTutoria;
    }

    public void setIdTutoria(int idTutoria) {
        this.idTutoria = idTutoria;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TutoriaTutoradoId)) return false;
        TutoriaTutoradoId that = (TutoriaTutoradoId) o;
        return idTutoria == that.idTutoria &&
                matricula.equals(that.matricula);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idTutoria, matricula);
    }
}

package com.codespace.tutorias.models;

import jakarta.persistence.*;


@Entity
@Table(name = "tutorias_tutorados")
public class TutoriaTutorado {

    @EmbeddedId
    private TutoriaTutoradoId id;

    @ManyToOne
    @MapsId("idTutoria")
    @JoinColumn(name = "id_tutoria")
    private Tutoria tutoria;

    @ManyToOne
    @MapsId("matricula")
    @JoinColumn(name = "matricula")
    private Tutorado tutorado;

    public TutoriaTutorado() {}

    public TutoriaTutorado(Tutoria tutoria, Tutorado tutorado) {
        this.tutoria = tutoria;
        this.tutorado = tutorado;
        this.id = new TutoriaTutoradoId(tutoria.getIdTutoria(), tutorado.getMatricula());
    }

    public TutoriaTutoradoId getId() {
        return id;
    }

    public void setId(TutoriaTutoradoId id) {
        this.id = id;
    }

    public Tutoria getTutoria() {
        return tutoria;
    }

    public void setTutoria(Tutoria tutoria) {
        this.tutoria = tutoria;
    }

    public Tutorado getTutorado() {
        return tutorado;
    }

    public void setTutorado(Tutorado tutorado) {
        this.tutorado = tutorado;
    }
}

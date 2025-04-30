
package com.codespace.tutorias.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

/**
 *
 * @author shtven
 */

@Entity
public class Tutorados {
    @Id
    private String matricula;

    private String nombre;
    private String apellidoP;
    private String apellidoM;
    private String correo;
    private String password;

    public Tutorados(){ }

    public Tutorados(String matricula, String nombre, String apellidoP, String apellidoM, String correo, String password) {
        this.matricula = matricula;
        this.nombre = nombre;
        this.apellidoP = apellidoP;
        this.apellidoM = apellidoM;
        this.correo = correo;
        this.password = password;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoP() {
        return apellidoP;
    }

    public void setApellidoP(String apellidoP) {
        this.apellidoP = apellidoP;
    }

    public String getApellidoM() {
        return apellidoM;
    }

    public void setApellidoM(String apellidoM) {
        this.apellidoM = apellidoM;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

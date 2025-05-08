package com.codespace.tutorias.DTO;

public class CambioPasswordDTO {
    private String matricula;
    private String passwordActual;
    private String passwordNueva;

    public CambioPasswordDTO(){}

    public void setMatricula(String matricula){
        this.matricula = matricula;
    }

    public String getMatricula(){
        return matricula;
    }

    public void setPasswordActual(String passwordActual){
        this.passwordActual = passwordActual;
    }

    public String getPasswordActual(){
        return passwordActual;
    }

    public void setPasswordNueva(String passwordNueva){
        this.passwordNueva = passwordNueva;
    }

    public String getPasswordNueva(){
        return passwordNueva;
    }
}

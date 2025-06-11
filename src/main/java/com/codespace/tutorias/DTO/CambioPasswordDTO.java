package com.codespace.tutorias.DTO;

import jakarta.validation.constraints.Pattern;

public class CambioPasswordDTO {
    private String token;
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d).{8,}$",
            message = "La contraseña debe tener al menos 8 caracteres, una mayúscula y un número.")
    private String passwordNueva;

    public CambioPasswordDTO(){}

    public void setToken(String token){
        this.token = token;
    }

    public String getToken(){
        return token;
    }

    public void setPasswordNueva(String passwordNueva){
        this.passwordNueva = passwordNueva;
    }

    public String getPasswordNueva(){
        return passwordNueva;
    }
}

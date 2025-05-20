package com.codespace.tutorias.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class TutoradoDTO {
    @NotBlank(message = "Debes tener una matricula.")
    @Pattern(regexp = "^[a-zA-Z]\\d{8}$",
             message = "La matrícula debe comenzar con una letra y tener 8 dígitos")
    private String matricula;
    @NotBlank(message = "Debes tener un nombre.")
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚüÜñÑ\\s]+$",
            message = "Debes ingresar un nombre valido")
    private String nombre;
    @NotBlank(message = "Debes ingresar un correo.")
    @Email(message = "Debes ingresar un correo valido.")
    private String correo;
    @NotBlank(message = "Debes ingresar una contraseña.")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d).{8,}$",
            message = "La contraseña debe tener al menos 8 caracteres, una mayúscula y un número.")
    private String password;

    public TutoradoDTO(){}

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

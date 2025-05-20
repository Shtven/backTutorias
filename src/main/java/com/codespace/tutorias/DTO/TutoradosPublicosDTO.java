package com.codespace.tutorias.DTO;

public class TutoradosPublicosDTO {
        private String matricula;
        private String nombre;
        private String correo;

        public TutoradosPublicosDTO(){}

    public TutoradosPublicosDTO(String matricula, String nombre, String correo) {
        this.matricula = matricula;
        this.nombre = nombre;
        this.correo = correo;
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

        public String getCorreo() {
            return correo;
        }

        public void setCorreo(String correo) {
            this.correo = correo;
        }
}

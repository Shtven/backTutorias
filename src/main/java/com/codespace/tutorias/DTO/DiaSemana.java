package com.codespace.tutorias.DTO;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum DiaSemana {
    LUNES, MARTES, MIERCOLES, JUEVES, VIERNES;

    @JsonCreator
    public static DiaSemana fromString(String value) {
        if (value == null) return null;

        String normalizado = value
                .toUpperCase()
                .replace("Á", "A")
                .replace("É", "E")
                .replace("Í", "I")
                .replace("Ó", "O")
                .replace("Ú", "U");

        return switch (normalizado) {
            case "LUNES"     -> LUNES;
            case "MARTES"    -> MARTES;
            case "MIERCOLES" -> MIERCOLES;
            case "JUEVES"    -> JUEVES;
            case "VIERNES"   -> VIERNES;
            default -> throw new IllegalArgumentException("Día no válido: " + value);
        };
    }
}

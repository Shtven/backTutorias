package com.codespace.tutorias.Helpers;

import com.codespace.tutorias.exceptions.BusinessException;

public class ValidationHelper {

    public static void requireNonNull(Object obj, String fieldName) {
        if (obj == null) {
            throw new BusinessException("El campo '" + fieldName + "' es requerido.");
        }
    }

    public static void requireNonEmpty(String str, String fieldName) {
        if (str == null || str.isBlank()) {
            throw new BusinessException("El campo '" + fieldName + "' no puede estar vacío.");
        }
    }

    public static void requireMin(int value, int minValue, String fieldName) {
        if (value < minValue) {
            throw new BusinessException(
                "El campo '" + fieldName + "' debe ser al menos " + minValue + "."
            );
        }
    }

    public static void requirePattern(String str, String regex, String fieldName) {
        requireNonEmpty(str, fieldName);
        if (!str.matches(regex)) {
            throw new BusinessException(
                "El campo '" + fieldName + "' no cumple el formato esperado."
            );
        }
    }
}

package com.codespace.tutorias.exceptions;

import com.codespace.tutorias.DTO.CrearHorarioDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class HorarioValidoValidator implements ConstraintValidator<HorarioValido, CrearHorarioDTO> {

    @Override
    public boolean isValid(CrearHorarioDTO dto, ConstraintValidatorContext context) {
        if (dto.getHoraInicio() == null || dto.getHoraFin() == null) {
            return true; // Ya lo valida @NotNull
        }

        return dto.getHoraInicio().isBefore(dto.getHoraFin());
    }
}
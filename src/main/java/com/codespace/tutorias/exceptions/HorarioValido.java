package com.codespace.tutorias.exceptions;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = HorarioValidoValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface HorarioValido {
    String message() default "La hora de inicio debe ser anterior a la hora de fin.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
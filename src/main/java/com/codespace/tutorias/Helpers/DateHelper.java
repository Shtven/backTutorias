package com.codespace.tutorias.Helpers;

import java.time.*;

public class DateHelper {

    public static boolean haySolapamiento(LocalTime inicio1, LocalTime fin1,
                                          LocalTime inicio2, LocalTime fin2) {
        return inicio1.isBefore(fin2) && inicio2.isBefore(fin1);
    }

    public static boolean faltaMenosDe15Minutos(LocalDate fecha, LocalTime horaInicio) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime inicioTutoria = LocalDateTime.of(fecha, horaInicio);
        return Duration.between(now, inicioTutoria).toMinutes() < 15;
    }

    public static boolean yaComenzo(LocalDate fecha, LocalTime horaInicio){
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime inicioTutoria = LocalDateTime.of(fecha, horaInicio);

        return now.isAfter(inicioTutoria);
    }
}


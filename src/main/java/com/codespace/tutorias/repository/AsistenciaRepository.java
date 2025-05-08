package com.codespace.tutorias.repository;

import com.codespace.tutorias.models.Asistencia;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AsistenciaRepository extends JpaRepository<Asistencia, Integer> {

    @Query("SELECT SUM(a.asistencia) FROM Asistencia a")
    Integer sumTotalAsistencias();

    @Query("SELECT a.tutoria.idTutoria AS tutoriaId, "
    + "SUM(a.asistencia) AS totalAsistidos, "
    + "COUNT(a) AS totalRegistros "
    + "FROM Asistencia a "
    + "GROUP BY a.tutoria.idTutoria")
    List<Object[]> asistenciasPorTutorias();

}

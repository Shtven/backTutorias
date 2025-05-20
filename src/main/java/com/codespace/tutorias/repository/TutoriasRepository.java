package com.codespace.tutorias.repository;

import com.codespace.tutorias.DTO.TutoradosPublicosDTO;
import com.codespace.tutorias.DTO.TutoriasPublicasDTO;
import com.codespace.tutorias.models.Tutorado;
import com.codespace.tutorias.models.Tutoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TutoriasRepository extends JpaRepository<Tutoria, Integer> {

    @Query("SELECT t FROM Tutoria t JOIN t.tutorados tut WHERE tut.matricula = :matricula")
    List<Tutoria> findTutoriasPorTutorado(String matricula);

    @Query("SELECT t from Tutoria t JOIN t.horario h JOIN h.tutor tut WHERE tut.matricula = :matricula")
    List<Tutoria> findTutoriasPorTutor(String matricula);

    @Query("SELECT t FROM Tutoria t JOIN t.horario h JOIN h.tutor tut WHERE LOWER(tut.nombre) LIKE LOWER(CONCAT('%', :nombre, '%'))")
    List<Tutoria> findTutoriasPorNombreDeTutor(String nombre);

    @Query("SELECT COUNT(t) FROM Tutoria t")
    long countTotalTutorias();

    @Query("SELECT COUNT(DISTINCT tut) FROM Tutoria t JOIN t.tutorados tut")
    long countTotalAlumnosInscritos();


}

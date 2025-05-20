package com.codespace.tutorias.repository;

import com.codespace.tutorias.models.Horario;
import com.codespace.tutorias.models.Tutoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HorarioRepository extends JpaRepository<Horario, Integer> {

    @Query("SELECT h FROM Horario h JOIN h.tutor t WHERE t.matricula = :matricula")
    List<Horario> findByTutor(String matricula);
}

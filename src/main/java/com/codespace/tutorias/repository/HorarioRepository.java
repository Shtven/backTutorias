package com.codespace.tutorias.repository;

import com.codespace.tutorias.models.Horario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HorarioRepository extends JpaRepository<Horario, Integer> {
}

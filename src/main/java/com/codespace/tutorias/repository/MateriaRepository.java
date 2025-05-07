package com.codespace.tutorias.repository;

import com.codespace.tutorias.models.Materia;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MateriaRepository extends JpaRepository<Materia, Integer> {
}
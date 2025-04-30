package com.codespace.tutorias.repository;

import com.codespace.tutorias.models.Tutoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TutoriasRepository extends JpaRepository<Tutoria, Integer> {
}

package com.codespace.tutorias.repository;

import com.codespace.tutorias.models.Tutores;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TutorRepository extends JpaRepository<Tutores, String> {
}

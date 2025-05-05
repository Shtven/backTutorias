package com.codespace.tutorias.repository;

import com.codespace.tutorias.models.Tutor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TutorRepository extends JpaRepository<Tutor, String> {
}

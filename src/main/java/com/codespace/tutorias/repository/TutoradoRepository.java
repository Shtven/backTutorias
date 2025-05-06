package com.codespace.tutorias.repository;

import com.codespace.tutorias.models.Tutorado;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TutoradoRepository extends JpaRepository <Tutorado, String> {
}

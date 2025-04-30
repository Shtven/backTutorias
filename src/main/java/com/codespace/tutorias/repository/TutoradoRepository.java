package com.codespace.tutorias.repository;

import com.codespace.tutorias.models.Tutorados;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TutoradoRepository extends JpaRepository <Tutorados, String> {
}

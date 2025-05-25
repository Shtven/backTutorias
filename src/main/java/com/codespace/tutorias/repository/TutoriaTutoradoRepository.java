package com.codespace.tutorias.repository;

import com.codespace.tutorias.models.TutoriaTutorado;
import com.codespace.tutorias.models.TutoriaTutoradoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface TutoriaTutoradoRepository extends JpaRepository<TutoriaTutorado, TutoriaTutoradoId> {
    long countByTutoriaIdTutoria(int idTutoria);
    List<TutoriaTutorado> findByTutoradoMatricula(String matricula);
    List<TutoriaTutorado> findByTutoriaIdTutoria(int idTutoria);
}

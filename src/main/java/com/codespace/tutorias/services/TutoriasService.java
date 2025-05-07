package com.codespace.tutorias.services;

import com.codespace.tutorias.DTO.*;
import com.codespace.tutorias.Mapping.TutoriaMapping;
import com.codespace.tutorias.exceptions.BusinessException;
import com.codespace.tutorias.models.*;
import com.codespace.tutorias.repository.TutoriasRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TutoriasService {
    @Autowired
    private TutoriasRepository tutoriasRepository;
    @Autowired
    private TutoriaMapping tutoriaMapping;

    public List<TutoriasPublicasDTO> mostrarTutorias(){
        return tutoriasRepository.findAll().stream().map(tutoriaMapping::convertirAPublicas).toList();
    }

    public TutoriasDTO generarTutoria(TutoriasDTO dto){
        Tutoria tutoria = tutoriaMapping.convertirAEntidad(dto);
        return tutoriaMapping.convertirADTO(tutoriasRepository.save(tutoria));
    }

    public Optional<TutoriasPublicasDTO> findTutoriaPublica(int id){
        return tutoriasRepository.findById(id).map(tutoriaMapping::convertirAPublicas);
    }

    public Optional<TutoriasDTO> findTutoriaPrivada(int id){
        return tutoriasRepository.findById(id).map(tutoriaMapping::convertirADTO);
    }

    public List<TutoriasPublicasDTO> findMisTutorias(String matricula) {
        return tutoriasRepository.findTutoriasPorTutorado(matricula)
                .stream()
                .map(tutoriaMapping::convertirAPublicas)
                .toList();
    }

    public List<TutoriasPublicasDTO> findTutoriasPorMatriculaTutor(String matricula) {
        return tutoriasRepository.findTutoriasPorTutor(matricula)
                .stream()
                .map(tutoriaMapping::convertirAPublicas)
                .toList();
    }

    public List<TutoriasPublicasDTO> findTutoriasPorNombreTutor(String nombre) {
        return tutoriasRepository.findTutoriasPorNombreDeTutor(nombre)
                .stream()
                .map(tutoriaMapping::convertirAPublicas)
                .toList();
    }

    @Transactional
    public TutoriasDTO actualizarTutoria(int id, TutoriaUpdateDTO dto) {
        Tutoria entidad = tutoriasRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Tutoria no existe con ID: " + id));

        boolean sinAlumnos = entidad.getTutorados().isEmpty();
        if (!sinAlumnos && !dto.isEmergencia()) {
            throw new BusinessException("No se puede modificar: hay alumnos inscritos");
        }

        entidad.setFecha(dto.getFecha());
        entidad.setEdificio(dto.getEdificio());
        entidad.setAula(dto.getAula());
        entidad.setEstado(dto.getEstado());

        return tutoriaMapping.convertirADTO(tutoriasRepository.save(entidad));
    }

    @Transactional
    public void cancelarTutoria(int id, boolean emergencia) {
        Tutoria entidad = tutoriasRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Tutoria no existe con ID: " + id));

        if (!entidad.getTutorados().isEmpty() && !emergencia) {
            throw new BusinessException("No se puede cancelar: hay alumnos inscritos");
        }

        tutoriasRepository.delete(entidad);
    }

}

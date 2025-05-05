package com.codespace.tutorias.services;

import com.codespace.tutorias.DTO.*;
import com.codespace.tutorias.Mapping.TutoriaMapping;
import com.codespace.tutorias.models.*;
import com.codespace.tutorias.repository.TutoriasRepository;
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
}

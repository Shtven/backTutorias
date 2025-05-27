package com.codespace.tutorias.services;

import com.codespace.tutorias.DTO.CambioPasswordDTO;
import com.codespace.tutorias.DTO.TutorDTO;
import com.codespace.tutorias.DTO.TutoresPublicosDTO;
import com.codespace.tutorias.DTO.TutoriasPublicasDTO;
import com.codespace.tutorias.Mapping.TutorMapping;
import com.codespace.tutorias.Mapping.TutoriaMapping;
import com.codespace.tutorias.exceptions.BusinessException;
import com.codespace.tutorias.models.Tutor;
import com.codespace.tutorias.models.TutoriaTutorado;
import com.codespace.tutorias.repository.TutorRepository;


import com.codespace.tutorias.repository.TutoriasRepository;
import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TutorService {

    @Autowired
    private TutorRepository tutorRepository;
    @Autowired
    private TutorMapping tutorMapping;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private TutoriasRepository tutoriasRepository;
    @Autowired
    private TutoriaMapping tutoriaMapping;


    public List<TutoresPublicosDTO> mostrarTutoresPublicos(){
        return tutorRepository.findAll()
                .stream().map(tutorMapping::convertirAFront)
                .toList();
    }

    public List<TutorDTO> mostrarTutoresPrivados(){
        return tutorRepository.findAll()
                .stream().map(tutorMapping::convertirADTO)
                .toList();
    }

    public TutorDTO crearTutores(TutorDTO dto) {
        Tutor tutor = tutorMapping.convertirAEntidad(dto);
        tutor.setPassword(passwordEncoder.encode(tutor.getPassword()));
        return tutorMapping.convertirADTO(tutorRepository.save(tutor));
    }

    public void eliminarTutorado(String id){
        tutorRepository.deleteById(id);
    }

    public Optional<TutoresPublicosDTO> buscarTutorPublico(String id){
        return tutorRepository.findById(id).map(tutorMapping::convertirAFront);
    }

    public Optional<TutorDTO> buscarTutorPrivado(String id){
        return tutorRepository.findById(id).map(tutorMapping::convertirADTO);
    }

    public List<TutoriasPublicasDTO> findMisTutorias(String matricula) {
        return tutoriasRepository.findTutoriasPorTutor(matricula).stream()
                .map(tutoriaMapping::convertirAPublicas)
                .toList();
    }

}

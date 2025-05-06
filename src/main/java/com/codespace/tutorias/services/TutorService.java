package com.codespace.tutorias.services;

import com.codespace.tutorias.DTO.TutorDTO;
import com.codespace.tutorias.DTO.TutoresPublicosDTO;
import com.codespace.tutorias.Mapping.TutorMapping;
import com.codespace.tutorias.models.Tutor;
import com.codespace.tutorias.repository.TutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TutorService {

    @Autowired
    private TutorRepository tutorRepository;
    @Autowired
    private TutorMapping tutorMapping;

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
}

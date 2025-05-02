package com.codespace.tutorias.services;

import com.codespace.tutorias.DTO.TutorDTO;
import com.codespace.tutorias.DTO.TutoresPublicosDTO;
import com.codespace.tutorias.Mapping.TutorMapping;
import com.codespace.tutorias.models.Tutores;
import com.codespace.tutorias.repository.TutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TutoresService {

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

    public Tutores crearTutores(TutorDTO dto) {
        Tutores tutores = tutorMapping.convertirAEntidad(dto);
        return tutorRepository.save(tutores);
    }

    public void eliminarTutorado(String id){
        tutorRepository.deleteById(id);
    }

    public Optional<TutoresPublicosDTO> buscarTutoradoPublico(String id){
        return tutorRepository.findById(id).map(tutorMapping::convertirAFront);
    }

    public Optional<TutorDTO> buscarTutoradoPrivado(String id){
        return tutorRepository.findById(id).map(tutorMapping::convertirADTO);
    }
}

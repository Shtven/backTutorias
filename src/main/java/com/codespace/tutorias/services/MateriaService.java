package com.codespace.tutorias.services;

import com.codespace.tutorias.DTO.MateriaDTO;
import com.codespace.tutorias.Mapping.MateriaMapping;
import com.codespace.tutorias.models.Materia;
import com.codespace.tutorias.repository.MateriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MateriaService {

    @Autowired
    private MateriaRepository materiaRepository;
    @Autowired
    private MateriaMapping materiaMapping;

    public List<MateriaDTO> listarMaterias() {
        return materiaRepository.findAll()
                .stream()
                .map(materiaMapping::convertirADTO)
                .toList();
    }
}
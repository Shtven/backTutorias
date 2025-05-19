package com.codespace.tutorias.services;

import com.codespace.tutorias.DTO.MateriaDTO;
import com.codespace.tutorias.Helpers.ValidationHelper;
import com.codespace.tutorias.Mapping.MateriaMapping;
import com.codespace.tutorias.exceptions.BusinessException;
import com.codespace.tutorias.models.Materia;
import com.codespace.tutorias.repository.MateriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MateriaService {

    @Autowired private MateriaRepository materiaRepository;
    @Autowired private MateriaMapping materiaMapping;

    public List<MateriaDTO> listarMateriasPublicas() {
        return materiaRepository.findAll()
                .stream()
                .map(materiaMapping::convertirADTO)
                .toList();
    }

    public MateriaDTO crearMateria(MateriaDTO dto) {
        ValidationHelper.requireMin(dto.getNrc(), 1, "nrc");
        ValidationHelper.requireNonEmpty(dto.getNombreMateria(), "nombreMateria");

        Materia entidad = materiaMapping.convetirAEntidad(dto);
        materiaRepository.save(entidad);
        return materiaMapping.convertirADTO(entidad);
    }
}

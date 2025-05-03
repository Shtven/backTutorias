package com.codespace.tutorias.services;

import com.codespace.tutorias.DTO.MateriasDTO;
import com.codespace.tutorias.DTO.MateriaPublicaDTO;
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

    public List<MateriaPublicaDTO> listarMateriasPublicas() {
        return materiaRepository.findAll()
                .stream()
                .map(materiaMapping::convertirAPublica)
                .toList();
    }

    public Optional<MateriaPublicaDTO> buscarMateriaPublica(int nrc) {
        return materiaRepository.findById(nrc)
                .map(materiaMapping::convertirAPublica);
    }

    public Materia crearMateria(MateriasDTO dto) {
        Materia entidad = materiaMapping.convertirAEntidad(dto);
        return materiaRepository.save(entidad);
    }

    public Optional<Materia> actualizarMateria(int nrc, MateriasDTO dto) {
        return materiaRepository.findById(nrc)
                .map(existing -> {
                    existing.setNombreMateria(dto.getNombreMateria());
                    return materiaRepository.save(existing);
                });
    }

    public void eliminarMateria(int nrc) {
        materiaRepository.deleteById(nrc);
    }
}
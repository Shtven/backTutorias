package com.codespace.tutorias.services;

import com.codespace.tutorias.DTO.AsistenciaDTO;
import com.codespace.tutorias.DTO.AsistenciaPublicaDTO;
import com.codespace.tutorias.Mapping.AsistenciaMapping;
import com.codespace.tutorias.models.Asistencia;
import com.codespace.tutorias.repository.AsistenciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AsistenciaService {
    @Autowired
    private AsistenciaRepository asistenciaRepository;
    @Autowired
    private AsistenciaMapping asistenciaMapping;

    public List<AsistenciaPublicaDTO> listarAsistenciasPublicas() {
        return asistenciaRepository.findAll()
                .stream()
                .map(asistenciaMapping::convertirAPublica)
                .toList();
    }

    public Optional<AsistenciaPublicaDTO> buscarAsistenciaPublica(int id) {
        return asistenciaRepository.findById(id)
                .map(asistenciaMapping::convertirAPublica);
    }

    public Asistencia crearAsistencia(AsistenciaDTO dto) {
        Asistencia entidad = asistenciaMapping.convertirAEntidad(dto);
        return asistenciaRepository.save(entidad);
    }

    public Optional<Asistencia> actualizarAsistencia(int id, AsistenciaDTO dto) {
        return asistenciaRepository.findById(id)
                .map(existing -> {
                    var entidad = asistenciaMapping.convertirAEntidad(dto);
                    entidad.setIdAsistencia(id);
                    return asistenciaRepository.save(entidad);
                });
    }

    public void eliminarAsistencia(int id) {
        asistenciaRepository.deleteById(id);
    }
}
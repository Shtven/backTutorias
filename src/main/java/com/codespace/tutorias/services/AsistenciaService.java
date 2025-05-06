package com.codespace.tutorias.services;

import com.codespace.tutorias.DTO.AsistenciaDTO;
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

    public AsistenciaDTO registrarAsistencia(AsistenciaDTO dto){
        Asistencia asistencia = asistenciaMapping.convertirAEntidad(dto);
        return asistenciaMapping.convertirADTO(asistenciaRepository.save(asistencia));
    }

    public List<AsistenciaDTO> mostrarAsistencias(){
        return asistenciaRepository.findAll().stream().map(asistenciaMapping::convertirADTO).toList();
    }

    public List<AsistenciaDTO> buscarPorIdTutoria(int idTutoria) {
        return asistenciaRepository.findAll().stream()
                .filter(a -> a.getTutoria().getIdTutoria() == idTutoria)
                .map(asistenciaMapping::convertirADTO)
                .toList();
    }

}

package com.codespace.tutorias.services;

import com.codespace.tutorias.DTO.AsistenciaDTO;
import com.codespace.tutorias.Helpers.ValidationHelper;
import com.codespace.tutorias.Mapping.AsistenciaMapping;
import com.codespace.tutorias.models.Asistencia;
import com.codespace.tutorias.repository.AsistenciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AsistenciaService {

    @Autowired private AsistenciaRepository asistenciaRepository;
    @Autowired private AsistenciaMapping asistenciaMapping;

    public AsistenciaDTO registrarAsistencia(AsistenciaDTO dto) {
        ValidationHelper.requireNonNull(dto.getTutoria(), "tutoria");
        ValidationHelper.requireMin(dto.getAsistencia(), 0, "asistencia");
    

        Asistencia entidad = asistenciaMapping.convertirAEntidad(dto);
        return asistenciaMapping.convertirADTO(asistenciaRepository.save(entidad));
    }

    public List<AsistenciaDTO> mostrarAsistencias() {
        return asistenciaRepository.findAll().stream()
                .map(asistenciaMapping::convertirADTO)
                .toList();
    }

    public List<AsistenciaDTO> buscarPorIdTutoria(int idTutoria) {
        ValidationHelper.requireMin(idTutoria, 1, "idTutoria");
        return asistenciaRepository.findAll().stream()
                .filter(a -> a.getTutoria().getIdTutoria() == idTutoria)
                .map(asistenciaMapping::convertirADTO)
                .toList();
    }

    public int obtenerTotalAsistencias() {
        return Optional.ofNullable(asistenciaRepository.sumTotalAsistencias()).orElse(0);
    }

    public List<Map<String, Object>> obtenerEstadisticasPorTutoria() {
        List<Object[]> resultados = asistenciaRepository.asistenciasPorTutorias();
        return resultados.stream()
            .map(fila -> {
                Map<String, Object> m = new HashMap<>();
                m.put("tutoriaId", fila[0]);
                m.put("totalAsistidos", fila[1]);
                m.put("totalRegistros", fila[2]);
                return m;
            })
            .collect(Collectors.toList());
    }
}

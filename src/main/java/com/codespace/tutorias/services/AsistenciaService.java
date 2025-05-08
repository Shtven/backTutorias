package com.codespace.tutorias.services;

import com.codespace.tutorias.DTO.AsistenciaDTO;
import com.codespace.tutorias.Mapping.AsistenciaMapping;
import com.codespace.tutorias.models.Asistencia;
import com.codespace.tutorias.repository.AsistenciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    
    public int obtenerTotalAsistencias() {
        return Optional.ofNullable(asistenciaRepository.sumTotalAsistencias()).orElse(0);
    }

    public List<Map<String, Object>> obtenerEstadisticasPorTutoria() {
        List<Object[]> resultados = asistenciaRepository.asistenciasPorTutorias     ();
        List<Map<String, Object>> datos = new ArrayList<>();

        for (Object[] fila : resultados) {
            Map<String, Object> map = new HashMap<>();
            map.put("tutoriaId", fila[0]);
            map.put("totalAsistidos", fila[1]);
            map.put("totalRegistros", fila[2]);
            datos.add(map);
        }

        return datos;
    }



}

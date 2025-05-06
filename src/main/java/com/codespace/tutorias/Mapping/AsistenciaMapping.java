package com.codespace.tutorias.Mapping;

import com.codespace.tutorias.DTO.AsistenciaDTO;
import com.codespace.tutorias.models.Asistencia;
import com.codespace.tutorias.models.Tutoria;
import com.codespace.tutorias.repository.TutoriasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AsistenciaMapping {

    @Autowired
    private TutoriasRepository tutoriasRepository;
    @Autowired
    private TutoriaMapping tutoriaMapping;

    public Asistencia convertirAEntidad(AsistenciaDTO dto){
        Asistencia entidad = new Asistencia();

        entidad.setIdAsistencia(dto.getIdAsistencia());

        Tutoria tutoria = tutoriasRepository.findById(dto.getTutoria().getIdTutoria()).orElseThrow(() -> new RuntimeException("Tutoria no encontrada con ID: " + dto.getTutoria().getIdTutoria()));

        entidad.setTutoria(tutoria);
        entidad.setAsistencia(dto.getAsistencia());

        return entidad;
    }

    public AsistenciaDTO convertirADTO(Asistencia entidad){
        AsistenciaDTO dto = new AsistenciaDTO();

        dto.setIdAsistencia(entidad.getIdAsistencia());

        dto.setTutoria(tutoriaMapping.convertirADTO(entidad.getTutoria()));
        dto.setAsistencia(entidad.getAsistencia());

        return dto;
    }
}

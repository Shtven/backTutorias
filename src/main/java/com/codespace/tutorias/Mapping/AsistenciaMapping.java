package com.codespace.tutorias.Mapping;

import com.codespace.tutorias.DTO.AsistenciaDTO;
import com.codespace.tutorias.DTO.AsistenciaPublicaDTO;
import com.codespace.tutorias.models.Asistencia;
import com.codespace.tutorias.models.Tutoria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.codespace.tutorias.repository.TutoriasRepository;

@Component
public class AsistenciaMapping {

    @Autowired
    private TutoriasRepository tutoriaRepository;

    public Asistencia convertirAEntidad(AsistenciaDTO dto) {
        Asistencia entidad = new Asistencia();
        Tutoria tut = tutoriaRepository.findById(dto.getIdTutoria())
                         .orElseThrow(() -> new IllegalArgumentException("Tutoria no encontrada"));
        entidad.setTutoria(tut);
        entidad.setAsistencia(dto.getAsistencia());
        return entidad;
    }

    public AsistenciaPublicaDTO convertirAPublica(Asistencia entidad) {
        AsistenciaPublicaDTO dto = new AsistenciaPublicaDTO();
        dto.setIdAsistencia(entidad.getIdAsistencia());
        dto.setAsistencia(entidad.getAsistencia());

        var tutPubDto = new com.codespace.tutorias.DTO.TutoriasPublicasDTO();
        tutPubDto.setIdTutoria(entidad.getTutoria().getIdTutoria());
        tutPubDto.setFecha(entidad.getTutoria().getFecha());
        tutPubDto.setEdificio(entidad.getTutoria().getEdificio());
        tutPubDto.setAula(entidad.getTutoria().getAula());
        dto.setTutoria(tutPubDto);

        return dto;
    }
}

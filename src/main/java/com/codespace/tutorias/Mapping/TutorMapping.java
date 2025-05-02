package com.codespace.tutorias.Mapping;

import com.codespace.tutorias.DTO.TutorDTO;
import com.codespace.tutorias.DTO.TutoresPublicosDTO;
import com.codespace.tutorias.models.Tutores;
import org.springframework.stereotype.Component;

@Component
public class TutorMapping {
    public TutoresPublicosDTO convertirAFront(Tutores entidad){
        TutoresPublicosDTO dto = new TutoresPublicosDTO();
        dto.setMatricula(entidad.getMatricula());
        dto.setNombre(entidad.getNombre());
        dto.setApellidoP(entidad.getApellidoP());
        dto.setApellidoM(entidad.getApellidoM());
        dto.setCorreo(entidad.getCorreo());
        return dto;
    }

    public TutorDTO convertirADTO(Tutores entidad) {
        TutorDTO dto = new TutorDTO();
        dto.setMatricula(entidad.getMatricula());
        dto.setNombre(entidad.getNombre());
        dto.setApellidoP(entidad.getApellidoP());
        dto.setApellidoM(entidad.getApellidoM());
        dto.setCorreo(entidad.getCorreo());
        dto.setPassword(entidad.getPassword());
        return dto;
    }

    public Tutores convertirAEntidad(TutorDTO dto){
        Tutores entidad = new Tutores();
        entidad.setMatricula(dto.getMatricula());
        entidad.setNombre(dto.getNombre());
        entidad.setApellidoP(dto.getApellidoP());
        entidad.setApellidoM(dto.getApellidoM());
        entidad.setCorreo(dto.getCorreo());
        entidad.setPassword(dto.getPassword());
        return entidad;
    }
}

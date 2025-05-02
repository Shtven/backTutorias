package com.codespace.tutorias.Mapping;

import com.codespace.tutorias.DTO.TutoradoDTO;
import com.codespace.tutorias.DTO.TutoradosPublicosDTO;
import com.codespace.tutorias.models.Tutorados;
import org.springframework.stereotype.Component;

@Component
public class TutoradoMapping {
    public TutoradosPublicosDTO convertirAFront(Tutorados entidad) {
        TutoradosPublicosDTO dto = new TutoradosPublicosDTO();
        dto.setMatricula(entidad.getMatricula());
        dto.setNombre(entidad.getNombre());
        dto.setApellidoP(entidad.getApellidoP());
        dto.setApellidoM(entidad.getApellidoM());
        dto.setCorreo(entidad.getCorreo());
        return dto;
    }

    public TutoradoDTO convertirADTO(Tutorados entidad) {
        TutoradoDTO dto = new TutoradoDTO();
        dto.setMatricula(entidad.getMatricula());
        dto.setNombre(entidad.getNombre());
        dto.setApellidoP(entidad.getApellidoP());
        dto.setApellidoM(entidad.getApellidoM());
        dto.setCorreo(entidad.getCorreo());
        dto.setPassword(entidad.getPassword());
        return dto;
    }

    public Tutorados convertirAEntidad(TutoradoDTO dto){
        Tutorados entidad = new Tutorados();
        entidad.setMatricula(dto.getMatricula());
        entidad.setNombre(dto.getNombre());
        entidad.setApellidoP(dto.getApellidoP());
        entidad.setApellidoM(dto.getApellidoM());
        entidad.setCorreo(dto.getCorreo());
        entidad.setPassword(dto.getPassword());
        return entidad;
    }
}

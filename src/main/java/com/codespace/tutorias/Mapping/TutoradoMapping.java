package com.codespace.tutorias.Mapping;

import com.codespace.tutorias.DTO.TutoradoDTO;
import com.codespace.tutorias.DTO.TutoradosPublicosDTO;
import com.codespace.tutorias.models.Tutorado;
import org.springframework.stereotype.Component;

@Component
public class TutoradoMapping {
    public TutoradosPublicosDTO convertirAFront(Tutorado entidad) {
        TutoradosPublicosDTO dto = new TutoradosPublicosDTO();
        dto.setMatricula(entidad.getMatricula());
        dto.setNombre(entidad.getNombre());
        dto.setCorreo(entidad.getCorreo());
        return dto;
    }

    public TutoradoDTO convertirADTO(Tutorado entidad) {
        TutoradoDTO dto = new TutoradoDTO();
        dto.setMatricula(entidad.getMatricula());
        dto.setNombre(entidad.getNombre());
        dto.setCorreo(entidad.getCorreo());
        dto.setPassword(entidad.getPassword());
        return dto;
    }

    public Tutorado convertirAEntidad(TutoradoDTO dto){
        Tutorado entidad = new Tutorado();
        entidad.setMatricula(dto.getMatricula());
        entidad.setNombre(dto.getNombre());
        entidad.setCorreo(dto.getCorreo());
        entidad.setPassword(dto.getPassword());
        return entidad;
    }
}

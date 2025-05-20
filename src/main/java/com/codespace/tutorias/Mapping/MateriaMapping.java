package com.codespace.tutorias.Mapping;

import com.codespace.tutorias.DTO.MateriaDTO;
import com.codespace.tutorias.models.Materia;
import org.springframework.stereotype.Component;

@Component
public class MateriaMapping {

    public MateriaDTO convertirADTO(Materia entidad){
        MateriaDTO dto = new MateriaDTO();

        dto.setNrc(entidad.getNrc());
        dto.setNombreMateria(entidad.getNombreMateria());

        return dto;
    }

}

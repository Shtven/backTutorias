package com.codespace.tutorias.Mapping;

import com.codespace.tutorias.DTO.MateriasDTO;
import com.codespace.tutorias.DTO.MateriaPublicaDTO;
import com.codespace.tutorias.models.Materia;
import org.springframework.stereotype.Component;

@Component
public class MateriaMapping {

    public Materia convertirAEntidad(MateriasDTO dto) {
        Materia entidad = new Materia();
        entidad.setNrc(dto.getNrc());
        entidad.setNombreMateria(dto.getNombreMateria());
        return entidad;
    }

    public MateriasDTO convertirADTO(Materia entidad) {
        MateriasDTO dto = new MateriasDTO();
        dto.setNrc(entidad.getNrc());
        dto.setNombreMateria(entidad.getNombreMateria());
        return dto;
    }

    public MateriaPublicaDTO convertirAPublica(Materia entidad) {
        MateriaPublicaDTO dto = new MateriaPublicaDTO();
        dto.setNrc(entidad.getNrc());
        dto.setNombreMateria(entidad.getNombreMateria());
        return dto;
    }
}
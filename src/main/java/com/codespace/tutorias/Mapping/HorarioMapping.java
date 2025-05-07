package com.codespace.tutorias.Mapping;

import com.codespace.tutorias.DTO.HorariosDTO;
import com.codespace.tutorias.DTO.HorariosPublicosDTO;
import com.codespace.tutorias.models.Horario;
import org.springframework.stereotype.Component;

@Component
public class HorarioMapping {

    public Horario convertirAEntidad(HorariosDTO dto) {
        Horario entidad = new Horario();
        entidad.setIdHorario(dto.getIdHorario());
        entidad.setDia(dto.getDia());
        entidad.setHoraInicio(dto.getHoraInicio());
        entidad.setHoraFin(dto.getHoraFin());
        return entidad;
    }

    public HorariosDTO convertirADTO(Horario entidad) {
        HorariosDTO dto = new HorariosDTO();
        dto.setIdHorario(entidad.getIdHorario());
        dto.setDia(entidad.getDia());
        dto.setHoraInicio(entidad.getHoraInicio());
        dto.setHoraFin(entidad.getHoraFin());
        var tut = new com.codespace.tutorias.DTO.TutorDTO();
        tut.setMatricula(entidad.getTutor().getMatricula());
        tut.setNombre(entidad.getTutor().getNombre());
        tut.setApellidoP(entidad.getTutor().getApellidoP());
        tut.setApellidoM(entidad.getTutor().getApellidoM());
        tut.setCorreo(entidad.getTutor().getCorreo());
        tut.setPassword(entidad.getTutor().getPassword());
        dto.setTutor(tut);

        var mat = new com.codespace.tutorias.DTO.MateriaDTO();
        mat.setNrc(entidad.getMateria().getNrc());
        mat.setNombreMateria(entidad.getMateria().getNombreMateria());
        dto.setMateria(mat);

        return dto;
    }

    public HorariosPublicosDTO convertirAPublica(Horario entidad) {
        HorariosPublicosDTO dto = new HorariosPublicosDTO();
        dto.setIdHorario(entidad.getIdHorario());
        dto.setDia(entidad.getDia());
        dto.setHoraInicio(entidad.getHoraInicio());
        dto.setHoraFin(entidad.getHoraFin());

        var tutPub = new com.codespace.tutorias.DTO.TutoresPublicosDTO();
        tutPub.setMatricula(entidad.getTutor().getMatricula());
        tutPub.setNombre(entidad.getTutor().getNombre());
        tutPub.setApellidoP(entidad.getTutor().getApellidoP());
        tutPub.setApellidoM(entidad.getTutor().getApellidoM());
        tutPub.setCorreo(entidad.getTutor().getCorreo());
        dto.setTutor(tutPub);

        var mat = new com.codespace.tutorias.DTO.MateriaDTO();
        mat.setNrc(entidad.getMateria().getNrc());
        mat.setNombreMateria(entidad.getMateria().getNombreMateria());
        dto.setMateria(mat);

        return dto;
    }
}

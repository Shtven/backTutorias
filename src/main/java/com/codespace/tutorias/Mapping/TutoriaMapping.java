package com.codespace.tutorias.Mapping;

import com.codespace.tutorias.DTO.*;
import com.codespace.tutorias.models.Horario;
import com.codespace.tutorias.models.Tutorados;
import com.codespace.tutorias.models.Tutoria;
import com.codespace.tutorias.repository.HorarioRepository;
import com.codespace.tutorias.repository.TutoradoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TutoriaMapping {

    @Autowired
    private TutoradoRepository tutoradoRepository;
    @Autowired
    private HorarioRepository horarioRepository;

    public Tutoria convertirAEntidad(TutoriasDTO dto) {
        Tutoria entidad = new Tutoria();
        entidad.setIdTutoria(dto.getIdTutoria());

        Horario horario = horarioRepository.findById(dto.getHorario().getIdHorario()).orElseThrow();
        entidad.setHorario(horario);

        entidad.setFecha(dto.getFecha());
        entidad.setEdificio(dto.getEdificio());
        entidad.setAula(dto.getAula());

        Tutorados tutorado = tutoradoRepository.findById(dto.getTutorados().getMatricula()).orElseThrow();
        entidad.setTutorados(tutorado);

        return entidad;
    }

    public TutoriasDTO convertirADTO(Tutoria entidad){
        TutoriasDTO dto = new TutoriasDTO();
        dto.setIdTutoria(entidad.getIdTutoria());

        HorariosDTO horariosDTO = new HorariosDTO();
        horariosDTO.setIdHorario(entidad.getHorario().getIdHorario());
        horariosDTO.setDia(entidad.getHorario().getDia());
        horariosDTO.setHoraInicio(entidad.getHorario().getHoraInicio());
        horariosDTO.setHoraFin(entidad.getHorario().getHoraFin());

        TutorDTO tutoresDTO = new TutorDTO();
        tutoresDTO.setMatricula(entidad.getHorario().getTutor().getMatricula());
        tutoresDTO.setNombre(entidad.getHorario().getTutor().getNombre());
        tutoresDTO.setApellidoP(entidad.getHorario().getTutor().getApellidoP());
        tutoresDTO.setApellidoM(entidad.getHorario().getTutor().getApellidoM());
        tutoresDTO.setCorreo(entidad.getHorario().getTutor().getCorreo());
        tutoresDTO.setPassword(entidad.getHorario().getTutor().getPassword());

        horariosDTO.setTutor(tutoresDTO);

        MateriasDTO materiasDTO = new MateriasDTO();
        materiasDTO.setNrc(entidad.getHorario().getMateria().getNrc());
        materiasDTO.setNombreMateria(entidad.getHorario().getMateria().getNombreMateria());

        horariosDTO.setMateria(materiasDTO);

        dto.setHorario(horariosDTO);
        dto.setFecha(entidad.getFecha());
        dto.setEdificio(entidad.getEdificio());
        dto.setAula(entidad.getAula());

        TutoradoDTO tutoradosDTO = new TutoradoDTO();
        tutoradosDTO.setMatricula(entidad.getTutorados().getMatricula());
        tutoradosDTO.setNombre(entidad.getTutorados().getNombre());
        tutoradosDTO.setApellidoP(entidad.getTutorados().getApellidoP());
        tutoradosDTO.setApellidoM(entidad.getTutorados().getApellidoM());
        tutoradosDTO.setCorreo(entidad.getTutorados().getCorreo());
        tutoradosDTO.setPassword(entidad.getTutorados().getPassword());

        dto.setTutorados(tutoradosDTO);

        return dto;
    }

    public TutoriasPublicasDTO convertirAPublicas(Tutoria entidad){
        TutoriasPublicasDTO dto = new TutoriasPublicasDTO();
        dto.setIdTutoria(entidad.getIdTutoria());

        HorariosPublicosDTO horariosDTO = new HorariosPublicosDTO();
        horariosDTO.setIdHorario(entidad.getHorario().getIdHorario());
        horariosDTO.setDia(entidad.getHorario().getDia());
        horariosDTO.setHoraInicio(entidad.getHorario().getHoraInicio());
        horariosDTO.setHoraFin(entidad.getHorario().getHoraFin());

        TutoresPublicosDTO tutoresDTO = new TutoresPublicosDTO();
        tutoresDTO.setMatricula(entidad.getHorario().getTutor().getMatricula());
        tutoresDTO.setNombre(entidad.getHorario().getTutor().getNombre());
        tutoresDTO.setApellidoP(entidad.getHorario().getTutor().getApellidoP());
        tutoresDTO.setApellidoM(entidad.getHorario().getTutor().getApellidoM());
        tutoresDTO.setCorreo(entidad.getHorario().getTutor().getCorreo());

        horariosDTO.setTutor(tutoresDTO);

        MateriasDTO materiasDTO = new MateriasDTO();
        materiasDTO.setNrc(entidad.getHorario().getMateria().getNrc());
        materiasDTO.setNombreMateria(entidad.getHorario().getMateria().getNombreMateria());

        horariosDTO.setMateria(materiasDTO);

        dto.setHorario(horariosDTO);
        dto.setFecha(entidad.getFecha());
        dto.setEdificio(entidad.getEdificio());
        dto.setAula(entidad.getAula());

        TutoradosPublicosDTO tutoradosDTO = new TutoradosPublicosDTO();
        tutoradosDTO.setMatricula(entidad.getTutorados().getMatricula());
        tutoradosDTO.setNombre(entidad.getTutorados().getNombre());
        tutoradosDTO.setApellidoP(entidad.getTutorados().getApellidoP());
        tutoradosDTO.setApellidoM(entidad.getTutorados().getApellidoM());
        tutoradosDTO.setCorreo(entidad.getTutorados().getCorreo());

        dto.setTutorados(tutoradosDTO);

        return dto;
    }
}

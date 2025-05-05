package com.codespace.tutorias.Mapping;

import com.codespace.tutorias.DTO.*;
import com.codespace.tutorias.models.Horario;
import com.codespace.tutorias.models.Tutorado;
import com.codespace.tutorias.models.Tutoria;
import com.codespace.tutorias.repository.HorarioRepository;
import com.codespace.tutorias.repository.TutoradoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

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

        if (dto.getTutorados() != null && !dto.getTutorados().isEmpty()) {
            List<Tutorado> listaTutorados = dto.getTutorados().stream()
                    .map(t -> tutoradoRepository.findById(t.getMatricula()).orElseThrow())
                    .toList();
            entidad.setTutorados(listaTutorados);
        } else {
            entidad.setTutorados(List.of());
        }
        entidad.setEstado(dto.getEstado());

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

        MateriaDTO materiaDTO = new MateriaDTO();
        materiaDTO.setNrc(entidad.getHorario().getMateria().getNrc());
        materiaDTO.setNombreMateria(entidad.getHorario().getMateria().getNombreMateria());

        horariosDTO.setMateria(materiaDTO);

        dto.setHorario(horariosDTO);
        dto.setFecha(entidad.getFecha());
        dto.setEdificio(entidad.getEdificio());
        dto.setAula(entidad.getAula());

        List<TutoradoDTO> tutoradosDTO = entidad.getTutorados().stream()
                .map(t -> {
                    TutoradoDTO dtoT = new TutoradoDTO();
                    dtoT.setMatricula(t.getMatricula());
                    dtoT.setNombre(t.getNombre());
                    dtoT.setApellidoP(t.getApellidoP());
                    dtoT.setApellidoM(t.getApellidoM());
                    dtoT.setCorreo(t.getCorreo());
                    dtoT.setPassword(t.getPassword());
                    return dtoT;
                }).toList();


        dto.setTutorados(tutoradosDTO);
        dto.setEstado(entidad.getEstado());

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

        MateriaDTO materiaDTO = new MateriaDTO();
        materiaDTO.setNrc(entidad.getHorario().getMateria().getNrc());
        materiaDTO.setNombreMateria(entidad.getHorario().getMateria().getNombreMateria());

        horariosDTO.setMateria(materiaDTO);

        dto.setHorario(horariosDTO);
        dto.setFecha(entidad.getFecha());
        dto.setEdificio(entidad.getEdificio());
        dto.setAula(entidad.getAula());

        List<TutoradosPublicosDTO> tutoradosDTO = entidad.getTutorados().stream()
                .map(t -> {
                    TutoradosPublicosDTO dtoT = new TutoradosPublicosDTO();
                    dtoT.setMatricula(t.getMatricula());
                    dtoT.setNombre(t.getNombre());
                    dtoT.setApellidoP(t.getApellidoP());
                    dtoT.setApellidoM(t.getApellidoM());
                    dtoT.setCorreo(t.getCorreo());
                    return dtoT;
                }).toList();

        dto.setTutorados(tutoradosDTO);
        dto.setEstado(entidad.getEstado());

        return dto;
    }
}

package com.codespace.tutorias.services;

import com.codespace.tutorias.DTO.CambioPasswordDTO;
import com.codespace.tutorias.DTO.TutoradoDTO;
import com.codespace.tutorias.DTO.TutoradosPublicosDTO;
import com.codespace.tutorias.DTO.TutoriasPublicasDTO;
import com.codespace.tutorias.Helpers.DateHelper;
import com.codespace.tutorias.Mapping.TutoradoMapping;
import com.codespace.tutorias.Mapping.TutoriaMapping;
import com.codespace.tutorias.exceptions.BusinessException;
import com.codespace.tutorias.models.*;
import com.codespace.tutorias.repository.TutoradoRepository;

import com.codespace.tutorias.repository.TutoriaTutoradoRepository;
import com.codespace.tutorias.repository.TutoriasRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TutoradoService {

    @Autowired
    private TutoradoRepository tutoradoRepository;
    @Autowired
    private TutoradoMapping tutoradoMapping;
    @Autowired
    private TutoriaMapping tutoriaMapping;
    @Autowired
    private TutoriasRepository tutoriasRepository;
    @Autowired
    private TutoriaTutoradoRepository tutoriaTutoradoRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    public List<TutoradoDTO> listarTutoradosPrivados() {
        return tutoradoRepository.findAll().stream()
                .map(tutoradoMapping::convertirADTO).toList();
    }

    public List<TutoradosPublicosDTO> listarTutoradosPublicos() {
        return tutoradoRepository.findAll().stream()
                .map(tutoradoMapping::convertirAFront).toList();
    }

    public TutoradoDTO crearTutorados(TutoradoDTO dto) {
        Tutorado tutorado = tutoradoMapping.convertirAEntidad(dto);
        tutorado.setPassword(passwordEncoder.encode(tutorado.getPassword()));
        return tutoradoMapping.convertirADTO(tutoradoRepository.save(tutorado));
    }

    public void eliminarTutorado(String id){
        tutoradoRepository.deleteById(id);
    }

    public Optional<TutoradosPublicosDTO> buscarTutoradoPublico(String id){
        return tutoradoRepository.findById(id).map(tutoradoMapping::convertirAFront);
    }

    public Optional<TutoradoDTO> buscarTutoradoPrivado(String id){
        return tutoradoRepository.findById(id).map(tutoradoMapping::convertirADTO);
    }

    public List<TutoriasPublicasDTO> findMisTutorias(String matricula) {
        return tutoriaTutoradoRepository.findByTutoradoMatricula(matricula).stream()
                .map(TutoriaTutorado::getTutoria)
                .map(tutoriaMapping::convertirAPublicas)
                .toList();
    }

    public void inscribirATutoria(String matricula, int idTutoria){
        Tutoria tutoria = tutoriasRepository.findById(idTutoria)
                .orElseThrow(() ->  new BusinessException("La tutoría no existe."));

        Tutorado tutorado = tutoradoRepository.findById(matricula)
                .orElseThrow(() -> new BusinessException("El tutorado no existe."));

        if(DateHelper.yaComenzo(tutoria.getFecha(), tutoria.getHorario().getHoraInicio())){
            throw new BusinessException("La tutoria ya ha comenzado, ya no puedes inscribirte.");
        }

        TutoriaTutoradoId id = new TutoriaTutoradoId(idTutoria, matricula);
        if (tutoriaTutoradoRepository.existsById(id)) {
            throw new BusinessException("Ya estás inscrito a esta tutoría.");
        }

        long inscritos = tutoriaTutoradoRepository.countByTutoriaIdTutoria(idTutoria);
        if (inscritos >= 5) {
            throw new BusinessException("La tutoría está llena.");
        }

        List<Tutoria> tutoriasInscritas = tutoriaTutoradoRepository.findByTutoradoMatricula(matricula)
                .stream()
                .map(TutoriaTutorado::getTutoria)
                .toList();

        for (Tutoria t : tutoriasInscritas) {
            Horario h = t.getHorario();
            if (h.getDia().equals(tutoria.getHorario().getDia()) &&
                    DateHelper.haySolapamiento(
                            h.getHoraInicio(), h.getHoraFin(),
                            tutoria.getHorario().getHoraInicio(), tutoria.getHorario().getHoraFin())) {
                throw new BusinessException("Ya estás inscrito en otra tutoría con el mismo horario.");
            }
        }

        TutoriaTutorado relacion = new TutoriaTutorado(tutoria, tutorado);
        tutoriaTutoradoRepository.save(relacion);
    }

    public void cancelarInscripcion(String matricula, int idTutoria){
        Tutoria tutoria = tutoriasRepository.findById(idTutoria)
                .orElseThrow(() -> new BusinessException("La tutoría no existe."));
        Tutorado tutorado = tutoradoRepository.findById(matricula)
                .orElseThrow(() -> new BusinessException("El tutorado no existe."));

        if (DateHelper.faltaMenosDe15Minutos(tutoria.getFecha(), tutoria.getHorario().getHoraInicio())) {
            throw new BusinessException("Faltan 15 minutos o menos para que comience la tutoría, ya no puedes cancelar tu inscripción.");
        }

        TutoriaTutoradoId id = new TutoriaTutoradoId(idTutoria, matricula);
        if (!tutoriaTutoradoRepository.existsById(id)) {
            throw new BusinessException("No estás inscrito en esta tutoría.");
        }

        tutoriaTutoradoRepository.deleteById(id);
    }

    public void mandarCorreoRecuperacion(String correo){
        Tutorado tutorado = tutoradoRepository.findByCorreo(correo);
        if(tutorado == null){
            throw new BusinessException("Correo no asociado a algun tutorado");
        }

    }

    public void activarNotificaciones(String matricula){
        Tutorado tutorado = tutoradoRepository.findById(matricula)
                .orElseThrow(() -> new BusinessException("El tutorado no existe."));
        tutorado.setNotificarme(true);
        tutoradoRepository.save(tutorado);
    }

    public void desactivarNotificaciones(String matricula){
        Tutorado tutorado = tutoradoRepository.findById(matricula)
                .orElseThrow(() -> new BusinessException("El tutorado no existe."));
        tutorado.setNotificarme(false);
        tutoradoRepository.save(tutorado);
    }

}

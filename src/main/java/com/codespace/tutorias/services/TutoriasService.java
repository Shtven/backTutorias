package com.codespace.tutorias.services;

import com.codespace.tutorias.DTO.*;
import com.codespace.tutorias.Helpers.DateHelper;
import com.codespace.tutorias.Mapping.TutoradoMapping;
import com.codespace.tutorias.Mapping.TutoriaMapping;
import com.codespace.tutorias.exceptions.BusinessException;
import com.codespace.tutorias.models.*;
import com.codespace.tutorias.repository.TutoradoRepository;
import com.codespace.tutorias.repository.TutoriaTutoradoRepository;
import com.codespace.tutorias.repository.TutoriasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TutoriasService {
    @Autowired
    private TutoriasRepository tutoriasRepository;
    @Autowired
    private TutoriaMapping tutoriaMapping;
    @Autowired
    private TutoradoRepository tutoradoRepository;
    @Autowired
    private TutoriaTutoradoRepository tutoriaTutoradoRepository;
    @Autowired
    private TutoradoMapping tutoradoMapping;
    @Autowired
    private EmailService emailService;

    public List<TutoriasPublicasDTO> mostrarTutorias(){
        return tutoriasRepository.findAll().stream().map(tutoriaMapping::convertirAPublicas).toList();
    }

    public TutoriasDTO generarTutoria(CrearTutoriaDTO dto){

        Tutoria tutoria = tutoriaMapping.convertirANuevaEntidad(dto);
        List<Tutoria> listaTutorias = findPorMatriculaTutor(tutoria.getHorario().getTutor().getMatricula());
        for (Tutoria lista: listaTutorias){
            Horario horario = lista.getHorario();

            if (horario.getDia().equals(tutoria.getHorario().getDia())) {
                if (DateHelper.haySolapamiento(
                        tutoria.getHorario().getHoraInicio(),
                        tutoria.getHorario().getHoraFin(),
                        horario.getHoraInicio(),
                        horario.getHoraFin()) && (!tutoria.getEstado().equals("COMPLETADA") && !tutoria.getEstado().equals("CANCELADA"))) {
                    throw new BusinessException("Ya existe una tutoría en ese horario y día.");
                }
            }
        }
        return tutoriaMapping.convertirADTO(tutoriasRepository.save(tutoria));
    }

    public Optional<TutoriasPublicasDTO> findTutoriaPublica(int id){
        return tutoriasRepository.findById(id).map(tutoriaMapping::convertirAPublicas);
    }

    public Optional<TutoriasDTO> findTutoriaPrivada(int id){
        return tutoriasRepository.findById(id).map(tutoriaMapping::convertirADTO);
    }

    public List<TutoriasPublicasDTO> findTutoriasPorMatriculaTutor(String matricula) {
        return tutoriasRepository.findTutoriasPorTutor(matricula)
                .stream()
                .map(tutoriaMapping::convertirAPublicas)
                .toList();
    }

    public List<TutoriasPublicasDTO> findTutoriasPorNombreTutor(String nombre) {
        return tutoriasRepository.findTutoriasPorNombreDeTutor(nombre)
                .stream()
                .map(tutoriaMapping::convertirAPublicas)
                .toList();
    }

    private List<Tutoria> findPorMatriculaTutor(String matricula) {
        return tutoriasRepository.findTutoriasPorTutor(matricula);
    }

    public TutoriasDTO editarTutoria(int id, ActualizarTutoriaDTO dto){
        Tutoria tutoria = tutoriaMapping.convertirEdicion(id, dto);

        Tutoria tuto = tutoriasRepository.findById(tutoria.getIdTutoria())
                .orElseThrow(() -> new BusinessException("La tutoría no existe."));

        if(DateHelper.faltaMenosDe15Minutos(tuto.getFecha(), tuto.getHorario().getHoraInicio())){
            throw new BusinessException("Faltan solo 15 minútos para que inicie la tutoria, ya no ṕuedes modificarla");
        }

        if (!tuto.getTutoriasTutorados().isEmpty()) {
            throw new BusinessException("No puedes modificar la tutoría porque ya hay tutorados inscritos.");
        }

        return tutoriaMapping.convertirADTO(tutoriasRepository.save(tutoria));
    }

    public void eliminarTutoria(int idTutoria){
        Tutoria tutoria =  tutoriasRepository.findById(idTutoria)
                .orElseThrow(() -> new BusinessException("La tutoría no existe."));

        if(DateHelper.faltaMenosDe15Minutos(tutoria.getFecha(), tutoria.getHorario().getHoraInicio())){
            throw new BusinessException("Faltan solo 15 minútos para que inicie la tutoria, ya no ṕuedes eliminarla");
        }

        if (!tutoria.getTutoriasTutorados().isEmpty()) {
            throw new BusinessException("No puedes eliminar la tutoría porque ya hay tutorados inscritos.");
        }

        tutoriasRepository.deleteById(tutoria.getIdTutoria());
    }

    public void tutoriaCompletada(int idTutoria){
        Tutoria tutoria =  tutoriasRepository.findById(idTutoria)
                .orElseThrow(() -> new BusinessException("La tutoría no existe."));

        tutoria.setEstado("COMPLETADA");

        tutoriasRepository.save(tutoria);
    }

    public List<TutoradosPublicosDTO> mostrarTutoradosInscritos(int idTutoria){
        if (!tutoriasRepository.existsById(idTutoria)) {
            throw new BusinessException("La tutoría no existe.");
        }

        return tutoriaTutoradoRepository.findByTutoriaIdTutoria(idTutoria).stream()
                .map(TutoriaTutorado::getTutorado)
                .map(tutoradoMapping::convertirAFront)
                .toList();
    }

    public void cancelarTutoria(int idTutoria){
        Tutoria tutoria =  tutoriasRepository.findById(idTutoria)
                .orElseThrow(() -> new BusinessException("La tutoría no existe."));

        if(DateHelper.faltaMenosDe15Minutos(tutoria.getFecha(), tutoria.getHorario().getHoraInicio())){
            throw new BusinessException("Faltan solo 15 minútos para que inicie la tutoria, ya no puedes cancelar la tutoria");
        }

        tutoria.setEstado("CANCELADA");

        tutoriasRepository.save(tutoria);

        emailService.enviarCorreoCancelacion(tutoria);
    }

    @Scheduled(fixedRate = 60000)
    private void marcarTutoriaCompleta(){
        List<Tutoria> tutorias = tutoriasRepository.findAll();
        ZoneId zonaMexico = ZoneId.of("America/Mexico_City");
        ZonedDateTime ahoraZoned = ZonedDateTime.now(zonaMexico);
        LocalDate hoy = ahoraZoned.toLocalDate();
        LocalTime ahora = ahoraZoned.toLocalTime();

        for (Tutoria t : tutorias){
            if((!"COMPLETADA".equalsIgnoreCase(t.getEstado()) && !"CANCELADA".equalsIgnoreCase(t.getEstado()))
                    && t.getFecha().equals(hoy)){
                if(ahora.isAfter(t.getHorario().getHoraFin()) && "EN CURSO".equals(t.getEstado())){
                    t.setEstado("COMPLETADA");
                    tutoriasRepository.save(t);
                }
            }
        }
    }

    @Scheduled(fixedRate = 60000)
    private void marcarTutoriaEnCurso(){
        List<Tutoria> tutorias = tutoriasRepository.findAll();
        ZoneId zonaMexico = ZoneId.of("America/Mexico_City");
        ZonedDateTime ahoraZoned = ZonedDateTime.now(zonaMexico);
        LocalDate hoy = ahoraZoned.toLocalDate();
        LocalTime ahora = ahoraZoned.toLocalTime();

        for (Tutoria t : tutorias){
            if((!"COMPLETADA".equalsIgnoreCase(t.getEstado()) && !"CANCELADA".equalsIgnoreCase(t.getEstado()))
                    && t.getFecha().equals(hoy)){
                if(ahora.isAfter(t.getHorario().getHoraInicio()) && "A PUNTO DE INICIAR".equals(t.getEstado())){
                    t.setEstado("EN CURSO");
                    tutoriasRepository.save(t);
                }
            }
        }
    }
}

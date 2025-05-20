package com.codespace.tutorias.services;

import com.codespace.tutorias.DTO.*;
import com.codespace.tutorias.Helpers.DateHelper;
import com.codespace.tutorias.Mapping.TutoriaMapping;
import com.codespace.tutorias.exceptions.BusinessException;
import com.codespace.tutorias.models.*;
import com.codespace.tutorias.repository.TutoriasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TutoriasService {
    @Autowired
    private TutoriasRepository tutoriasRepository;
    @Autowired
    private TutoriaMapping tutoriaMapping;

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
                        horario.getHoraFin())) {
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

    public TutoriasDTO editarTutoria(TutoriasDTO dto){
        Tutoria tutoria = tutoriaMapping.convertirAEntidad(dto);

        Tutoria tuto = tutoriasRepository.findById(tutoria.getIdTutoria())
                .orElseThrow(() -> new BusinessException("La tutoría no existe."));

        if(DateHelper.faltaMenosDe15Minutos(tuto.getFecha(), tuto.getHorario().getHoraInicio())){
            throw new BusinessException("Faltan solo 15 minútos para que inicie la tutoria, ya no ṕuedes modificarla");
        }

        if (!tuto.getTutorados().isEmpty()) {
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

        if (!tutoria.getTutorados().isEmpty()) {
            throw new BusinessException("No puedes eliminar la tutoría porque ya hay tutorados inscritos.");
        }

        tutoriasRepository.deleteById(tutoria.getIdTutoria());
    }

}

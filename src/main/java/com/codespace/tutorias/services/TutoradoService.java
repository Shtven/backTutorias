package com.codespace.tutorias.services;

import com.codespace.tutorias.DTO.CambioPasswordDTO;
import com.codespace.tutorias.DTO.TutoradoDTO;
import com.codespace.tutorias.DTO.TutoradosPublicosDTO;
import com.codespace.tutorias.DTO.TutoriasPublicasDTO;
import com.codespace.tutorias.Helpers.DateHelper;
import com.codespace.tutorias.Mapping.TutoradoMapping;
import com.codespace.tutorias.Mapping.TutoriaMapping;
import com.codespace.tutorias.exceptions.BusinessException;
import com.codespace.tutorias.models.Horario;
import com.codespace.tutorias.models.Tutorado;
import com.codespace.tutorias.models.Tutoria;
import com.codespace.tutorias.repository.TutoradoRepository;

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
        return tutoriasRepository.findTutoriasPorTutorado(matricula)
                .stream()
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

        List<Tutorado> listaInscritos = tutoria.getTutorados();

        if(listaInscritos.contains(tutorado)){
            throw new BusinessException("Ya estás inscrito a esta tutoria");
        }

        if(listaInscritos.size() > 4){
            throw new BusinessException("La tutoria está llena.");
        }

        List<Tutoria> tutoriasInscritas = tutoriasRepository.findTutoriasPorTutorado(matricula);
        for (Tutoria t : tutoriasInscritas) {
            Horario h = t.getHorario();
            if (h.getDia().equals(tutoria.getHorario().getDia()) &&
                    DateHelper.haySolapamiento(
                            h.getHoraInicio(), h.getHoraFin(),
                            tutoria.getHorario().getHoraInicio(), tutoria.getHorario().getHoraFin())) {
                throw new BusinessException("Ya estás inscrito en otra tutoría con el mismo horario.");
            }
        }

        listaInscritos.add(tutorado);
        tutoria.setTutorados(listaInscritos);

        tutoriasRepository.save(tutoria);
    }

    public void cancelarInscripcion(String matricula, int idHorario){
        Tutoria tutoria = tutoriasRepository.findById(idHorario)
                .orElseThrow(() -> new BusinessException("La tutoria no existe."));
        Tutorado tutorado = tutoradoRepository.findById(matricula)
                .orElseThrow(() -> new BusinessException("El tutorado no existe."));

        if(DateHelper.faltaMenosDe15Minutos(tutoria.getFecha(), tutoria.getHorario().getHoraInicio())){
            throw new BusinessException("Faltan 15 mminutos o menos para que comience la tutoria, ya no puedes cancelar tu inscripción.");
        }

        List<Tutorado> listaInscritos = tutoria.getTutorados();

        boolean removed = listaInscritos.removeIf(t -> t.getMatricula().equals(matricula));

        if (!removed) {
            throw new BusinessException("No estás inscrito en esta tutoría.");
        }

        tutoria.setTutorados(listaInscritos);
        tutoriasRepository.save(tutoria);
    }

}

package com.codespace.tutorias.services;

import com.codespace.tutorias.DTO.TutoriasDTO;
import com.codespace.tutorias.DTO.TutoriasPublicasDTO;
import com.codespace.tutorias.Helpers.ValidationHelper;
import com.codespace.tutorias.Mapping.TutoriaMapping;
import com.codespace.tutorias.exceptions.BusinessException;
import com.codespace.tutorias.models.Tutoria;
import com.codespace.tutorias.repository.TutoriasRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
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
        return tutoriasRepository.findAll()
                .stream()
                .map(tutoriaMapping::convertirAPublicas)
                .toList();
    }

    public TutoriasDTO generarTutoria(TutoriasDTO dto){
        ValidationHelper.requireNonNull(dto, "tutoriasDTO");
        ValidationHelper.requireNonNull(dto.getHorario(), "horario");
        ValidationHelper.requireNonNull(dto.getFecha(), "fecha");
        ValidationHelper.requireMin(dto.getEdificio(), 1, "edificio");
        ValidationHelper.requireMin(dto.getAula(), 1, "aula");
        ValidationHelper.requireNonEmpty(dto.getEstado(), "estado");

        Tutoria tutoria = tutoriaMapping.convertirAEntidad(dto);
        Tutoria guardada = tutoriasRepository.save(tutoria);
        return tutoriaMapping.convertirADTO(guardada);
    }

    public Optional<TutoriasPublicasDTO> findTutoriaPublica(int id){
        ValidationHelper.requireMin(id, 1, "idTutoria");
        return tutoriasRepository.findById(id)
                .map(tutoriaMapping::convertirAPublicas);
    }

    public Optional<TutoriasDTO> findTutoriaPrivada(int id){
        ValidationHelper.requireMin(id, 1, "idTutoria");
        return tutoriasRepository.findById(id)
                .map(tutoriaMapping::convertirADTO);
    }

    public List<TutoriasPublicasDTO> findMisTutorias(String matricula) {
        ValidationHelper.requireNonEmpty(matricula, "matricula");
        return tutoriasRepository.findTutoriasPorTutorado(matricula)
                .stream()
                .map(tutoriaMapping::convertirAPublicas)
                .toList();
    }

    public List<TutoriasPublicasDTO> findTutoriasPorMatriculaTutor(String matricula) {
        ValidationHelper.requireNonEmpty(matricula, "matricula");
        return tutoriasRepository.findTutoriasPorTutor(matricula)
                .stream()
                .map(tutoriaMapping::convertirAPublicas)
                .toList();
    }

    public List<TutoriasPublicasDTO> findTutoriasPorNombreTutor(String nombre) {
        ValidationHelper.requireNonEmpty(nombre, "valor de búsqueda");
        return tutoriasRepository.findTutoriasPorNombreDeTutor(nombre)
                .stream()
                .map(tutoriaMapping::convertirAPublicas)
                .toList();
    }

    @Transactional
    public TutoriasDTO actualizarTutoria(int id, TutoriasDTO dto) {
        ValidationHelper.requireMin(id, 1, "idTutoria");
        ValidationHelper.requireNonNull(dto, "tutoriasDTO");
        ValidationHelper.requireNonNull(dto.getHorario(), "horario");
        ValidationHelper.requireNonNull(dto.getFecha(), "fecha");
        ValidationHelper.requireMin(dto.getEdificio(), 1, "edificio");
        ValidationHelper.requireMin(dto.getAula(), 1, "aula");
        ValidationHelper.requireNonEmpty(dto.getEstado(), "estado");

        Tutoria original = tutoriasRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tutoria no existe"));

        if (!original.getTutorados().isEmpty()) {
            throw new BusinessException("No se puede modificar: hay alumnos inscritos");
        }

        Tutoria entidad = tutoriaMapping.convertirAEntidad(dto);
        entidad.setIdTutoria(id); 
        Tutoria guardada = tutoriasRepository.save(entidad);
        return tutoriaMapping.convertirADTO(guardada);
    }

    @Transactional
    public void cancelarTutoria(int id) {
        ValidationHelper.requireMin(id, 1, "idTutoria");

        if (!tutoriasRepository.existsById(id)) {
            throw new EntityNotFoundException("Tutoria no existe");
        }
        tutoriasRepository.deleteById(id);
    }

    public List<com.codespace.tutorias.DTO.TutoradosPublicosDTO> listarTutoradosInscritos(Integer idTutoria) {
        ValidationHelper.requireMin(idTutoria, 1, "idTutoria");
        return tutoriasRepository.findTutoradosByTutoria(idTutoria);
    }

    public long obtenerTotalTutorias() {
        return tutoriasRepository.countTotalTutorias();
    }

    public long obtenerTotalAlumnosInscritos() {
        return tutoriasRepository.countTotalAlumnosInscritos();
    }
}

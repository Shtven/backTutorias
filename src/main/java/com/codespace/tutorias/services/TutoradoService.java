package com.codespace.tutorias.services;

import com.codespace.tutorias.DTO.TutoradoDTO;
import com.codespace.tutorias.DTO.TutoradosPublicosDTO;
import com.codespace.tutorias.Helpers.ValidationHelper;
import com.codespace.tutorias.Mapping.TutoradoMapping;
import com.codespace.tutorias.exceptions.BusinessException;
import com.codespace.tutorias.models.Horario;
import com.codespace.tutorias.models.Tutorado;
import com.codespace.tutorias.models.Tutoria;
import com.codespace.tutorias.repository.TutoradoRepository;
import com.codespace.tutorias.repository.TutoriasRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class TutoradoService {

    @Autowired
    private TutoradoRepository tutoradoRepository;
    @Autowired
    private TutoriasRepository tutoriasRepository;
    @Autowired
    private TutoradoMapping tutoradoMapping;

    public List<TutoradoDTO> listarTutoradosPrivados() {
        return tutoradoRepository.findAll().stream()
                .map(tutoradoMapping::convertirADTO)
                .toList();
    }

    public List<TutoradosPublicosDTO> listarTutoradosPublicos() {
        return tutoradoRepository.findAll().stream()
                .map(tutoradoMapping::convertirAFront)
                .toList();
    }

    public TutoradoDTO crearTutorados(TutoradoDTO dto) {
        ValidationHelper.requireNonEmpty(dto.getMatricula(), "matricula");
        ValidationHelper.requireNonEmpty(dto.getNombre(), "nombre");
        ValidationHelper.requireNonEmpty(dto.getApellidoP(), "apellidoP");
        ValidationHelper.requireNonEmpty(dto.getApellidoM(), "apellidoM");
        ValidationHelper.requireNonEmpty(dto.getCorreo(), "correo");
        ValidationHelper.requireNonEmpty(dto.getPassword(), "password");

        Tutorado entidad = tutoradoMapping.convertirAEntidad(dto);
        Tutorado guardado = tutoradoRepository.save(entidad);
        return tutoradoMapping.convertirADTO(guardado);
    }

    public void eliminarTutorado(String id) {
        ValidationHelper.requireNonEmpty(id, "matricula");
        tutoradoRepository.deleteById(id);
    }

    public Optional<TutoradosPublicosDTO> buscarTutoradoPublico(String id) {
        ValidationHelper.requireNonEmpty(id, "matricula");
        return tutoradoRepository.findById(id)
                .map(tutoradoMapping::convertirAFront);
    }

    public Optional<TutoradoDTO> buscarTutoradoPrivado(String id) {
        ValidationHelper.requireNonEmpty(id, "matricula");
        return tutoradoRepository.findById(id)
                .map(tutoradoMapping::convertirADTO);
    }

    @Transactional
    public void cancelarInscripcionATutoria(String matricula, int idTutoria) {
        ValidationHelper.requireNonEmpty(matricula, "matricula");
        ValidationHelper.requireMin(idTutoria, 1, "idTutoria");

        Tutoria tutoria = tutoriasRepository.findById(idTutoria)
                .orElseThrow(() -> new EntityNotFoundException("La tutoria no existe."));
        Horario horario = tutoria.getHorario();
        if (horario == null) {
            throw new BusinessException("La tutoria no tiene horario asignado.");
        }

        DayOfWeek diaSemana = tutoria.getFecha().getDayOfWeek();
        String diaTexto = diaSemana.getDisplayName(TextStyle.FULL, new Locale("es", "ES"));
        if (!horario.getDia().equalsIgnoreCase(diaTexto)) {
            throw new BusinessException("La fecha no coincide con el día asignado en el horario.");
        }

        LocalDateTime fechaHoraTutoria = tutoria.getFecha().atTime(horario.getHoraInicio());
        long minutosRestantes = Duration.between(LocalDateTime.now(), fechaHoraTutoria).toMinutes();
        if (minutosRestantes < 15) {
            throw new BusinessException("No puedes cancelar tu inscripción con menos de 15 minutos de anticipación.");
        }

        List<Tutorado> tutorados = tutoria.getTutorados();
        tutorados.removeIf(t -> t.getMatricula().equals(matricula));
        tutoriasRepository.save(tutoria);
    }
}

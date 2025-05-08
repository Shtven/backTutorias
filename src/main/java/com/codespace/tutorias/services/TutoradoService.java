package com.codespace.tutorias.services;

//import com.codespace.tutorias.DTO.PasswordUpdateDTO;
import com.codespace.tutorias.DTO.TutoradoDTO;
import com.codespace.tutorias.DTO.TutoradosPublicosDTO;
import com.codespace.tutorias.Mapping.TutoradoMapping;
import com.codespace.tutorias.exceptions.BusinessException;
import com.codespace.tutorias.models.Horario;
import com.codespace.tutorias.models.Tutorado;
import com.codespace.tutorias.models.Tutoria;
import com.codespace.tutorias.repository.TutoradoRepository;
import com.codespace.tutorias.repository.TutoriasRepository;
import java.time.Duration;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class TutoradoService {

    @Autowired
    private TutoriasRepository tutoriasRepository;
    @Autowired
    private TutoradoRepository tutoradoRepository;
    @Autowired
    private TutoradoMapping tutoradoMapping;

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
        return tutoradoMapping.convertirADTO(tutoradoRepository.save(tutorado));
    }

    public void eliminarTutorado(String id) {
        tutoradoRepository.deleteById(id);
    }

    public Optional<TutoradosPublicosDTO> buscarTutoradoPublico(String id) {
        return tutoradoRepository.findById(id).map(tutoradoMapping::convertirAFront);
    }

    public Optional<TutoradoDTO> buscarTutoradoPrivado(String id) {
        return tutoradoRepository.findById(id).map(tutoradoMapping::convertirADTO);
    }

 /*     public void cambiarContrasena(String matricula, PasswordUpdateDTO dto) {
        Tutorado tutorado = tutoradoRepository.findById(matricula)
                .orElseThrow(() -> new EntityNotFoundException("Tutorado no encontrado: " + matricula));

        if (!tutorado.getPassword().equals(dto.getOldPassword())) {
            throw new BusinessException("Contraseña actual incorrecta");
        }
        tutorado.setPassword(dto.getNewPassword());
        tutoradoRepository.save(tutorado);
    }
*/
    @Transactional
    public void cancelarInscripcionATutoria(String matricula, int idTutoria) {
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
        tutorados.removeIf(tutorado -> tutorado.getMatricula().equals(matricula));
        tutoriasRepository.save(tutoria); 
    }

}

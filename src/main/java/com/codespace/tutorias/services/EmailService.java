package com.codespace.tutorias.services;
import com.codespace.tutorias.Helpers.EmailHelper;
import com.codespace.tutorias.models.Tutoria;
import com.codespace.tutorias.models.TutoriaTutorado;
import com.codespace.tutorias.repository.TutoriasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.List;

@Service
public class EmailService {

    @Autowired private TutoriasRepository tutoriasRepository;
    @Autowired private EmailHelper emailHelper;

    @Scheduled(fixedRate = 60000)
    public void enviarRecordatorios() {
        List<Tutoria> tutorias = tutoriasRepository.findAll();

        ZoneId zonaMexico = ZoneId.of("America/Mexico_City");
        ZonedDateTime ahoraZoned = ZonedDateTime.now(zonaMexico);
        LocalDate hoy = ahoraZoned.toLocalDate();
        LocalTime ahora = ahoraZoned.toLocalTime();

        for (Tutoria tutoria : tutorias) {
            Tutoria tutoriaConTutorados = tutoriasRepository.findTutoriaWithTutorados(tutoria.getIdTutoria());

            if (!"COMPLETADO".equalsIgnoreCase(tutoria.getEstado())
                    && tutoria.getFecha().equals(hoy)) {

                LocalTime inicio = tutoria.getHorario().getHoraInicio();
                long minutosRestantes = java.time.Duration.between(ahora, inicio).toMinutes();

                if (minutosRestantes <= 15 && ahora.isBefore(inicio) && !"A PUNTO DE INICIAR".equalsIgnoreCase(tutoria.getEstado())) {
                    for (TutoriaTutorado t : tutoriaConTutorados.getTutoriasTutorados()) {
                        if(!t.getTutorado().isRecordatorio()){
                            continue;
                        }
                        String cuerpo = String.format("""
<html>
<body>
    <h2 style='color: #4CAF50;'>¡Hola %s!</h2>
    <p>Te recordamos que tienes una <strong>tutoría</strong> próxima a iniciar.</p>
    <p><b>Materia:</b> %s</p>
    <p><b>Horario:</b> %s</p>
    <p><b>Edificio:</b> %s | <b>Aula:</b> %s</p>
    <hr>
    <p style='color: gray; font-size: 12px;'>Este es un mensaje automático del sistema de tutorías.</p>
</body>
</html>
""",
                                t.getTutorado().getNombre(),
                                tutoria.getMateria().getNombreMateria(),
                                tutoria.getHorario().getHoraInicio(),
                                tutoria.getEdificio(),
                                tutoria.getAula()
                        );

                        emailHelper.enviarCorreo(
                                t.getTutorado().getCorreo(),
                                "Recordatorio de tutoría próxima",
                                cuerpo
                        );
                    }

                    tutoria.setEstado("A PUNTO DE INICIAR");
                    tutoriasRepository.save(tutoria);

                    String cuerpoTutor = String.format("""
<html>
<body>
    <h2 style='color: #2196F3;'>¡Hola %s!</h2>
    <p>Este es un recordatorio para tu <strong>tutoría</strong> próxima a iniciar.</p>
    <p><b>Materia:</b> %s</p>
    <p><b>Horario:</b> %s</p>
    <p><b>Edificio:</b> %s | <b>Aula:</b> %s</p>
    <hr>
    <p style='color: gray; font-size: 12px;'>Mensaje automático del sistema de tutorías.</p>
</body>
</html>
""",
                            tutoria.getHorario().getTutor().getNombre(),
                            tutoria.getMateria().getNombreMateria(),
                            tutoria.getHorario().getHoraInicio(),
                            tutoria.getEdificio(),
                            tutoria.getAula()
                    );

                    emailHelper.enviarCorreo(
                            tutoria.getHorario().getTutor().getCorreo(),
                            "Recordatorio: tu tutoría comienza en 15 minutos",
                            cuerpoTutor
                    );
                }
            }
        }
    }
}
package com.codespace.tutorias.services;
import com.codespace.tutorias.Helpers.EmailHelper;
import com.codespace.tutorias.models.Tutorado;
import com.codespace.tutorias.models.Tutoria;
import com.codespace.tutorias.repository.TutoriasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class ReminderService {

    @Autowired private TutoriasRepository tutoriasRepository;
    @Autowired private EmailHelper emailHelper;

    @Scheduled(fixedRate = 60000)
    public void enviarRecordatorios() {
        List<Tutoria> tutorias = tutoriasRepository.findAll();

        LocalDate hoy = LocalDate.now();
        LocalTime ahora = LocalTime.now();

        for (Tutoria tutoria : tutorias) {
            if (!"COMPLETADO".equalsIgnoreCase(tutoria.getEstado())
                    && tutoria.getFecha().equals(hoy)) {

                LocalTime inicio = tutoria.getHorario().getHoraInicio();
                long minutosRestantes = java.time.Duration.between(ahora, inicio).toMinutes();

                if (minutosRestantes == 15) {
                    for (Tutorado t : tutoria.getTutorados()) {
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
                                t.getNombre(),
                                tutoria.getMateria().getNombreMateria(),
                                tutoria.getHorario().getHoraInicio(),
                                tutoria.getEdificio(),
                                tutoria.getAula()
                        );

                    }
                }
            }
        }
    }

}

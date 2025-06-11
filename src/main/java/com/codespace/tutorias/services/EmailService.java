package com.codespace.tutorias.services;
import com.codespace.tutorias.Helpers.EmailHelper;
import com.codespace.tutorias.models.Tutorado;
import com.codespace.tutorias.models.Tutoria;
import com.codespace.tutorias.models.TutoriaTutorado;
import com.codespace.tutorias.repository.TutoriasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.List;

@Service
public class EmailService {

    @Autowired
    private TutoriasRepository tutoriasRepository;
    @Autowired
    private EmailHelper emailHelper;
    @Value("${url.var}")
    private String url;

    @Scheduled(fixedRate = 60000)
    public void enviarRecordatorios() {
        List<Tutoria> tutorias = tutoriasRepository.findAll();

        ZoneId zonaMexico = ZoneId.of("America/Mexico_City");
        ZonedDateTime ahoraZoned = ZonedDateTime.now(zonaMexico);
        LocalDate hoy = ahoraZoned.toLocalDate();
        LocalTime ahora = ahoraZoned.toLocalTime();

        for (Tutoria tutoria : tutorias) {
            Tutoria tutoriaConTutorados = tutoriasRepository.findTutoriaWithTutorados(tutoria.getIdTutoria());

            if ((!"COMPLETADA".equalsIgnoreCase(tutoria.getEstado()) && !"CANCELADA".equalsIgnoreCase(tutoria.getEstado()))
                    && tutoria.getFecha().equals(hoy)) {

                LocalTime inicio = tutoria.getHorario().getHoraInicio();
                long minutosRestantes = java.time.Duration.between(ahora, inicio).toMinutes();

                if (minutosRestantes <= 15 && ahora.isBefore(inicio) && "PENDIENTE".equalsIgnoreCase(tutoria.getEstado())) {
                    for (TutoriaTutorado t : tutoriaConTutorados.getTutoriasTutorados()) {
                        if(!t.getTutorado().isRecordatorio()){
                            continue;
                        }
                        String cuerpo = new StringBuilder()
                                .append("<html>")
                                .append("<body style=\"font-family: 'Segoe UI', sans-serif; background-color: #f9f9f9; padding: 20px;\">")
                                .append("<div style=\"max-width: 600px; margin: auto; background: white; border-radius: 10px; box-shadow: 0 2px 8px rgba(0,0,0,0.1); padding: 30px;\">")
                                .append("<h2 style=\"color: #4CAF50; text-align: center;\">¡Hola ").append(t.getTutorado().getNombre()).append("!</h2>")
                                .append("<p style=\"font-size: 16px; text-align: center; color: #333;\">")
                                .append("Te recordamos que tienes una <strong>tutoría</strong> próxima a iniciar.")
                                .append("</p>")
                                .append("<table style=\"margin: 20px auto; border-collapse: collapse; width: 100%; max-width: 400px;\">")
                                .append("<tr><td style=\"padding: 10px; font-weight: bold; color: #444;\">Materia:</td>")
                                .append("<td style=\"padding: 10px; color: #6A1B9A;\">").append(tutoria.getMateria().getNombreMateria()).append("</td></tr>")
                                .append("<tr><td style=\"padding: 10px; font-weight: bold; color: #444;\">Horario:</td>")
                                .append("<td style=\"padding: 10px; color: #D84315;\">").append(tutoria.getHorario().getHoraInicio()).append("</td></tr>")
                                .append("<tr><td style=\"padding: 10px; font-weight: bold; color: #444;\">Edificio / Aula:</td>")
                                .append("<td style=\"padding: 10px; color: #0277BD;\">").append(tutoria.getEdificio()).append(" / ").append(tutoria.getAula()).append("</td></tr>")
                                .append("</table>")
                                .append("<p style=\"text-align: center; font-size: 14px; color: gray; margin-top: 40px;\">")
                                .append("Este es un mensaje automático del sistema de tutorías.")
                                .append("</p>")
                                .append("</div>")
                                .append("</body>")
                                .append("</html>")
                                .toString();

                        emailHelper.enviarCorreo(
                                t.getTutorado().getCorreo(),
                                "Recordatorio de tutoría próxima",
                                cuerpo
                        );
                    }

                    tutoria.setEstado("A PUNTO DE INICIAR");
                    tutoriasRepository.save(tutoria);

                    String cuerpoTutor = new StringBuilder()
                            .append("<html>")
                            .append("<body style=\"font-family: 'Segoe UI', sans-serif; background-color: #f9f9f9; padding: 20px;\">")
                            .append("<div style=\"max-width: 600px; margin: auto; background: white; border-radius: 10px; box-shadow: 0 2px 8px rgba(0,0,0,0.1); padding: 30px;\">")
                            .append("<h2 style=\"color: #4CAF50; text-align: center;\">¡Hola ").append(tutoria.getHorario().getTutor().getNombre()).append("!</h2>")
                            .append("<p style=\"font-size: 16px; text-align: center; color: #333;\">")
                            .append("Te recordamos que tienes una <strong>tutoría</strong> próxima a iniciar.")
                            .append("</p>")
                            .append("<table style=\"margin: 20px auto; border-collapse: collapse; width: 100%; max-width: 400px;\">")
                            .append("<tr><td style=\"padding: 10px; font-weight: bold; color: #444;\">Materia:</td>")
                            .append("<td style=\"padding: 10px; color: #6A1B9A;\">").append(tutoria.getMateria().getNombreMateria()).append("</td></tr>")
                            .append("<tr><td style=\"padding: 10px; font-weight: bold; color: #444;\">Horario:</td>")
                            .append("<td style=\"padding: 10px; color: #D84315;\">").append(tutoria.getHorario().getHoraInicio()).append("</td></tr>")
                            .append("<tr><td style=\"padding: 10px; font-weight: bold; color: #444;\">Edificio / Aula:</td>")
                            .append("<td style=\"padding: 10px; color: #0277BD;\">").append(tutoria.getEdificio()).append(" / ").append(tutoria.getAula()).append("</td></tr>")
                            .append("</table>")
                            .append("<p style=\"text-align: center; font-size: 14px; color: gray; margin-top: 40px;\">")
                            .append("Este es un mensaje automático del sistema de tutorías.")
                            .append("</p>")
                            .append("</div>")
                            .append("</body>")
                            .append("</html>")
                            .toString();

                    emailHelper.enviarCorreo(
                            tutoria.getHorario().getTutor().getCorreo(),
                            "Recordatorio: tu tutoría comienza en 15 minutos",
                            cuerpoTutor
                    );
                }
            }
        }
    }

    public void enviarCorreoRecuperacion(String correoDestino, String nombreUsuario, String tokenRecuperacion) {

        String cuerpo = new StringBuilder()
                .append("<html>")
                .append("<body style=\"font-family: 'Segoe UI', sans-serif; background-color: #f9f9f9; padding: 20px;\">")
                .append("<div style=\"max-width: 600px; margin: auto; background: white; border-radius: 10px; box-shadow: 0 2px 8px rgba(0,0,0,0.1); padding: 30px;\">")
                .append("<h2 style=\"color: #4CAF50; text-align: center;\">Hola ").append(nombreUsuario).append("</h2>")
                .append("<p style=\"font-size: 16px; text-align: center; color: #333;\">")
                .append("Has solicitado recuperar tu contraseña. Haz clic en el siguiente enlace para restablecerla:")
                .append("</p>")
                .append("<p style=\"text-align: center; margin-top: 20px;\">")
                .append("<a href=\"").append(url).append("/reset-password?token=").append(tokenRecuperacion)
                .append("\" style=\"background-color: #4CAF50; color: white; padding: 10px 20px; text-decoration: none; border-radius: 5px;\">")
                .append("Restablecer contraseña</a></p>")
                .append("<p style=\"text-align: center; font-size: 14px; color: #888; margin-top: 30px;\">")
                .append("Si no solicitaste este cambio, puedes ignorar este mensaje.")
                .append("</p>")
                .append("<hr style=\"margin-top: 40px;\">")
                .append("<p style=\"color: gray; font-size: 12px; text-align: center;\">Este es un mensaje automático del sistema de tutorías.</p>")
                .append("</div>")
                .append("</body>")
                .append("</html>")
                .toString();

        emailHelper.enviarCorreo(
                correoDestino,
                "Recuperación de contraseña - Sistema de Tutorías",
                cuerpo
        );
    }

    public void enviarCorreoCancelacion(Tutoria tutoria){
        List<Tutorado> tutorados = tutoria.getTutoriasTutorados()
                .stream()
                .map(TutoriaTutorado::getTutorado).toList();

        if(tutorados.isEmpty()) return;

        for(Tutorado t: tutorados){
            String cuerpo = new StringBuilder()
                    .append("<html>")
                    .append("<body style=\"font-family: 'Segoe UI', sans-serif; background-color: #f9f9f9; padding: 20px;\">")
                    .append("<div style=\"max-width: 600px; margin: auto; background: white; border-radius: 10px; box-shadow: 0 2px 8px rgba(0,0,0,0.1); padding: 30px;\">")
                    .append("<h2 style=\"color: #4CAF50; text-align: center;\">¡Hola ").append(t.getNombre()).append("!</h2>")
                    .append("<p style=\"font-size: 16px; text-align: center; color: #333;\">")
                    .append("Te informamos que la tutoría con el tutor ").append(tutoria.getHorario().getTutor().getNombre()).append(" ")
                    .append(tutoria.getHorario().getTutor().getApellidoP()).append(" ")
                    .append(tutoria.getHorario().getTutor().getApellidoM()).append(" ")
                    .append(" ha sido cancelada.</p>")
                    .append("<table style=\"margin: 20px auto; border-collapse: collapse; width: 100%; max-width: 400px;\">")
                    .append("<tr><td style=\"padding: 10px; font-weight: bold; color: #444;\">Materia:</td>")
                    .append("<td style=\"padding: 10px; color: #6A1B9A;\">").append(tutoria.getMateria().getNombreMateria()).append("</td></tr>")
                    .append("<tr><td style=\"padding: 10px; font-weight: bold; color: #444;\">Horario:</td>")
                    .append("<td style=\"padding: 10px; color: #D84315;\">").append(tutoria.getHorario().getHoraInicio()).append("</td></tr>")
                    .append("<tr><td style=\"padding: 10px; font-weight: bold; color: #444;\">Edificio / Aula:</td>")
                    .append("<td style=\"padding: 10px; color: #0277BD;\">").append(tutoria.getEdificio()).append(" / ").append(tutoria.getAula()).append("</td></tr>")
                    .append("</table>")
                    .append("<p style=\"text-align: center; font-size: 14px; color: gray; margin-top: 40px;\">")
                    .append("Este es un mensaje automático del sistema de tutorías.")
                    .append("</p>")
                    .append("</div>")
                    .append("</body>")
                    .append("</html>")
                    .toString();

            emailHelper.enviarCorreo(
                    t.getCorreo(),
                    "Cancelación de tutoria",
                    cuerpo
            );
        }
    }

}
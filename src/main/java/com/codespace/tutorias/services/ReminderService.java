package com.codespace.tutorias.services;
import com.codespace.tutorias.Helpers.EmailHelper;
import com.codespace.tutorias.models.Tutoria;
import com.codespace.tutorias.repository.TutoriasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReminderService {

    @Autowired private TutoriasRepository tutoriasRepository;
    @Autowired private EmailHelper emailHelper;

    @Scheduled(cron = "0 0 8 * * *")
    public void sendTutoriaReminders() {
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        List<Tutoria> lista = tutoriasRepository.findAll().stream()
            .filter(t -> tomorrow.equals(t.getFecha()))
            .toList();

        for (Tutoria t : lista) {
            t.getTutorados().forEach(usuario -> {
                String to = usuario.getCorreo();
                String subject = "Recordatorio: Tutoría programada para " + tomorrow;
                String body = String.format(
                    "Hola %s,\n\nTienes programada una tutoría el %s en el edificio %d, aula %d.\n\n¡No faltes!\n",
                    usuario.getNombre(), tomorrow, t.getEdificio(), t.getAula()
                );
                emailHelper.sendEmail(to, subject, body);
            });
        }
    }
}

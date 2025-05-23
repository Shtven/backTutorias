package com.codespace.tutorias.controllers;

import com.codespace.tutorias.DTO.TutoriasDTO;
import com.codespace.tutorias.DTO.TutoriasPublicasDTO;
import com.codespace.tutorias.DTO.TutoradosPublicosDTO;
import com.codespace.tutorias.Helpers.ValidationHelper;
import com.codespace.tutorias.services.TutoriasService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tutorias")
public class TutoriasController {
    @Autowired private TutoriasService tutoriasService;

    @GetMapping("/all")
    public ResponseEntity<?> obtenerTutorias(HttpServletRequest req) {
        String rol = (String) req.getAttribute("rol");
        if (!"tutorado".equals(rol) && !"tutor".equals(rol)) {
            return ResponseEntity.status(403).body("Acceso denegado");
        }
        return ResponseEntity.ok(tutoriasService.mostrarTutorias());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TutoriasPublicasDTO> obtenerTutoria(@PathVariable int id){
        ValidationHelper.requireMin(id, 1, "idTutoria");
        return tutoriasService.findTutoriaPublica(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/genera-tutoria")
    public ResponseEntity<?> generarTutoria(@RequestBody TutoriasDTO dto, HttpServletRequest req){
        String rol = (String) req.getAttribute("rol");
        if (!"tutor".equals(rol)) {
            return ResponseEntity.status(403).body("Acceso denegado");
        }
        ValidationHelper.requireNonNull(dto, "tutoriasDTO");
        ValidationHelper.requireNonNull(dto.getHorario(), "horario");
        ValidationHelper.requireNonNull(dto.getFecha(), "fecha");
        ValidationHelper.requireMin(dto.getEdificio(), 1, "edificio");
        ValidationHelper.requireMin(dto.getAula(), 1, "aula");
        ValidationHelper.requireNonEmpty(dto.getEstado(), "estado");
        TutoriasDTO creado = tutoriasService.generarTutoria(dto);
        URI location = URI.create("/tutorias/" + creado.getIdTutoria());
        return ResponseEntity.created(location).body(creado);
    }

    @GetMapping("/mis-tutorias")
    public ResponseEntity<?> misTutorias(HttpServletRequest req){
        String rol = (String) req.getAttribute("rol");
        String matricula = (String) req.getAttribute("matricula");
        if (!"tutorado".equals(rol)) {
            return ResponseEntity.status(403).body("Acceso denegado");
        }
        ValidationHelper.requireNonEmpty(matricula, "matricula");
        return ResponseEntity.ok(tutoriasService.findMisTutorias(matricula));
    }

    @GetMapping("/buscar")
    public ResponseEntity<?> buscarPorMatriculaONombre(
            @RequestParam String valor,
            HttpServletRequest req) {
        String rol = (String) req.getAttribute("rol");
        if (!"tutorado".equals(rol)) {
            return ResponseEntity.status(403).body("Acceso denegado");
        }
        ValidationHelper.requireNonEmpty(valor, "valor de búsqueda");
        if (valor.matches("^[a-zA-Z]\\d{8}$")) {
            return ResponseEntity.ok(tutoriasService.findTutoriasPorMatriculaTutor(valor));
        } else {
            return ResponseEntity.ok(tutoriasService.findTutoriasPorNombreTutor(valor));
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> cancelarTutoria(
            @PathVariable int id,
            @RequestParam(defaultValue = "false") boolean emergencia) {
        ValidationHelper.requireMin(id, 1, "idTutoria");
        if (!emergencia) {
            return ResponseEntity.status(401).build();
        }
        tutoriasService.cancelarTutoria(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/alumnos-inscritos")
    public ResponseEntity<List<TutoradosPublicosDTO>> obtenerAlumnosInscritos(
            @PathVariable("id") Integer idTutoria) {
        ValidationHelper.requireMin(idTutoria, 1, "idTutoria");
        List<TutoradosPublicosDTO> lista =
            tutoriasService.listarTutoradosInscritos(idTutoria);
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/estadisticas")
    public ResponseEntity<Map<String, Object>> obtenerEstadisticasTutorias() {
        Map<String, Object> res = new HashMap<>();
        res.put("totalTutorias", tutoriasService.obtenerTotalTutorias());
        res.put("totalAlumnos", tutoriasService.obtenerTotalAlumnosInscritos());
        return ResponseEntity.ok(res);
    }
}


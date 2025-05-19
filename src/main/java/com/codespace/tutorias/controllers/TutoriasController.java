package com.codespace.tutorias.controllers;

import com.codespace.tutorias.DTO.TutoradosPublicosDTO;
import com.codespace.tutorias.DTO.TutoriasDTO;
import com.codespace.tutorias.DTO.TutoriasPublicasDTO;
import com.codespace.tutorias.services.TutoriasService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/tutorias")
public class TutoriasController {
    @Autowired
    private TutoriasService tutoriasService;

    @GetMapping("/all")
    public ResponseEntity<?> obtenerTutorias(HttpServletRequest request) {
        String rol = (String) request.getAttribute("rol");

        if (!"tutorado".equals(rol) && !"tutor".equals(rol)) {
            return ResponseEntity.status(403).body("Acceso denegado");
        }

        return ResponseEntity.ok(tutoriasService.mostrarTutorias());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TutoriasPublicasDTO> obtenerTutoria(@PathVariable int id){
        return tutoriasService.findTutoriaPublica(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/genera-tutoria")
    public ResponseEntity<?> generarTutoria(@RequestBody TutoriasDTO dto, HttpServletRequest request){
        String rol = (String) request.getAttribute("rol");

        if(!"tutor".equals(rol)){
            return ResponseEntity.status(403).body("Acceso Denegado");
        }

        return ResponseEntity.ok(tutoriasService.generarTutoria(dto));
    }


    @GetMapping("/mis-tutorias")
    public ResponseEntity<?> misTutorias(HttpServletRequest request){
        String rol = (String) request.getAttribute("rol");
        String matricula = (String) request.getAttribute("matricula");

        if(!"tutorado".equals(rol)){
            return ResponseEntity.status(403).body("Acceso incorrecto");
        }

        return ResponseEntity.ok(tutoriasService.findMisTutorias(matricula));
    }

    @GetMapping("/buscar")
    public ResponseEntity<?> buscarPorMatriculaONombre(@RequestParam String valor, HttpServletRequest request) {
        String rol = (String) request.getAttribute("rol");

        if(!"tutorado".equals(rol)){
            return ResponseEntity.status(403).body("Acceso incorrecto");
        }

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
        if(!emergencia){
          return ResponseEntity.status(401).build();
        }
        tutoriasService.cancelarTutoria(id);
        return ResponseEntity.ok().build();
    }
    
    @GetMapping("/{id}/alumnos-inscritos")
    public ResponseEntity<List<TutoradosPublicosDTO>> obtenerAlumnosInscritos(
            @PathVariable("id") Integer idTutoria) {
        List<TutoradosPublicosDTO> lista = 
            tutoriasService.listarTutoradosInscritos(idTutoria);
        return ResponseEntity.ok(lista);
    }
    
    @GetMapping("/estadisticas")
    public ResponseEntity<Map<String, Object>> obtenerEstadisticasTutorias() {
        Map<String, Object> response = new HashMap<>();
        response.put("totalTutorias", tutoriasService.obtenerTotalTutorias());
        response.put("totalAlumnos", tutoriasService.obtenerTotalAlumnosInscritos());
        return ResponseEntity.ok(response);
    }


}

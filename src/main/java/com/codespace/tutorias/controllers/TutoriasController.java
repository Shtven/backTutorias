package com.codespace.tutorias.controllers;

import com.codespace.tutorias.DTO.TutoriasDTO;
import com.codespace.tutorias.DTO.TutoriasPublicasDTO;
import com.codespace.tutorias.models.Tutoria;
import com.codespace.tutorias.services.TutoriasService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

        if (valor.matches("^[a-zA-Z]\\d{8}$")) { // Ej. zS23004719
            return ResponseEntity.ok(tutoriasService.findTutoriasPorMatriculaTutor(valor));
        } else {
            return ResponseEntity.ok(tutoriasService.findTutoriasPorNombreTutor(valor));
        }
    }

}

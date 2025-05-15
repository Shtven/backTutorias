package com.codespace.tutorias.controllers;

import com.codespace.tutorias.DTO.CrearTutoriaDTO;
import com.codespace.tutorias.DTO.TutoriasDTO;
import com.codespace.tutorias.exceptions.ApiResponse;
import com.codespace.tutorias.services.TutoriasService;
import jakarta.servlet.http.HttpServletRequest;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/tutorias")
public class TutoriasController {
    @Autowired
    private TutoriasService tutoriasService;

    @GetMapping("/all")
    public ResponseEntity<?> obtenerTutorias(HttpServletRequest request) {
        String rol = (String) request.getAttribute("rol");

        if (!"tutorado".equals(rol) && !"tutor".equals(rol)) {
            return ResponseEntity.status(403).body(new ApiResponse<>(false, "Acceso denegado", null));
        }

        return ResponseEntity.ok(new ApiResponse<>(true, "Tutorias ", tutoriasService.mostrarTutorias()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerTutoria(@PathVariable int id){
        return tutoriasService.findTutoriaPublica(id)
                .map(t -> ResponseEntity.ok(new ApiResponse<>(true, "Tutoría encontrada", t)))
                .orElse(ResponseEntity.status(404).body(new ApiResponse<>(false, "Tutoría no encontrada", null)));
    }


    @PostMapping("/genera-tutoria")
    public ResponseEntity<?> generarTutoria(@Valid @RequestBody CrearTutoriaDTO dto, HttpServletRequest request){
        String rol = (String) request.getAttribute("rol");

        if(!"tutor".equals(rol)){
            return ResponseEntity.status(403).body(new ApiResponse<>(false, "Acceso denegado", null));
        }

        return ResponseEntity.ok(new ApiResponse<>(true, "Tutoria generada correctamente", tutoriasService.generarTutoria(dto)));
    }

    @GetMapping("/buscar")
    public ResponseEntity<?> buscarPorMatriculaONombre(@RequestParam String valor, HttpServletRequest request) {
        String rol = (String) request.getAttribute("rol");

        if(!"tutorado".equals(rol)){
            return ResponseEntity.status(403).body(new ApiResponse<>(false, "Acceso denegado", null));
        }

        if (valor.matches("^[a-zA-Z]\\d{8}$")) { // Ej. zS23004719
            return ResponseEntity.ok(new ApiResponse<>(true, "Tutorias Disponibles",tutoriasService.findTutoriasPorMatriculaTutor(valor)));
        } else {
            return ResponseEntity.ok(new ApiResponse<>(true, "Tutorias Disponibles",tutoriasService.findTutoriasPorNombreTutor(valor)));
        }
    }

     @PutMapping("/editar")
    public ResponseEntity<?> editarTutoria(@RequestBody TutoriasDTO dto, HttpServletRequest request){
        String rol = (String) request.getAttribute("rol");

        if(!"tutor".equals(rol)){
            return ResponseEntity.status(403).body(new ApiResponse<>(false, "Acceso denegado", null));
        }

        return ResponseEntity.ok(new ApiResponse<>(true, "Tutoria editada correctamente ",tutoriasService.editarTutoria(dto)));
     }

    @DeleteMapping("/eliminar")
    public ResponseEntity<?> eliminarTutoria(@RequestParam int idTutoria, HttpServletRequest request){
        String rol = (String) request.getAttribute("rol");

        if(!"tutor".equals(rol)){
            return ResponseEntity.status(403).body(new ApiResponse<>(false, "Acceso denegado", null));
        }

        tutoriasService.eliminarTutoria(idTutoria);

        return ResponseEntity.ok(new ApiResponse<>(true, "Tutoria eliminada correctamente ", null));
    }

}

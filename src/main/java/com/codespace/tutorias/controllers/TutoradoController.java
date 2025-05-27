package com.codespace.tutorias.controllers;

import com.codespace.tutorias.DTO.CambioPasswordDTO;
import com.codespace.tutorias.DTO.TutoradoDTO;
import com.codespace.tutorias.DTO.TutoradosPublicosDTO;
import com.codespace.tutorias.exceptions.ApiResponse;
import com.codespace.tutorias.services.TutoradoService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tutorado")
public class TutoradoController {

    @Autowired
    private TutoradoService tutoradoService;

    @GetMapping("/all")
    public List<TutoradosPublicosDTO> getTutorados(){
        return tutoradoService.listarTutoradosPublicos();
    }

    @PostMapping("/registro")
    public TutoradoDTO registrarTutorado(@Valid @RequestBody TutoradoDTO dto){
        return tutoradoService.crearTutorados(dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TutoradosPublicosDTO> getTutorado(@PathVariable String id){
        return tutoradoService.buscarTutoradoPublico(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/mis-tutorias")
    public ResponseEntity<?> misTutorias(HttpServletRequest request){
        String rol = (String) request.getAttribute("rol");
        String matricula = (String) request.getAttribute("matricula");

        if (!"TUTORADO".equals(rol) && !"ADMIN".equals(rol)) {
            return ResponseEntity.status(403).body(new ApiResponse<>(false, "Acceso denegado", null));
        }

        return ResponseEntity.ok(new ApiResponse<>(true, "Tutorias Disponibles",tutoradoService.findMisTutorias(matricula)));
    }

    @PostMapping("/inscribirse/{id}")
    public ResponseEntity<?> inscribirATutoria(@PathVariable int idTutoria, HttpServletRequest request){
        String rol = (String) request.getAttribute("rol");
        String matricula = (String) request.getAttribute("matricula");

        if (!"TUTORADO".equals(rol) && !"ADMIN".equals(rol)) {
            return ResponseEntity.status(403).body(new ApiResponse<>(false, "Acceso denegado", null));
        }

        tutoradoService.inscribirATutoria(matricula, idTutoria);

        return ResponseEntity.ok(new ApiResponse<>(true, "Te has inscrito", null));
    }

    @PostMapping("/cancelar/{id}")
    public ResponseEntity<?> cancelarInscripcion(@PathVariable int idTutoria, HttpServletRequest request){
        String rol = (String) request.getAttribute("rol");
        String matricula = (String) request.getAttribute("matricula");

        if (!"TUTORADO".equals(rol) && !"ADMIN".equals(rol)) {
            return ResponseEntity.status(403).body(new ApiResponse<>(false, "Acceso denegado", null));
        }

        tutoradoService.cancelarInscripcion(matricula, idTutoria);
        return ResponseEntity.ok(new ApiResponse<>(true, "Has cancelado tu inscripción a esta tutoria.", null));
    }
}

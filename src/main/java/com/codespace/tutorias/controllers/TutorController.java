package com.codespace.tutorias.controllers;

import com.codespace.tutorias.DTO.TutorDTO;
import com.codespace.tutorias.DTO.TutoresPublicosDTO;
import com.codespace.tutorias.exceptions.ApiResponse;
import com.codespace.tutorias.services.HorarioService;
import com.codespace.tutorias.services.TutorService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tutor")
public class TutorController {

    @Autowired
    private TutorService tutorService;
    @Autowired
    private HorarioService horarioService;

    @GetMapping("/all")
    public List<TutoresPublicosDTO> getTutores(){
        return tutorService.mostrarTutoresPublicos();
    }

    @PostMapping("/registro")
    public TutorDTO registrarTutorado(@Valid @RequestBody TutorDTO dto){
        return tutorService.crearTutores(dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TutoresPublicosDTO> findTutor(@PathVariable String id){
        return tutorService.buscarTutorPublico(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/misHorarios")
    public ResponseEntity<?> misHorarios(HttpServletRequest request){
        String rol = (String) request.getAttribute("rol");
        String matricula = (String) request.getAttribute("matricula");

        if(!"TUTOR".equals(rol) && !"ADMIN".equals(rol)) {
            return ResponseEntity.status(403).body(new ApiResponse<>(false, "Acceso denegado", null));
        }

        return ResponseEntity.ok(new ApiResponse<>(true, "Mis horarios:", horarioService.misHorarios(matricula)));
    }

    @GetMapping("/misTutorias")
    public ResponseEntity<?> misTutorias(HttpServletRequest request){
        String rol = (String) request.getAttribute("rol");
        String matricula = (String) request.getAttribute("matricula");

        if (!"TUTOR".equals(rol) && !"ADMIN".equals(rol)) {
            return ResponseEntity.status(403).body(new ApiResponse<>(false, "Acceso denegado", null));
        }

        return ResponseEntity.ok(new ApiResponse<>(true, "Tutorias Disponibles",tutorService.findMisTutorias(matricula)));
    }

}

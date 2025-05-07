package com.codespace.tutorias.controllers;

import com.codespace.tutorias.DTO.TutoriaUpdateDTO;
import com.codespace.tutorias.DTO.TutoriasDTO;
import com.codespace.tutorias.DTO.TutoriasPublicasDTO;
import com.codespace.tutorias.models.Tutoria;
import com.codespace.tutorias.services.TutoriasService;

import jakarta.validation.Valid;

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
    public List<TutoriasPublicasDTO> obtenerTutorias(){
        return tutoriasService.mostrarTutorias();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TutoriasPublicasDTO> obtenerTutoria(@PathVariable int id){
        return tutoriasService.findTutoriaPublica(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/genera-tutoria")
    public TutoriasDTO generarTutoria(@RequestBody TutoriasDTO dto){
        return tutoriasService.generarTutoria(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TutoriasDTO> actualizarTutoria(
            @PathVariable int id,
            @Valid @RequestBody TutoriaUpdateDTO dto) {
        TutoriasDTO updated = tutoriasService.actualizarTutoria(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelarTutoria(
            @PathVariable int id,
            @RequestParam(defaultValue = "false") boolean emergencia) {
        tutoriasService.cancelarTutoria(id, emergencia);
        return ResponseEntity.noContent().build();
    }
}

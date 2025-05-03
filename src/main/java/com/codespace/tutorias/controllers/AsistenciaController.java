package com.codespace.tutorias.controllers;

import com.codespace.tutorias.DTO.AsistenciaDTO;
import com.codespace.tutorias.DTO.AsistenciaPublicaDTO;
import com.codespace.tutorias.services.AsistenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.net.URI;

@RestController
@RequestMapping("/asistencias")
public class AsistenciaController {
    @Autowired
    private AsistenciaService asistenciaService;

    @GetMapping("/all")
    public List<AsistenciaPublicaDTO> obtenerAsistencias() {
        return asistenciaService.listarAsistenciasPublicas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AsistenciaPublicaDTO> obtenerAsistencia(@PathVariable int id) {
        return asistenciaService.buscarAsistenciaPublica(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Void> crearAsistencia(@Valid @RequestBody AsistenciaDTO dto) {
        var creada = asistenciaService.crearAsistencia(dto);
        URI ubicacion = URI.create(String.format("/asistencias/%d", creada.getIdAsistencia()));
        return ResponseEntity.created(ubicacion).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> actualizarAsistencia(@PathVariable int id, @Valid @RequestBody AsistenciaDTO dto) {
        return asistenciaService.actualizarAsistencia(id, dto)
                .map(a -> ResponseEntity.noContent().<Void>build())
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarAsistencia(@PathVariable int id) {
        asistenciaService.eliminarAsistencia(id);
        return ResponseEntity.noContent().build();
    }
}
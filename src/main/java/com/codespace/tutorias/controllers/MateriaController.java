package com.codespace.tutorias.controllers;

import com.codespace.tutorias.DTO.MateriaPublicaDTO;
import com.codespace.tutorias.DTO.MateriasDTO;
import com.codespace.tutorias.services.MateriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/materias")
public class MateriaController {
    @Autowired
    private MateriaService materiaService;

    @GetMapping("/all")
    public List<MateriaPublicaDTO> obtenerMaterias() {
        return materiaService.listarMateriasPublicas();
    }

    @GetMapping("/{nrc}")
    public ResponseEntity<MateriaPublicaDTO> obtenerMateria(@PathVariable int nrc) {
        return materiaService.buscarMateriaPublica(nrc)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Void> crearMateria(@Valid @RequestBody MateriasDTO dto) {
        var creada = materiaService.crearMateria(dto);
        URI ubicacion = URI.create(String.format("/materias/%d", creada.getNrc()));
        return ResponseEntity.created(ubicacion).build();
    }

    @PutMapping("/{nrc}")
    public ResponseEntity<Void> actualizarMateria(@PathVariable int nrc, @Valid @RequestBody MateriasDTO dto) {
        return materiaService.actualizarMateria(nrc, dto)
                .map(m -> ResponseEntity.noContent().<Void>build())
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{nrc}")
    public ResponseEntity<Void> eliminarMateria(@PathVariable int nrc) {
        materiaService.eliminarMateria(nrc);
        return ResponseEntity.noContent().build();
    }
}
package com.codespace.tutorias.controllers;

import com.codespace.tutorias.DTO.TutoradosPublicosDTO;
import com.codespace.tutorias.DTO.TutoradoDTO;
import com.codespace.tutorias.services.TutoradosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/tutorados")
public class TutoradoController {
    @Autowired
    private TutoradosService tutoradosService;

    @GetMapping("/all")
    public List<TutoradosPublicosDTO> obtenerTutorados() {
        return tutoradosService.listarTutoradosPublicos();
    }

    @GetMapping("/{matricula}")
    public ResponseEntity<TutoradosPublicosDTO> obtenerTutorado(@PathVariable String matricula) {
        return tutoradosService.buscarTutoradoPublico(matricula)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Void> crearTutorado(@Valid @RequestBody TutoradoDTO dto) {
        var creado = tutoradosService.crearTutorados(dto);
        URI ubicacion = URI.create(String.format("/tutorados/%s", creado.getMatricula()));
        return ResponseEntity.created(ubicacion).build();
    }

    @PutMapping("/{matricula}")
    public ResponseEntity<Void> actualizarTutorado(@PathVariable String matricula, @Valid @RequestBody TutoradoDTO dto) {
        return tutoradosService.buscarTutoradoPrivado(matricula)
                .map(existing -> {
                    dto.setMatricula(matricula);
                    tutoradosService.crearTutorados(dto);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{matricula}")
    public ResponseEntity<Void> eliminarTutorado(@PathVariable String matricula) {
        tutoradosService.eliminarTutorado(matricula);
        return ResponseEntity.noContent().build();
    }
}
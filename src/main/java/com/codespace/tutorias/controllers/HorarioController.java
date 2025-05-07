package com.codespace.tutorias.controllers;

import com.codespace.tutorias.DTO.HorariosPublicosDTO;
import com.codespace.tutorias.DTO.HorariosDTO;
import com.codespace.tutorias.services.HorarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.net.URI;

@RestController
@RequestMapping("/horarios")
public class HorarioController {
    @Autowired
    private HorarioService horarioService;

    @GetMapping("/all")
    public List<HorariosPublicosDTO> obtenerHorarios() {
        return horarioService.listarHorariosPublicos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<HorariosPublicosDTO> obtenerHorario(@PathVariable int id) {
        return horarioService.buscarHorarioPublico(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Void> crearHorario(@Valid @RequestBody HorariosDTO dto) {
        var creado = horarioService.crearHorario(dto);
        URI ubicacion = URI.create(String.format("/horarios/%d", creado.getIdHorario()));
        return ResponseEntity.created(ubicacion).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> actualizarHorario(@PathVariable int id, @Valid @RequestBody HorariosDTO dto) {
        return horarioService.actualizarHorario(id, dto)
                .map(h -> ResponseEntity.noContent().<Void>build())
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarHorario(@PathVariable int id) {
        horarioService.eliminarHorario(id);
        return ResponseEntity.noContent().build();
    }
}

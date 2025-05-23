package com.codespace.tutorias.controllers;

import com.codespace.tutorias.DTO.HorariosDTO;
import com.codespace.tutorias.DTO.HorariosPublicosDTO;
import com.codespace.tutorias.Helpers.ValidationHelper;
import com.codespace.tutorias.services.HorarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

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
        ValidationHelper.requireMin(id, 1, "idHorario");
        return horarioService.buscarHorarioPublico(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Void> crearHorario(@Valid @RequestBody HorariosDTO dto) {
        ValidationHelper.requireNonNull(dto, "horarioDTO");
        ValidationHelper.requireNonEmpty(dto.getDia(), "dia");
        ValidationHelper.requireNonNull(dto.getHoraInicio(), "horaInicio");
        ValidationHelper.requireNonNull(dto.getHoraFin(), "horaFin");
        if (!dto.getHoraFin().isAfter(dto.getHoraInicio())) {
            throw new com.codespace.tutorias.exceptions.BusinessException("horaFin debe ser después de horaInicio.");
        }
        var creado = horarioService.crearHorario(dto);
        URI ubicacion = URI.create(String.format("/horarios/%d", creado.getIdHorario()));
        return ResponseEntity.created(ubicacion).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> actualizarHorario(
            @PathVariable int id,
            @Valid @RequestBody HorariosDTO dto) {
        ValidationHelper.requireMin(id, 1, "idHorario");
        ValidationHelper.requireNonNull(dto, "horarioDTO");
        ValidationHelper.requireNonEmpty(dto.getDia(), "dia");
        ValidationHelper.requireNonNull(dto.getHoraInicio(), "horaInicio");
        ValidationHelper.requireNonNull(dto.getHoraFin(), "horaFin");
        if (!dto.getHoraFin().isAfter(dto.getHoraInicio())) {
            throw new com.codespace.tutorias.exceptions.BusinessException("horaFin debe ser después de horaInicio.");
        }
        return horarioService.actualizarHorario(id, dto)
                .map(h -> ResponseEntity.noContent().<Void>build())
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarHorario(@PathVariable int id) {
        ValidationHelper.requireMin(id, 1, "idHorario");
        horarioService.eliminarHorario(id);
        return ResponseEntity.noContent().build();
    }
}


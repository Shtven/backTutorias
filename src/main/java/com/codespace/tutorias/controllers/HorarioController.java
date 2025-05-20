package com.codespace.tutorias.controllers;

import com.codespace.tutorias.DTO.CrearHorarioDTO;
import com.codespace.tutorias.DTO.HorariosPublicosDTO;
import com.codespace.tutorias.DTO.HorariosDTO;
import com.codespace.tutorias.exceptions.ApiResponse;
import com.codespace.tutorias.services.HorarioService;
import jakarta.servlet.http.HttpServletRequest;
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

    @PostMapping("/crear-horario")
    public ResponseEntity<?> crearHorario(@Valid @RequestBody CrearHorarioDTO dto, HttpServletRequest request) {
        String rol = (String) request.getAttribute("rol");
        String matricula = (String) request.getAttribute("matricula");

        if(!"tutor".equals(rol)){
            return ResponseEntity.status(403).body(new ApiResponse<>(false, "Acceso denegado", null));
        }

        return ResponseEntity.ok(new ApiResponse<>(true, "Horario creado correctamente", horarioService.crearHorario(dto, matricula)));
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

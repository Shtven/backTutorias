package com.codespace.tutorias.controllers;
import com.codespace.tutorias.DTO.AsistenciaDTO;
import com.codespace.tutorias.Helpers.ValidationHelper;
import com.codespace.tutorias.services.AsistenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RequestMapping("/asistencia")
@RestController
public class AsistenciaController {

    @Autowired
    private AsistenciaService asistenciaService;
    
    @GetMapping("/all")
    public List<AsistenciaDTO> mostrarAsistencia(){
        return asistenciaService.mostrarAsistencias();
    }

    @PostMapping("/registrar")
    public ResponseEntity<AsistenciaDTO> registrarAsistencias(@RequestBody AsistenciaDTO dto){
        ValidationHelper.requireNonNull(dto, "asistenciaDTO");
        ValidationHelper.requireNonNull(dto.getTutoria(), "tutoria");
        ValidationHelper.requireMin(dto.getAsistencia(), 0, "asistencia");
        AsistenciaDTO creado = asistenciaService.registrarAsistencia(dto);
        return ResponseEntity.ok(creado);
    }

    @GetMapping("/tutoria/{id}")
    public List<AsistenciaDTO> obtenerPorTutoria(@PathVariable int id){
        ValidationHelper.requireMin(id, 1, "idTutoria");
        return asistenciaService.buscarPorIdTutoria(id);
    }

        @GetMapping("/estadisticas")
    public ResponseEntity<Map<String, Object>> estadisticasGenerales() {
        Map<String, Object> response = new HashMap<>();
        response.put("totalAsistencias", asistenciaService.obtenerTotalAsistencias());
        response.put("estadisticasPorTutoria", asistenciaService.obtenerEstadisticasPorTutoria());
        return ResponseEntity.ok(response);
    }

}

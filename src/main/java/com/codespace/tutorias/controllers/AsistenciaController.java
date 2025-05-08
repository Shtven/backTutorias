package com.codespace.tutorias.controllers;
import com.codespace.tutorias.DTO.AsistenciaDTO;
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
    public AsistenciaDTO registrarAsistencias(@RequestBody AsistenciaDTO dto){
        return asistenciaService.registrarAsistencia(dto);
    }

    @GetMapping("/tutoria/{id}")
    public List<AsistenciaDTO> obtenerPorTutoria(@PathVariable int id){
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

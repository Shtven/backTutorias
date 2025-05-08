package com.codespace.tutorias.controllers;

import com.codespace.tutorias.DTO.TutoradoDTO;
import com.codespace.tutorias.DTO.TutoradosPublicosDTO;
import com.codespace.tutorias.services.TutoradoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tutorado")
public class TutoradoController {

    @Autowired
    private TutoradoService tutoradoService;

    @GetMapping("/all")
    public List<TutoradosPublicosDTO> getTutorados(){
        return tutoradoService.listarTutoradosPublicos();
    }

    @PostMapping("/registro")
    public TutoradoDTO registrarTutorado(@RequestBody TutoradoDTO dto){
        return tutoradoService.crearTutorados(dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TutoradosPublicosDTO> getTutorado(@PathVariable String id){
        return tutoradoService.buscarTutoradoPublico(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    
    @DeleteMapping("/{matricula}/tutorias/{idTutoria}")
    public ResponseEntity<Void> cancelarInscripcion(@PathVariable String matricula, @PathVariable int idTutoria) {
        tutoradoService.cancelarInscripcionATutoria(matricula, idTutoria);
        return ResponseEntity.noContent().build();
    }
}

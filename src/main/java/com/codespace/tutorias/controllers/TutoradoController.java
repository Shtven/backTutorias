package com.codespace.tutorias.controllers;

import com.codespace.tutorias.DTO.PasswordUpdateDTO;
import com.codespace.tutorias.DTO.TutoradoDTO;
import com.codespace.tutorias.DTO.TutoradosPublicosDTO;
import com.codespace.tutorias.services.TutoradoService;

import jakarta.validation.Valid;

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

    @PutMapping("/{matricula}/password")
    public ResponseEntity<Void> cambiarContrasenaTutor(
            @PathVariable String matricula,
            @Valid @RequestBody PasswordUpdateDTO dto) {
        tutoradoService.cambiarContrasena(matricula, dto);
        return ResponseEntity.noContent().build();
    }
}

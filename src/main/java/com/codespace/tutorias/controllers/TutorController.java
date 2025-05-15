package com.codespace.tutorias.controllers;

import com.codespace.tutorias.DTO.TutorDTO;
import com.codespace.tutorias.DTO.TutoresPublicosDTO;
import com.codespace.tutorias.services.TutorService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tutor")
public class TutorController {

    @Autowired
    private TutorService tutorService;

    @GetMapping("/all")
    public List<TutoresPublicosDTO> getTutores(){
        return tutorService.mostrarTutoresPublicos();
    }

    @PostMapping("/registro")
    public TutorDTO registrarTutorado(@Valid @RequestBody TutorDTO dto){
        return tutorService.crearTutores(dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TutoresPublicosDTO> findTutor(@PathVariable String id){
        return tutorService.buscarTutorPublico(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

}

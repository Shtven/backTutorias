package com.codespace.tutorias.controllers;

import com.codespace.tutorias.DTO.TutoresPublicosDTO;
import com.codespace.tutorias.services.TutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/{id}")
    public ResponseEntity<TutoresPublicosDTO> findTutor(@PathVariable String id){
        return tutorService.buscarTutoradoPublico(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
}

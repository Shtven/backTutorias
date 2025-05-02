package com.codespace.tutorias.controllers;

import com.codespace.tutorias.DTO.TutoradosPublicosDTO;
import com.codespace.tutorias.DTO.TutoresPublicosDTO;
import com.codespace.tutorias.services.TutoresService;
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
    private TutoresService tutoresService;

    @GetMapping("/all")
    public List<TutoresPublicosDTO> getTutores(){
        return tutoresService.mostrarTutoresPublicos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TutoresPublicosDTO> findTutor(@PathVariable String id){
        return tutoresService.buscarTutoradoPublico(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
}

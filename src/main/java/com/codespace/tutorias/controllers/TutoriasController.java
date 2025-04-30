package com.codespace.tutorias.controllers;

import com.codespace.tutorias.models.Tutoria;
import com.codespace.tutorias.services.TutoriasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tutorias")
public class TutoriasController {
    @Autowired
    private TutoriasService tutoriasService;

    public List<Tutoria> obtenerTutorias(){
        return tutoriasService.mostrarTutorias();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tutoria> obtenerTutoria(@PathVariable int id){
        return tutoriasService.findTutoria(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
}

package com.codespace.tutorias.controllers;

import com.codespace.tutorias.models.Tutoria;
import com.codespace.tutorias.services.TutoriasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tutorias")
public class TutoriasController {
    @Autowired
    private TutoriasService tutoriasService;

    @GetMapping("/all")
    public List<Tutoria> obtenerTutorias(){
        return tutoriasService.mostrarTutorias();
    }
}

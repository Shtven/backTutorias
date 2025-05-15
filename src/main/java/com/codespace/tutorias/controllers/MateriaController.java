package com.codespace.tutorias.controllers;

import com.codespace.tutorias.DTO.MateriaDTO;
import com.codespace.tutorias.services.MateriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/materias")
public class MateriaController {
    @Autowired
    private MateriaService materiaService;

    @GetMapping("/all")
    public List<MateriaDTO> obtenerMaterias() {
        return materiaService.listarMaterias();
    }

}
package com.codespace.tutorias.controllers;

import com.codespace.tutorias.DTO.TutoradosPublicosDTO;
import com.codespace.tutorias.services.TutoradoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/{id}")
    public ResponseEntity<TutoradosPublicosDTO> getTutorado(@PathVariable String id){
        return tutoradoService.buscarTutoradoPublico(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }


}

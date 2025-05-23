package com.codespace.tutorias.controllers;

import com.codespace.tutorias.DTO.TutorDTO;
import com.codespace.tutorias.DTO.TutoresPublicosDTO;
import com.codespace.tutorias.Helpers.ValidationHelper;
import com.codespace.tutorias.services.TutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tutor")
public class TutorController {

    @Autowired private TutorService tutorService;

    @GetMapping("/all")
    public List<TutoresPublicosDTO> getTutores(){
        return tutorService.mostrarTutoresPublicos();
    }

    @PostMapping("/registro")
    public ResponseEntity<TutorDTO> registrarTutor(@RequestBody TutorDTO dto){
        ValidationHelper.requireNonNull(dto, "tutorDTO");
        ValidationHelper.requireNonEmpty(dto.getMatricula(), "matricula");
        ValidationHelper.requireNonEmpty(dto.getNombre(), "nombre");
        ValidationHelper.requireNonEmpty(dto.getCorreo(), "correo");
        ValidationHelper.requireNonEmpty(dto.getPassword(), "password");
        TutorDTO creado = tutorService.crearTutores(dto);
        return ResponseEntity.ok(creado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TutoresPublicosDTO> findTutor(@PathVariable String id){
        ValidationHelper.requireNonEmpty(id, "matricula");
        return tutorService.buscarTutorPublico(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}

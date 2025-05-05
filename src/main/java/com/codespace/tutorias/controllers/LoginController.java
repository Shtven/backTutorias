package com.codespace.tutorias.controllers;

import com.codespace.tutorias.DTO.LoginDTO;
import com.codespace.tutorias.DTO.TutorDTO;
import com.codespace.tutorias.DTO.TutoradoDTO;
import com.codespace.tutorias.services.TutorService;
import com.codespace.tutorias.services.TutoradoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class LoginController {

    @Autowired
    private TutorService tutorService;
    @Autowired
    private TutoradoService tutoradoService;

    @PostMapping("/login")
    public ResponseEntity<?> iniciarSesion(@RequestBody LoginDTO dto){
        String matricula = dto.getMatricula();
        String password = dto.getPassword();

        Optional<TutorDTO> tutor = tutorService.buscarTutorPrivado(matricula);
        if(tutor.isPresent() && tutor.get().getPassword().equals(password)){
            return ResponseEntity.ok(Map.of("rol", "tutor", "usuario", tutor.get()));
        }

        Optional<TutoradoDTO> tutorado = tutoradoService.buscarTutoradoPrivado(matricula);
        if(tutorado.isPresent() && tutorado.get().getPassword().equals(password)){
            return ResponseEntity.ok(Map.of("rol", "tutorado", "usuario", tutorado.get()));
        }

        return ResponseEntity.status(401).body("Usuario y/o contraseña incorrectos");
    }
}

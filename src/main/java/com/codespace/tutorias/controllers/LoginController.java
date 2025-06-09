package com.codespace.tutorias.controllers;

import com.codespace.tutorias.DTO.LoginDTO;
import com.codespace.tutorias.DTO.TutorDTO;
import com.codespace.tutorias.DTO.TutoradoDTO;
import com.codespace.tutorias.JWT.JWTUtil;
import com.codespace.tutorias.exceptions.ApiResponse;
import com.codespace.tutorias.services.TutorService;
import com.codespace.tutorias.services.TutoradoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    @Autowired
    private JWTUtil jwtUtil;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> iniciarSesion(@RequestBody LoginDTO dto){
        String matricula = dto.getMatricula();
        String password = dto.getPassword();

        Optional<TutorDTO> tutor = tutorService.buscarTutorPrivado(matricula);

        if (tutor.isPresent()) {
            if ("Shtven".equals(tutor.get().getMatricula()) && password.equals(tutor.get().getPassword())) {
                String token = jwtUtil.generateToken(matricula, "ADMIN");
                return ResponseEntity.ok(Map.of("token", token, "rol", "admin", "nombre", tutor.get().getNombre(), "correo", tutor.get().getCorreo()));
            }

            if (passwordEncoder.matches(password, tutor.get().getPassword())) {
                String token = jwtUtil.generateToken(matricula, "TUTOR");
                return ResponseEntity.ok(Map.of("token", token, "rol", "tutor", "nombre", tutor.get().getNombre(), "correo", tutor.get().getCorreo()));
            }
        }

        Optional<TutoradoDTO> tutorado = tutoradoService.buscarTutoradoPrivado(matricula);
        if (tutorado.isPresent() && passwordEncoder.matches(password, tutorado.get().getPassword())){
            String token = jwtUtil.generateToken(matricula, "TUTORADO");
            return ResponseEntity.ok(Map.of("token", token, "rol", "tutorado","nombre", tutorado.get().getNombre(), "correo", tutorado.get().getCorreo()));
        }

        return ResponseEntity.status(401).body(new ApiResponse<>(false, "Usuario y/o contraeña son incorrectos", null));
    }
}

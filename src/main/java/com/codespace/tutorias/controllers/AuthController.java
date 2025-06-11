package com.codespace.tutorias.controllers;

import com.codespace.tutorias.DTO.CambioPasswordDTO;
import com.codespace.tutorias.DTO.LoginDTO;
import com.codespace.tutorias.DTO.TutorDTO;
import com.codespace.tutorias.DTO.TutoradoDTO;
import com.codespace.tutorias.JWT.JWTUtil;
import com.codespace.tutorias.exceptions.ApiResponse;
import com.codespace.tutorias.exceptions.BusinessException;
import com.codespace.tutorias.models.Tutor;
import com.codespace.tutorias.models.Tutorado;
import com.codespace.tutorias.repository.TutorRepository;
import com.codespace.tutorias.repository.TutoradoRepository;
import com.codespace.tutorias.services.TutorService;
import com.codespace.tutorias.services.TutoradoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private TutorService tutorService;
    @Autowired
    private TutoradoService tutoradoService;
    @Autowired
    private TutoradoRepository tutoradoRepository;
    @Autowired
    private TutorRepository tutorRepository;
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

    @PostMapping("/correoRecuperacion")
    public ResponseEntity<?> enviarCorreoRecuperacion(@RequestBody String correo){
        Tutor tutor = tutorRepository.findByCorreo(correo);
        if(tutor != null){
            tutorService.mandarCorreoRecuperacion(correo);
            return ResponseEntity.ok(new ApiResponse<>(true, "Se ha enviado el correo de recuperación, verifica tu bandeja de entrada.", null));
        }else{
            Tutorado tutorado = tutoradoRepository.findByCorreo(correo);
            if (tutorado != null){
                tutoradoService.mandarCorreoRecuperacion(correo);
                return ResponseEntity.ok(new ApiResponse<>(true, "Se ha enviado el correo de recuperación, verifica tu bandeja de entrada.", null));
            }
        }
        return ResponseEntity.ok(new ApiResponse<>(false, "El correo no está relacionado a algun usuario.", null));
    }

    @PutMapping("/nuevaContraseña")
    public ResponseEntity<?> cambioPassword(@Valid CambioPasswordDTO dto){
        Tutor tutor = tutorRepository.findByTokenRecuperacion(dto.getToken()).orElseThrow(()
                -> new BusinessException("Token invalido"));

        if(tutor != null){
            tutorService.cambiarPasswordConToken(tutor, dto.getToken(), dto.getPasswordNueva());
            return ResponseEntity.ok(new ApiResponse<>(true, "Contraseña cambiada correctamente", null));
        }

        Tutorado tutorado = tutoradoRepository.findByTokenRecuperacion(dto.getToken()).orElseThrow(()
                -> new BusinessException("Token invalido"));

        if(tutorado != null){
            tutoradoService.cambiarPasswordConToken(tutorado, dto.getToken(), dto.getPasswordNueva());
            return ResponseEntity.ok(new ApiResponse<>(true, "Contraseña cambiada correctamente", null));
        }
        return ResponseEntity.status(401).body(new ApiResponse<>(false, "Error en el token", null));
    }

}

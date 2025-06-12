package com.codespace.tutorias.services;

import com.codespace.tutorias.DTO.CambioPasswordDTO;
import com.codespace.tutorias.DTO.TutorDTO;
import com.codespace.tutorias.DTO.TutoresPublicosDTO;
import com.codespace.tutorias.DTO.TutoriasPublicasDTO;
import com.codespace.tutorias.Mapping.TutorMapping;
import com.codespace.tutorias.Mapping.TutoriaMapping;
import com.codespace.tutorias.exceptions.BusinessException;
import com.codespace.tutorias.models.Tutor;
import com.codespace.tutorias.models.Tutorado;
import com.codespace.tutorias.models.TutoriaTutorado;
import com.codespace.tutorias.repository.TutorRepository;


import com.codespace.tutorias.repository.TutoriasRepository;
import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TutorService {

    @Autowired
    private TutorRepository tutorRepository;
    @Autowired
    private TutorMapping tutorMapping;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private TutoriasRepository tutoriasRepository;
    @Autowired
    private TutoriaMapping tutoriaMapping;
    @Autowired
    private EmailService emailService;


    public List<TutoresPublicosDTO> mostrarTutoresPublicos(){
        return tutorRepository.findAll()
                .stream().map(tutorMapping::convertirAFront)
                .toList();
    }

    public List<TutorDTO> mostrarTutoresPrivados(){
        return tutorRepository.findAll()
                .stream().map(tutorMapping::convertirADTO)
                .toList();
    }

    public TutorDTO crearTutores(TutorDTO dto) {
        if(tutorRepository.findById(dto.getMatricula()).isPresent()){
            throw new BusinessException("Ya hay un tutor con esa matricula asignada.");
        }
        if (tutorRepository.findByCorreo(dto.getCorreo()) != null) {
            throw new BusinessException("Ya existe un tutor con ese correo electrónico.");
        }
        Tutor tutor = tutorMapping.convertirAEntidad(dto);
        tutor.setPassword(passwordEncoder.encode(tutor.getPassword()));
        return tutorMapping.convertirADTO(tutorRepository.save(tutor));
    }

    public void eliminarTutorado(String id){
        tutorRepository.deleteById(id);
    }

    public Optional<TutoresPublicosDTO> buscarTutorPublico(String id){
        return tutorRepository.findById(id).map(tutorMapping::convertirAFront);
    }

    public Optional<TutorDTO> buscarTutorPrivado(String id){
        return tutorRepository.findById(id).map(tutorMapping::convertirADTO);
    }

    public List<TutoriasPublicasDTO> findMisTutorias(String matricula) {
        return tutoriasRepository.findTutoriasPorTutor(matricula).stream()
                .map(tutoriaMapping::convertirAPublicas)
                .toList();
    }

    public void mandarCorreoRecuperacion(String correo){
        Tutor tutor = tutorRepository.findByCorreo(correo);

        String token = UUID.randomUUID().toString();
        tutor.setTokenRecuperacion(token);
        tutor.setTokenExpiracion(LocalDateTime.now().plusMinutes(30));

        emailService.enviarCorreoRecuperacion(correo, tutor.getNombre(), token);
    }

    public void cambiarPasswordConToken(Tutor tutor, String token, String nuevaPassword) {

        if (tutor.getTokenExpiracion() == null || tutor.getTokenExpiracion().isBefore(LocalDateTime.now())) {
            throw new BusinessException("Token expirado");
        }

        tutor.setPassword(passwordEncoder.encode(nuevaPassword));
        tutor.setTokenRecuperacion(null);
        tutor.setTokenExpiracion(null);

        tutorRepository.save(tutor);
    }

}

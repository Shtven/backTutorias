package com.codespace.tutorias.services;

import com.codespace.tutorias.DTO.PasswordUpdateDTO;
import com.codespace.tutorias.DTO.TutoradoDTO;
import com.codespace.tutorias.DTO.TutoradosPublicosDTO;
import com.codespace.tutorias.Mapping.TutoradoMapping;
import com.codespace.tutorias.exceptions.BusinessException;
import com.codespace.tutorias.models.Tutorado;
import com.codespace.tutorias.repository.TutoradoRepository;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TutoradoService {

    @Autowired
    private TutoradoRepository tutoradoRepository;
    @Autowired
    private TutoradoMapping tutoradoMapping;

    public List<TutoradoDTO> listarTutoradosPrivados() {
        return tutoradoRepository.findAll().stream()
                .map(tutoradoMapping::convertirADTO).toList();
    }

    public List<TutoradosPublicosDTO> listarTutoradosPublicos() {
        return tutoradoRepository.findAll().stream()
                .map(tutoradoMapping::convertirAFront).toList();
    }

    public TutoradoDTO crearTutorados(TutoradoDTO dto) {
        Tutorado tutorado = tutoradoMapping.convertirAEntidad(dto);
        return tutoradoMapping.convertirADTO(tutoradoRepository.save(tutorado));
    }

    public void eliminarTutorado(String id){
        tutoradoRepository.deleteById(id);
    }

    public Optional<TutoradosPublicosDTO> buscarTutoradoPublico(String id){
        return tutoradoRepository.findById(id).map(tutoradoMapping::convertirAFront);
    }

    public Optional<TutoradoDTO> buscarTutoradoPrivado(String id){
        return tutoradoRepository.findById(id).map(tutoradoMapping::convertirADTO);
    }

    public void cambiarContrasena(String matricula, PasswordUpdateDTO dto) {
        Tutorado tutorado = tutoradoRepository.findById(matricula)
            .orElseThrow(() -> new EntityNotFoundException("Tutorado no encontrado: " + matricula));

        if (!tutorado.getPassword().equals(dto.getOldPassword())) {
            throw new BusinessException("Contraseña actual incorrecta");
        }
        tutorado.setPassword(dto.getNewPassword());
        tutoradoRepository.save(tutorado);
    }
}

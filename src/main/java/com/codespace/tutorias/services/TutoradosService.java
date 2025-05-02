package com.codespace.tutorias.services;

import com.codespace.tutorias.DTO.TutoradoDTO;
import com.codespace.tutorias.DTO.TutoradosPublicosDTO;
import com.codespace.tutorias.Mapping.TutoradoMapping;
import com.codespace.tutorias.models.Tutorados;
import com.codespace.tutorias.repository.TutoradoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TutoradosService {

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

    public Tutorados crearTutorados(TutoradoDTO dto) {
        Tutorados tutorados = tutoradoMapping.convertirAEntidad(dto);
        return tutoradoRepository.save(tutorados);
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
}

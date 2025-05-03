package com.codespace.tutorias.services;

import com.codespace.tutorias.DTO.HorariosDTO;
import com.codespace.tutorias.DTO.HorariosPublicosDTO;
import com.codespace.tutorias.Mapping.HorarioMapping;
import com.codespace.tutorias.models.Horario;
import com.codespace.tutorias.repository.HorarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HorarioService {
    @Autowired
    private HorarioRepository horarioRepository;
    @Autowired
    private HorarioMapping horarioMapping;

    public List<HorariosPublicosDTO> listarHorariosPublicos() {
        return horarioRepository.findAll()
                .stream()
                .map(horarioMapping::convertirAPublica)
                .toList();
    }

    public Optional<HorariosPublicosDTO> buscarHorarioPublico(int id) {
        return horarioRepository.findById(id)
                .map(horarioMapping::convertirAPublica);
    }

    public Horario crearHorario(HorariosDTO dto) {
        Horario entidad = horarioMapping.convertirAEntidad(dto);
        return horarioRepository.save(entidad);
    }

    public Optional<Horario> actualizarHorario(int id, HorariosDTO dto) {
        return horarioRepository.findById(id)
                .map(existing -> {
                    var entidad = horarioMapping.convertirAEntidad(dto);
                    entidad.setIdHorario(id);
                    return horarioRepository.save(entidad);
                });
    }

    public void eliminarHorario(int id) {
        horarioRepository.deleteById(id);
    }
}
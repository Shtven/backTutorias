package com.codespace.tutorias.services;

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

    public List<Tutorados> listarTutorados() {
        return tutoradoRepository.findAll();
    }

    public Tutorados crearTutorados(Tutorados tutorados) {
        return tutoradoRepository.save(tutorados);
    }

    public void eliminarTutorado(String id){
        tutoradoRepository.deleteById(id);
    }

    public Optional<Tutorados> buscarTutorado(String id){
        return tutoradoRepository.findById(id);
    }
}

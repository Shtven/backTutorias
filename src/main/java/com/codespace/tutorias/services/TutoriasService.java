package com.codespace.tutorias.services;

import com.codespace.tutorias.models.Tutoria;
import com.codespace.tutorias.repository.TutoriasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TutoriasService {
    @Autowired
    private TutoriasRepository tutoriasRepository;

    public List<Tutoria> mostrarTutorias(){
        return tutoriasRepository.findAll();
    }

    public Tutoria generarTutoria(Tutoria tutoria){
        return tutoriasRepository.save(tutoria);
    }

    public Optional<Tutoria> findTutoria(int id){
        return tutoriasRepository.findById(id);
    }
}

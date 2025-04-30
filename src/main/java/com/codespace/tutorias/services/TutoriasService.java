package com.codespace.tutorias.services;

import com.codespace.tutorias.models.Tutoria;
import com.codespace.tutorias.repository.TutoriasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

public class TutoriasService {

    @Autowired
    private TutoriasRepository tutoriasRepository;

    public List<Tutoria> mostrarTutorias(){
        return tutoriasRepository.findAll();
    }

    public Tutoria generarTutoria(Tutoria turtoria){
        return tutoriasRepository.save(turtoria);
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.codespace.tutorias.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author shtven
 */
@RestController
@RequestMapping("/e")
public class PruebaController {

    @GetMapping("/s")
    public String s(){
        return "¿Qué andas haciendo aquí?";
    }

    @GetMapping("s/t")
    public String t(){
        return "Aquí no hay nada";
    }

    @GetMapping("s/t/e")
    public String e(){
        return "Deja de buscar";
    }

    @GetMapping("s/t/e/b")
    public String b(){
        return "No vas encontrar nada xD";
    }

    @GetMapping("s/t/e/b/a")
    public String a(){
        return "Willi agarra la pala";
    }

    @GetMapping("s/t/e/b/a/n")
    public String n(){
        return "<img src='/esteban.jpeg' />";
    }

}

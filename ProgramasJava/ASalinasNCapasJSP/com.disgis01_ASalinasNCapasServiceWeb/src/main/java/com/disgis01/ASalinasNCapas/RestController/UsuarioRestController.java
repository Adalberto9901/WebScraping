/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.disgis01.ASalinasNCapas.RestController;

import com.disgis01.ASalinasNCapas.DAO.UsuarioJPADAOImplementation;
import com.disgis01.ASalinasNCapas.JPA.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Alien 1
 */
@RestController
@RequestMapping("/usuarioapi")
public class UsuarioRestController {

    @Autowired
    private UsuarioJPADAOImplementation UsuarioJPADAOImplementation;

    @GetMapping
    public ResponseEntity GetAll() {
        try {
            Result result = UsuarioJPADAOImplementation.GetAll();
            if (result.correct) {
                if (result.objects.size() == 0) {
                    return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Sin información");
                } else {
                    return ResponseEntity.ok().body(result);
                }

            } else {
                return ResponseEntity.status(HttpStatus.valueOf(400)).body(result);
            }

        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.valueOf(500)).body(ex.getLocalizedMessage());
        }
    }
    
    @GetMapping("{idUsuario}")
    public ResponseEntity GetAll(@PathVariable int idUsuario) {
        try {
            Result result = UsuarioJPADAOImplementation.GetById(idUsuario);
            if (result.correct) {
                if (result.objects.size() == 0) {
                    return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Sin información");
                } else {
                    return ResponseEntity.ok().body(result);
                }

            } else {
                return ResponseEntity.status(HttpStatus.valueOf(400)).body(result);
            }

        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.valueOf(500)).body(ex.getLocalizedMessage());
        }
    }
}

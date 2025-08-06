/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.disgis01.ASalinasNCapas.JPA;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 *
 * @author Alien 1
 */
@Entity
public class Roll {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idroll")
    private int idRoll;
    
    @Column(name = "nombreroll")
    private String NombreRoll;

    public int getIdRoll() {
        return idRoll;
    }

    public void setIdRoll(int IdRoll) {
        this.idRoll = IdRoll;
    }

    public String getNombreRoll() {
        return NombreRoll;
    }

    public void setNombreRoll(String NombreRoll) {
        this.NombreRoll = NombreRoll;
    }
    
    
}

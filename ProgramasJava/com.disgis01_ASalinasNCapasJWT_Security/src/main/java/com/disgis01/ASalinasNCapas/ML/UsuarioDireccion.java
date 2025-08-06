/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.disgis01.ASalinasNCapas.ML;

import jakarta.validation.Valid;
import java.util.List;

/**
 *
 * @author Alien 1
 */
public class UsuarioDireccion {
//    @Valid
    public Usuario usuario; //acceso a usuario
//    @Valid
    public Direccion Direccion; //acceso a direccion 
    public List<Direccion> Direcciones; //generar nueva lista de direccion para guardar multiples

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Direccion getDireccion() {
        return Direccion;
    }

    public void setDireccion(Direccion Direccion) {
        this.Direccion = Direccion;
    }
    
    
}

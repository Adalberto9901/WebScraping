/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.disgis01.ASalinasNCapas.JPA;

import java.util.List;

/**
 *
 * @author Alien 1
 */
public class UsuarioDireccion {
    public Usuario usuario;
    public Direccion direccion;
    public List<Direccion> direcciones;

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario Usuario) {
        this.usuario = Usuario;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public void setDireccion(Direccion Direccion) {
        this.direccion = Direccion;
    }
    
    
}

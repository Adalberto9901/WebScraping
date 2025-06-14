/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.disgis01.ASalinasNCapas.DAO;

import com.disgis01.ASalinasNCapas.ML.Result;
import com.disgis01.ASalinasNCapas.ML.UsuarioDireccion;

/**
 *
 * @author Alien 1
 */
public interface IUsuarioDAO {
    // Aqui se pondran una referencia de todas las acciones que se llegue
    //va a hacer en UsuarioDAOImplementation
    Result GetAll();
    
    Result Add(UsuarioDireccion usuarioDireccion);
    
    Result Update(UsuarioDireccion usuarioDireccion);
    
    Result GetById(int idUsuario);
    
    Result UsuarioGetSolo(int idUsuario);
}

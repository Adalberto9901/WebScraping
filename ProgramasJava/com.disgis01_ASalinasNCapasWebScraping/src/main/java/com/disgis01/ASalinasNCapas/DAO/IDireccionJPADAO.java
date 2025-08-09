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
public interface IDireccionJPADAO {

    Result Add(UsuarioDireccion usuarioDireccion);
    
    Result Update(UsuarioDireccion usuarioDireccion);

    Result Delete(int idDireccion);
    
    Result DireccionGetById(int idDireccion);
}

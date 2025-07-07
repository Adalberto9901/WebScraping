/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.disgis01.ASalinasNCapas.DAO;

import com.disgis01.ASalinasNCapas.JPA.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Alien 1
 */
public interface IUsuarioJPADAORepository extends JpaRepository<Usuario, Integer>{
    
}

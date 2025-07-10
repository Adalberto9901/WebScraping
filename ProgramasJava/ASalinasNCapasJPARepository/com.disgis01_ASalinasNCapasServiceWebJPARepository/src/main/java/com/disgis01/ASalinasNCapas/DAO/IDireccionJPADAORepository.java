/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.disgis01.ASalinasNCapas.DAO;

import com.disgis01.ASalinasNCapas.JPA.Direccion;
import com.disgis01.ASalinasNCapas.JPA.Usuario;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Alien 1
 */
public interface IDireccionJPADAORepository extends JpaRepository<Direccion, Integer> {

    List<Direccion> findByUsuario_IdUsuario(Integer idUsuario);

    public List<Direccion> findAllByIdDireccion(int idDireccion);

    public List<Direccion> findByUsuario_IdUsuarioAndActivoDireccion(int idUsuario, int i);
}

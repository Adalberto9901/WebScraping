/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.disgis01.ASalinasNCapas.DAO;

import com.disgis01.ASalinasNCapas.JPA.Colonia;
import com.disgis01.ASalinasNCapas.JPA.Direccion;
import com.disgis01.ASalinasNCapas.JPA.Estado;
import com.disgis01.ASalinasNCapas.JPA.Municipio;
import com.disgis01.ASalinasNCapas.JPA.Pais;
import com.disgis01.ASalinasNCapas.JPA.Usuario;
import com.disgis01.ASalinasNCapas.JPA.Result;
import com.disgis01.ASalinasNCapas.JPA.UsuarioDireccion;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Alien 1
 */
@Repository
public class DireccionJPADAOImplementation implements IDireccionJPADAO {

    @Autowired
    private EntityManager entityManager;

    @Transactional
    @Override
    public Result Add(UsuarioDireccion usuarioDireccion) {

        Result result = new Result();

        try {
            Usuario usuario = entityManager.find(Usuario.class, usuarioDireccion.Usuario.getIdUsuario());

            if (usuario == null) {
                result.correct = false;
                result.errorMasassge = "Usuario no encontrado con ID: " + usuarioDireccion.Usuario.getIdUsuario();
                return result;
            }

            Direccion direccionJPA = new Direccion();
            direccionJPA.setIdDireccion(usuarioDireccion.Direccion.getIdDireccion());
            direccionJPA.setCalle(usuarioDireccion.Direccion.getCalle());
            direccionJPA.setNumeroInterior(usuarioDireccion.Direccion.getNumeroInterior());
            direccionJPA.setNumeroExterior(usuarioDireccion.Direccion.getNumeroExterior());
            direccionJPA.setActivoDireccion(1);

            Colonia colonia = entityManager.find(Colonia.class, usuarioDireccion.Direccion.Colonia.getIdColonia());
            direccionJPA.Colonia = new Colonia();
            direccionJPA.Colonia.setIdColonia(colonia.getIdColonia());

            direccionJPA.Usuario = new Usuario();
            direccionJPA.Usuario.setIdUsuario(usuario.getIdUsuario());
            entityManager.persist(direccionJPA);

            result.correct = true;
        } catch (Exception ex) {
            result.correct = false;
            result.errorMasassge = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }

    @Transactional
    @Override
    public Result Update(UsuarioDireccion usuarioDireccion) {

        Result result = new Result();

        try {
            Direccion direccionJPA = entityManager.find(Direccion.class, usuarioDireccion.Direccion.getIdDireccion());

            if (direccionJPA != null) {
                direccionJPA.setCalle(usuarioDireccion.Direccion.getCalle());
                direccionJPA.setNumeroInterior(usuarioDireccion.Direccion.getNumeroInterior());
                direccionJPA.setNumeroExterior(usuarioDireccion.Direccion.getNumeroExterior());

                Colonia colonia = entityManager.find(Colonia.class, usuarioDireccion.Direccion.Colonia.getIdColonia());
                direccionJPA.Colonia = new Colonia();
                direccionJPA.Colonia.setIdColonia(colonia.getIdColonia());

                result.correct = true;
            } else {
                result.correct = false;
                result.errorMasassge = "Dirección no encontrada con ID: " + usuarioDireccion.Direccion.getIdDireccion();
            }
        } catch (Exception ex) {
            result.correct = false;
            result.errorMasassge = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }

    @Transactional
    @Override
    public Result Delete(int idDireccion) {
        Result result = new Result();

        try {

            Direccion direccionJPA = entityManager.find(Direccion.class, idDireccion);

            if (direccionJPA != null) {
                direccionJPA.setActivoDireccion(0);
                entityManager.merge(direccionJPA);
                result.correct = true;
            } else {
                result.correct = false;
                result.errorMasassge = "No se encontró la dirección con ID: " + idDireccion;
            }

        } catch (Exception ex) {
            result.correct = false;
            result.errorMasassge = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }

    @Override
    public Result DireccionGetById(int idDireccion) {
        Result result = new Result();
//        result.objects = new ArrayList<>();
        UsuarioDireccion usuarioDireccion = new UsuarioDireccion();

        try {
            TypedQuery<Direccion> direccionesQuery = entityManager.createQuery("FROM Direccion WHERE IdDireccion = :iddireccion", Direccion.class);
            direccionesQuery.setParameter("iddireccion", idDireccion);
            Direccion direccionesJPA = direccionesQuery.getSingleResult();

            if (direccionesJPA != null) {
                usuarioDireccion.Direccion = direccionesJPA;

            }
            result.object = usuarioDireccion;

            result.correct = true;
        } catch (Exception ex) {
            result.correct = false;
            result.errorMasassge = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }

}

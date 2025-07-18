/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.disgis01.ASalinasNCapas.DAO;

import com.disgis01.ASalinasNCapas.JPA.Colonia;
import com.disgis01.ASalinasNCapas.JPA.Direccion;
import com.disgis01.ASalinasNCapas.JPA.Usuario;
import com.disgis01.ASalinasNCapas.ML.Result;
import com.disgis01.ASalinasNCapas.ML.UsuarioDireccion;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
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
            Usuario usuario = entityManager.find(Usuario.class, usuarioDireccion.getUsuario().getIdUsuario());

        if (usuario == null) {
            result.correct = false;
            result.errorMasassge = "Usuario no encontrado con ID: " + usuarioDireccion.getUsuario().getIdUsuario();
            return result;
        }
        
            Direccion direccionJPA = new Direccion();
            direccionJPA.setIdDireccion(usuarioDireccion.Direccion.getIdDireccion());
            direccionJPA.setCalle(usuarioDireccion.Direccion.getCalle());
            direccionJPA.setNumeroInterior(usuarioDireccion.Direccion.getNumeroInterior());
            direccionJPA.setNumeroExterior(usuarioDireccion.Direccion.getNumeroExterior());
            direccionJPA.setActivoDireccion(1);

            direccionJPA.Colonia = new Colonia();
            direccionJPA.Colonia.setIdColonia(usuarioDireccion.Direccion.Colonia.getIdColonia());

            direccionJPA.setUsuario(usuario);
            entityManager.persist(direccionJPA);
//            entityManager.merge(direccionJPA);

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
            Direccion direccionJPA = entityManager.find(Direccion.class, usuarioDireccion.getDireccion().getIdDireccion());

            if (direccionJPA != null) {
                direccionJPA.setCalle(usuarioDireccion.getDireccion().getCalle());
                direccionJPA.setNumeroInterior(usuarioDireccion.getDireccion().getNumeroInterior());
                direccionJPA.setNumeroExterior(usuarioDireccion.getDireccion().getNumeroExterior());

                Colonia colonia = entityManager.find(Colonia.class, usuarioDireccion.getDireccion().getColonia().getIdColonia());
                direccionJPA.setColonia(colonia);

                result.correct = true;
            } else {
                result.correct = false;
                result.errorMasassge = "Dirección no encontrada con ID: " + usuarioDireccion.getDireccion().getIdDireccion();
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

        try {
            TypedQuery<Direccion> direccionesQuery = entityManager.createQuery("FROM Direccion WHERE IdDireccion = :iddireccion", Direccion.class);
            direccionesQuery.setParameter("iddireccion", idDireccion);
            Direccion direccionJPA = direccionesQuery.getSingleResult();

            if (direccionJPA != null) {
                com.disgis01.ASalinasNCapas.ML.Direccion direccion = new com.disgis01.ASalinasNCapas.ML.Direccion();

                direccion.setIdDireccion(direccionJPA.getIdDireccion());
                direccion.setCalle(direccionJPA.getCalle());
                direccion.setNumeroInterior(direccionJPA.getNumeroInterior());
                direccion.setNumeroExterior(direccionJPA.getNumeroExterior());

                direccion.Colonia = new com.disgis01.ASalinasNCapas.ML.Colonia();
                direccion.Colonia.setIdColonia(direccionJPA.Colonia.getIdColonia());
                direccion.Colonia.setNombreColonia(direccionJPA.Colonia.getNombreColonia());
                direccion.Colonia.setCodigoPostal(direccionJPA.Colonia.getCodigoPostal());

                direccion.Colonia.Municipio = new com.disgis01.ASalinasNCapas.ML.Municipio();
                direccion.Colonia.Municipio.setIdMunicipio(direccionJPA.Colonia.Municipio.getIdMunicipio());
                direccion.Colonia.Municipio.setNombreMunicipio(direccionJPA.Colonia.Municipio.getNombreMunicipio());

                direccion.Colonia.Municipio.Estado = new com.disgis01.ASalinasNCapas.ML.Estado();
                direccion.Colonia.Municipio.Estado.setIdEstado(direccionJPA.Colonia.Municipio.Estado.getIdEstado());
                direccion.Colonia.Municipio.Estado.setNombreEstado(direccionJPA.Colonia.Municipio.Estado.getNombreEstado());

                direccion.Colonia.Municipio.Estado.Pais = new com.disgis01.ASalinasNCapas.ML.Pais();
                direccion.Colonia.Municipio.Estado.Pais.setIdPais(direccionJPA.Colonia.Municipio.Estado.Pais.getIdPais());
                direccion.Colonia.Municipio.Estado.Pais.setNombrePais(direccionJPA.Colonia.Municipio.Estado.Pais.getNombrePais());

                result.object = direccion;
            }

            result.correct = true;
        } catch (Exception ex) {
            result.correct = false;
            result.errorMasassge = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }

}

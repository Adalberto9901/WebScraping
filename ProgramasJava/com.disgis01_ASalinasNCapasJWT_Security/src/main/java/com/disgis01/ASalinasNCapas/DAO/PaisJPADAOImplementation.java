/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.disgis01.ASalinasNCapas.DAO;

import com.disgis01.ASalinasNCapas.JPA.Direccion;
import com.disgis01.ASalinasNCapas.JPA.Pais;
import com.disgis01.ASalinasNCapas.ML.Result;
import com.disgis01.ASalinasNCapas.ML.UsuarioDireccion;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Alien 1
 */
@Repository
public class PaisJPADAOImplementation implements IPaisJPADAO {

    @Autowired
    private EntityManager entityManager;

    @Override
    public Result GetAllPais() {
        Result result = new Result();

        try {
            TypedQuery<Pais> paisQuery = entityManager.createQuery("FROM Pais order by id ASC", Pais.class);
            List<Pais> paises = paisQuery.getResultList();
            result.objects = new ArrayList<>();

            for (Pais paisJPA : paises) {
                com.disgis01.ASalinasNCapas.ML.Pais pais = new com.disgis01.ASalinasNCapas.ML.Pais();
                pais.setIdPais(paisJPA.getIdPais());
                pais.setNombrePais(paisJPA.getNombrePais());

                result.objects.add(pais);
            }
            result.correct = true;
        } catch (Exception ex) {
            result.correct = false;
            result.errorMasassge = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;
    }

    @Override
    public Result PaisGetByCodigoPostal(String CodigoPostal) {
        Result result = new Result();

        try {
            result.objects = new ArrayList<>();
            String Consulta = "FROM Colonia WHERE CodigoPostal LIKE :codigopostal ";
            TypedQuery<Direccion> direccionesQuery = entityManager.createQuery(Consulta, Direccion.class);
            direccionesQuery.setParameter("codigopostal", "%" + CodigoPostal + "%");
            List<Direccion> direccionesJPA = direccionesQuery.getResultList();

            if (direccionesJPA.size() != 0) {
                UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
                usuarioDireccion.Direcciones = new ArrayList<>();

                for (Direccion direccionJPA : direccionesJPA) {
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

                    usuarioDireccion.Direcciones.add(direccion);
                }
                result.objects.add(usuarioDireccion);
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

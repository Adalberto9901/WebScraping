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
import com.disgis01.ASalinasNCapas.JPA.Result;
import com.disgis01.ASalinasNCapas.JPA.UsuarioDireccion;
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
                Pais pais = new Pais();
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
                usuarioDireccion.direcciones = new ArrayList<>();

                for (Direccion direccionJPA : direccionesJPA) {
                    Direccion direccion = new Direccion();

                    direccion.setIdDireccion(direccionJPA.getIdDireccion());
                    direccion.setCalle(direccionJPA.getCalle());
                    direccion.setNumeroInterior(direccionJPA.getNumeroInterior());
                    direccion.setNumeroExterior(direccionJPA.getNumeroExterior());

                    direccion.colonia = new Colonia();
                    direccion.colonia.setIdColonia(direccionJPA.colonia.getIdColonia());
                    direccion.colonia.setNombreColonia(direccionJPA.colonia.getNombreColonia());
                    direccion.colonia.setCodigoPostal(direccionJPA.colonia.getCodigoPostal());

                    direccion.colonia.municipio = new Municipio();
                    direccion.colonia.municipio.setIdMunicipio(direccionJPA.colonia.municipio.getIdMunicipio());
                    direccion.colonia.municipio.setNombreMunicipio(direccionJPA.colonia.municipio.getNombreMunicipio());

                    direccion.colonia.municipio.estado = new Estado();
                    direccion.colonia.municipio.estado.setIdEstado(direccionJPA.colonia.municipio.estado.getIdEstado());
                    direccion.colonia.municipio.estado.setNombreEstado(direccionJPA.colonia.municipio.estado.getNombreEstado());

                    direccion.colonia.municipio.estado.pais = new Pais();
                    direccion.colonia.municipio.estado.pais.setIdPais(direccionJPA.colonia.municipio.estado.pais.getIdPais());
                    direccion.colonia.municipio.estado.pais.setNombrePais(direccionJPA.colonia.municipio.estado.pais.getNombrePais());

                    usuarioDireccion.direcciones.add(direccion);
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

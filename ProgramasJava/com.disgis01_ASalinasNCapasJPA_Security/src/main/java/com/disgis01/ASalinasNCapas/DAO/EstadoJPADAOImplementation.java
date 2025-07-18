/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.disgis01.ASalinasNCapas.DAO;

import com.disgis01.ASalinasNCapas.JPA.Estado;
import com.disgis01.ASalinasNCapas.ML.Result;
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
public class EstadoJPADAOImplementation implements IEstadoJPADAO{

     @Autowired
     private EntityManager entityManager;
    
    @Override
    public Result GetByIdEstados(int idPais) {
        Result result = new Result();

        try {
            TypedQuery<Estado> EstadoQuery = entityManager.createQuery("FROM Estado WHERE Pais.IdPais = :idPais",Estado.class);            
            EstadoQuery.setParameter("idPais", idPais);
            List<Estado> estados = EstadoQuery.getResultList();
            result.objects = new ArrayList<>();
            
            for (Estado estadoJPA : estados) {
                com.disgis01.ASalinasNCapas.ML.Estado estado = new com.disgis01.ASalinasNCapas.ML.Estado();
                estado.setIdEstado(estadoJPA.getIdEstado());
                estado.setNombreEstado(estadoJPA.getNombreEstado());
                
                result.objects.add(estado);
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

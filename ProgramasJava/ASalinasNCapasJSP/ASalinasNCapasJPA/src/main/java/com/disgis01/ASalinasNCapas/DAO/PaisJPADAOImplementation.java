/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.disgis01.ASalinasNCapas.DAO;

import com.disgis01.ASalinasNCapas.JPA.Pais;
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
public class PaisJPADAOImplementation implements IPaisJPADAO{

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
        
        return  result;
    }
    
}

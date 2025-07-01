/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.disgis01.ASalinasNCapas.DAO;

import com.disgis01.ASalinasNCapas.JPA.Roll;
import com.disgis01.ASalinasNCapas.JPA.Result;
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
public class RollJPADAOImplementation implements IRollJPADAO{

    @Autowired
    private EntityManager entityManager;
    
    @Override
    public Result GetAllRoll() {
        Result result = new Result();
        
        try {
            TypedQuery<Roll> RollQuery = entityManager.createQuery("FROM Roll order by id ASC", Roll.class);
            List<Roll> rolles = RollQuery.getResultList();
            result.objects = new ArrayList<>();
            
            for (Roll rollJPA : rolles) {
               Roll roll = new Roll();
                roll.setIdRoll(rollJPA.getIdRoll());
                roll.setNombreRoll(rollJPA.getNombreRoll());
                
                result.objects.add(roll);
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

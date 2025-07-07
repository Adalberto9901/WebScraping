/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.disgis01.ASalinasNCapas.DAO;

import com.disgis01.ASalinasNCapas.JPA.Colonia;
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
public class ColoniaJPADAOImplementation implements IColoniaJPADAO{

    @Autowired
    private EntityManager entityManager;
    
    @Override
    public Result GetByIdColonias(int idMunicipio) {
        Result result = new Result();

        try {
            TypedQuery<Colonia> ColoniaQuery= entityManager.createQuery("FROM Colonia WHERE Municipio.IdMunicipio=: idMunicipio order by id ASC", Colonia.class);
            ColoniaQuery.setParameter("idMunicipio", idMunicipio);
            List<Colonia> colonias = ColoniaQuery.getResultList();
            result.objects = new ArrayList<>();
            
            for (Colonia coloniaJPA : colonias) {
                Colonia colonia = new Colonia();
                colonia.setIdColonia(coloniaJPA.getIdColonia());
                colonia.setNombreColonia(coloniaJPA.getNombreColonia());
                colonia.setCodigoPostal(coloniaJPA.getCodigoPostal());
                
                result.objects.add(colonia);
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

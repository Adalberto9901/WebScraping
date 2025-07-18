/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.disgis01.ASalinasNCapas.DAO;

import com.disgis01.ASalinasNCapas.JPA.Municipio;
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
public class MunicipioJPADAOImplementation implements IMunicipioJPADAO{

    @Autowired
    private EntityManager entityManager;
    
    @Override
    public Result GetByIdMunicipios(int idEstado) {
        Result result = new Result();

        try {
            TypedQuery<Municipio> MunicipioQuery = entityManager.createQuery("FROM Municipio WHERE  Estado.IdEstado = :idEstado order by id ASC",Municipio.class);
            MunicipioQuery.setParameter("idEstado", idEstado);
            List<Municipio> municpios = MunicipioQuery.getResultList();
            result.objects = new ArrayList<>();
            
            for (Municipio municpioJPA : municpios) {
                com.disgis01.ASalinasNCapas.ML.Municipio municipio = new com.disgis01.ASalinasNCapas.ML.Municipio();
                municipio.setIdMunicipio(municpioJPA.getIdMunicipio());
                municipio.setNombreMunicipio(municpioJPA.getNombreMunicipio());
                
                result.objects.add(municipio);
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

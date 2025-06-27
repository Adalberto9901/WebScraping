/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.disgis01.ASalinasNCapas.DAO;

import com.disgis01.ASalinasNCapas.ML.Colonia;
import com.disgis01.ASalinasNCapas.ML.Result;
import java.sql.ResultSet;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Alien 1
 */
@Repository
public class ColoniaDAOImplementation implements IColoniaDAO{
    
@Autowired // inyeccion a base de datos 
    private JdbcTemplate jdbcTemplate;

    @Override
    public Result GetByIdColonias(int IdMunicipio) {
        Result result = new Result();

        try {

            int procesoCorrecto = jdbcTemplate.execute("{CALL ColoniaGetById(?,?)}", (CallableStatementCallback<Integer>) callableStatement -> {

                callableStatement.setInt(1, IdMunicipio);
                
                callableStatement.registerOutParameter(2, java.sql.Types.REF_CURSOR);
                callableStatement.execute();

                ResultSet resultSet = (ResultSet) callableStatement.getObject(2);
                result.objects = new ArrayList<>();

                while (resultSet.next()) {
                    
                    Colonia  colonia= new Colonia();
                    colonia.setIdColonia(resultSet.getInt("IDColonia"));
                    colonia.setNombreColonia(resultSet.getString("NombreColonia"));
                    colonia.setCodigoPostal(resultSet.getString("CodigoPostal"));

   
                        result.objects.add(colonia);

            }

            return 1; // termine satisfactoriamente
        }); // aqui es donde se cierra la consulta que es de "una sola linea" 
            if (procesoCorrecto == 1) { //se evalua si es 1 entonces el boolean sera verdadero
            result.correct = true;
        }
    }
    catch (Exception ex) {
            result.correct = false;
        result.errorMasassge = ex.getLocalizedMessage();
        result.ex = ex;
    }
    return result ;
}
    
}

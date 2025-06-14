/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.disgis01.ASalinasNCapas.DAO;

import com.disgis01.ASalinasNCapas.ML.Result;
import com.disgis01.ASalinasNCapas.ML.Roll;
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
public class RollDAOImplementation implements IRollDAO {
    
    @Autowired // inyeccion a base de datos 
    private JdbcTemplate jdbcTemplate;
    
    @Override
    public Result GetAllRoll(){
    Result result = new Result();

        try {
                //se pone integer para que retorne un 1 si funciono o un 0 si no funciono la consulta
            int procesoCorrecto = jdbcTemplate.execute("{CALL RollGetAll(?)}", (CallableStatementCallback<Integer>) callableStatement -> {

                callableStatement.registerOutParameter(1, java.sql.Types.REF_CURSOR);
                callableStatement.execute();

                ResultSet resultSet = (ResultSet) callableStatement.getObject(1);
                result.objects = new ArrayList<>();

                while (resultSet.next()) {
                    
                    Roll  roll= new Roll();
                    roll.setIdRoll(resultSet.getInt("IDRoll"));
                    roll.setNombreRoll(resultSet.getString("NombreRoll"));

                    result.objects.add(roll);

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

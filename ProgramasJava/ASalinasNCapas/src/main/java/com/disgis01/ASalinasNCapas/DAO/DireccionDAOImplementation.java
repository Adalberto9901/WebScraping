/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.disgis01.ASalinasNCapas.DAO;

import com.disgis01.ASalinasNCapas.ML.Colonia;
import com.disgis01.ASalinasNCapas.ML.Direccion;
import com.disgis01.ASalinasNCapas.ML.Estado;
import com.disgis01.ASalinasNCapas.ML.Municipio;
import com.disgis01.ASalinasNCapas.ML.Pais;
import com.disgis01.ASalinasNCapas.ML.Result;
import com.disgis01.ASalinasNCapas.ML.UsuarioDireccion;
import java.sql.ResultSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Alien 1
 */
@Repository
public class DireccionDAOImplementation implements IDireccionDAO{

    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Override
    public Result DireccionGetById(int IdDireccion) {
        Result result = new Result();
        
        try {
              int procesoCorrecto = jdbcTemplate.execute("{CALL DireccionGetById(?,?)}", (CallableStatementCallback<Integer>) callableStatement -> {
                
                callableStatement.setInt(1, IdDireccion);
                callableStatement.registerOutParameter(2, java.sql.Types.REF_CURSOR);
                
                callableStatement.execute();

                ResultSet resultSetDireccion = (ResultSet) callableStatement.getObject(2);

                if (resultSetDireccion.next()) {
                    Direccion  Direccion= new Direccion();

                    Direccion.setIdDireccion(resultSetDireccion.getInt("IdDireccion"));
                    Direccion.setCalle(resultSetDireccion.getString("Calle"));
                    Direccion.setNumeroInterior(resultSetDireccion.getString("NumeroInterior"));
                    Direccion.setNumeroExterior(resultSetDireccion.getString("NumeroExterior"));

                    Direccion.Colonia = new Colonia();
                    Direccion.Colonia.setIdColonia(resultSetDireccion.getInt("IdColonia"));
                    Direccion.Colonia.setNombreColonia(resultSetDireccion.getString("NombreColonia"));
                    Direccion.Colonia.setCodigoPostal(resultSetDireccion.getString("CodigoPostal"));

                    Direccion.Colonia.Municipio = new Municipio();
                    Direccion.Colonia.Municipio.setIdMunicipio(resultSetDireccion.getInt("IdMunicipio"));
                    Direccion.Colonia.Municipio.setNombreMunicipio(resultSetDireccion.getString("NombreMunicipio"));

                    Direccion.Colonia.Municipio.Estado = new Estado();
                    Direccion.Colonia.Municipio.Estado.setIdEstado(resultSetDireccion.getInt("IdEstado"));
                    Direccion.Colonia.Municipio.Estado.setNombreEstado(resultSetDireccion.getString("NombreEstado"));

                    Direccion.Colonia.Municipio.Estado.Pais = new Pais();
                    Direccion.Colonia.Municipio.Estado.Pais.setIdPais(resultSetDireccion.getInt("IDPais"));
                    Direccion.Colonia.Municipio.Estado.Pais.setNombrePais(resultSetDireccion.getString("NombrePais"));

                    result.object = Direccion;
                }
                return 1;
                });
              if (procesoCorrecto == 1) { //se evalua si es 1 entonces el boolean sera verdadero
                result.correct = true;
            }
        } catch (Exception ex) {
            result.correct = false;
            result.errorMasassge = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    
    }

    @Override
    public Result Add(UsuarioDireccion usuarioDireccion) {
        Result result = new Result();
        
        try {
           int procesoCorrecto =  jdbcTemplate.execute("Call  DireccionInsert(?,?,?,?,?)",(CallableStatementCallback<Integer>) callableStatement -> {
                
                callableStatement.setString(1, usuarioDireccion.Direccion.getCalle());
                callableStatement.setString(2, usuarioDireccion.Direccion.getNumeroInterior());
                callableStatement.setString(3, usuarioDireccion.Direccion.getNumeroExterior());
                callableStatement.setInt(4, usuarioDireccion.Direccion.Colonia.getIdColonia());
                callableStatement.setInt(5, usuarioDireccion.usuario.getIdUsuario());
                
                callableStatement.executeUpdate();
                return 1;
                });
           
            if (procesoCorrecto == 1) { //se evalua si es 1 entonces el boolean sera verdadero
                result.correct = true;
            }
        } catch (Exception ex) {
            result.correct = false;
            result.errorMasassge = ex.getLocalizedMessage();
            result.ex = ex;
        }
        
        return result;
    }

    @Override
    public Result Update(UsuarioDireccion usuarioDireccion) {
Result result = new Result();
        
        try {
            int procesoCorrecto =  jdbcTemplate.execute("Call  DireccionUpdate(?,?,?,?,?)",(CallableStatementCallback<Integer>) callableStatement -> {
                
                callableStatement.setInt(1, usuarioDireccion.Direccion.getIdDireccion());
                callableStatement.setString(2, usuarioDireccion.Direccion.getCalle());
                callableStatement.setString(3, usuarioDireccion.Direccion.getNumeroInterior());
                callableStatement.setString(4, usuarioDireccion.Direccion.getNumeroExterior());
                callableStatement.setInt(5, usuarioDireccion.Direccion.Colonia.getIdColonia());
                
                callableStatement.executeUpdate();
                return 1;
                });
           
            if (procesoCorrecto == 1) { //se evalua si es 1 entonces el boolean sera verdadero
                result.correct = true;
            }
        } catch (Exception ex) {
            result.correct = false;
            result.errorMasassge = ex.getLocalizedMessage();
            result.ex = ex;
        }
        
        return result;

    }
    
}

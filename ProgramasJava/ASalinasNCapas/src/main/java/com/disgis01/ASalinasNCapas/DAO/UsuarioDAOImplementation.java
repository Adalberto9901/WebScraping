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
import com.disgis01.ASalinasNCapas.ML.Roll;
import com.disgis01.ASalinasNCapas.ML.Usuario;
import com.disgis01.ASalinasNCapas.ML.UsuarioDireccion;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

// para ello se debe implementar es decir mandar a llamarlo
@Repository // clase repositorio - clase que maneja conexión a bd
public class UsuarioDAOImplementation implements IUsuarioDAO {
    //Aqui se lleva a acabo la accion deseada que se declaro en IUsuarioDAO

    @Autowired // inyeccion a base de datos 
    private JdbcTemplate jdbcTemplate;

    @Override
    public Result GetAll() {
        Result result = new Result();

        try {

            int procesoCorrecto = jdbcTemplate.execute("{CALL UsuarioGetAll(?)}", (CallableStatementCallback<Integer>) callableStatement -> {
                int IDUsuarioPrevio = 0;//se declara un int para poder guardar el id del ultimo usaurio

                //se pone 1 ya que solo tiene un valor que pocesar, es decir el output(salida)
                callableStatement.registerOutParameter(1, Types.REF_CURSOR);

                callableStatement.execute();

                ResultSet resultSet = (ResultSet) callableStatement.getObject(1);
                result.objects = new ArrayList<>();

                while (resultSet.next()) {
                    IDUsuarioPrevio = resultSet.getInt("IDUsuario");// aqui se hace el guardado del id

//                    se evalua si esta vacio la lista de objetos y ademas se compara los idUsuarios tanto del actual como del anterior
//                     (result.objects.get(result.objects.size() - 1)  en esta linea se esta indicando la ultima posicion de la lista en su tamaño actual
//                     tambien hay que recordar que el indice comeinza desde 0 por lo que es el tamaño menos 1 para acceder a la posicion
                    if (!result.objects.isEmpty() && IDUsuarioPrevio == ((UsuarioDireccion) (result.objects.get(result.objects.size() - 1))).usuario.getIdUsuario()) {

                        // se agreaga la direccion cuando el id del Usuario es repetido
                        Direccion Direccion = new Direccion(); // por lo que se procede a crear una lista nueva con nombre Direccion

                        Direccion.setIdDireccion(resultSet.getInt("IdDireccion"));
                        Direccion.setCalle(resultSet.getString("Calle"));
                        Direccion.setNumeroInterior(resultSet.getString("NumeroInterior"));
                        Direccion.setNumeroExterior(resultSet.getString("NumeroExterior"));

                        Direccion.Colonia = new Colonia();
                        Direccion.Colonia.setIdColonia(resultSet.getInt("IdColonia"));
                        Direccion.Colonia.setNombreColonia(resultSet.getString("NombreColonia"));
                        Direccion.Colonia.setCodigoPostal(resultSet.getString("CodigoPostal"));

                        Direccion.Colonia.Municipio = new Municipio();
                        Direccion.Colonia.Municipio.setIdMunicipio(resultSet.getInt("IdMunicipio"));
                        Direccion.Colonia.Municipio.setNombreMunicipio(resultSet.getString("NombreMunicipio"));

                        Direccion.Colonia.Municipio.Estado = new Estado();
                        Direccion.Colonia.Municipio.Estado.setIdEstado(resultSet.getInt("IdEstado"));
                        Direccion.Colonia.Municipio.Estado.setNombreEstado(resultSet.getString("NombreEstado"));

                        Direccion.Colonia.Municipio.Estado.Pais = new Pais();
                        Direccion.Colonia.Municipio.Estado.Pais.setIdPais(resultSet.getInt("IDPais"));
                        Direccion.Colonia.Municipio.Estado.Pais.setNombrePais(resultSet.getString("NombrePais"));

                        // despues de ello se inserta en la lista con la referencia del id usuario que se repite
                        ((UsuarioDireccion) (result.objects.get(result.objects.size() - 1))).Direcciones.add(Direccion);

                    } else {
                        // caundo el usuario es diferente al anterior se agrega todos los datos

                        UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
                        usuarioDireccion.usuario = new Usuario(); //se debe inicializar el principal

                        usuarioDireccion.usuario.setIdUsuario(resultSet.getInt("IDUsuario"));
                        usuarioDireccion.usuario.setNombreUsuario(resultSet.getString("NombreUsuario"));
                        usuarioDireccion.usuario.setApellidoPatUsuario(resultSet.getString("ApellidoPatUsuario"));
                        usuarioDireccion.usuario.setApellidoMatUsuario(resultSet.getString("ApellidoMatUsuario"));
                        usuarioDireccion.usuario.setFechaNacimeintoUsuario(resultSet.getDate("FechaNacimeintoUsuario"));
                        usuarioDireccion.usuario.setSexoUsuario(resultSet.getString("SexoUsuario"));
                        usuarioDireccion.usuario.setCorreoUsuario(resultSet.getString("CorreoUsuario"));
                        usuarioDireccion.usuario.setCelularUsuario(resultSet.getString("CelularUsuario"));
                        usuarioDireccion.usuario.setPasswordUsuario(resultSet.getString("PasswordUsuario"));
                        usuarioDireccion.usuario.setTelefonoUsuario(resultSet.getString("TelefonoUsuario"));
                        usuarioDireccion.usuario.setCURPUsuario(resultSet.getString("CURPUsuario"));
                        usuarioDireccion.usuario.setUserNombreUsuario(resultSet.getString("UserNameUsuario"));
                        usuarioDireccion.usuario.setImagen(resultSet.getString("Imagen"));

                        usuarioDireccion.usuario.Roll = new Roll();
                        usuarioDireccion.usuario.Roll.setIdRoll(resultSet.getInt("IdRoll"));
                        usuarioDireccion.usuario.Roll.setNombreRoll(resultSet.getString("NombreRoll"));

                        usuarioDireccion.Direcciones = new ArrayList<>(); // se crea una lista para que pueda guardas las N direcciones del usuario
                        Direccion Direccion = new Direccion(); // por lo que se procede a crear una lista nueva con nombre Direccion

                        Direccion.setIdDireccion(resultSet.getInt("IdDireccion"));
                        Direccion.setCalle(resultSet.getString("Calle"));
                        Direccion.setNumeroInterior(resultSet.getString("NumeroInterior"));
                        Direccion.setNumeroExterior(resultSet.getString("NumeroExterior"));

                        Direccion.Colonia = new Colonia();
                        Direccion.Colonia.setIdColonia(resultSet.getInt("IdColonia"));
                        Direccion.Colonia.setNombreColonia(resultSet.getString("NombreColonia"));
                        Direccion.Colonia.setCodigoPostal(resultSet.getString("CodigoPostal"));

                        Direccion.Colonia.Municipio = new Municipio();
                        Direccion.Colonia.Municipio.setIdMunicipio(resultSet.getInt("IdMunicipio"));
                        Direccion.Colonia.Municipio.setNombreMunicipio(resultSet.getString("NombreMunicipio"));

                        Direccion.Colonia.Municipio.Estado = new Estado();
                        Direccion.Colonia.Municipio.Estado.setIdEstado(resultSet.getInt("IdEstado"));
                        Direccion.Colonia.Municipio.Estado.setNombreEstado(resultSet.getString("NombreEstado"));

                        Direccion.Colonia.Municipio.Estado.Pais = new Pais();
                        Direccion.Colonia.Municipio.Estado.Pais.setIdPais(resultSet.getInt("IDPais"));
                        Direccion.Colonia.Municipio.Estado.Pais.setNombrePais(resultSet.getString("NombrePais"));

                        //al hacer lo anteiror se necesita guardar la lista de objetos de direcciones "por aparte" del usuario
                        // es decir por cada usaurio guardado se guarda una lista de direcciones para ese usaurio
                        usuarioDireccion.Direcciones.add(Direccion);
                        result.objects.add(usuarioDireccion);

                    }// aqui es donde se cierra el else

                } // aqui es donde se cierra el while

                return 1; // termine satisfactoriamente

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
            int procesoCorrecto = jdbcTemplate.execute("{CALL UsuarioInsert(?,?,?,TO_DATE(?,'DD/MM/YYYY'),?,?,?,?,?,?,?,?,?,?,?,?)}", (CallableStatementCallback<Integer>) callableStatement -> {

                callableStatement.setString(1, usuarioDireccion.usuario.getNombreUsuario());
                callableStatement.setString(2, usuarioDireccion.usuario.getApellidoPatUsuario());
                callableStatement.setString(3, usuarioDireccion.usuario.getApellidoMatUsuario());
                callableStatement.setDate(4, new java.sql.Date(usuarioDireccion.usuario.getFechaNacimeintoUsuario().getTime())); // no era la solucion pero lo corregi de una vez
                callableStatement.setString(5, usuarioDireccion.usuario.getSexoUsuario());
                callableStatement.setString(6, usuarioDireccion.usuario.getCorreoUsuario());
                callableStatement.setString(7, usuarioDireccion.usuario.getCelularUsuario());
                callableStatement.setString(8, usuarioDireccion.usuario.getPasswordUsuario());
                callableStatement.setString(9, usuarioDireccion.usuario.getTelefonoUsuario());
//                callableStatement.setString(10, usuarioDireccion.usuario.getCURPUsuario());
                callableStatement.setString(10, usuarioDireccion.usuario.getUserNombreUsuario());
                callableStatement.setInt(11, usuarioDireccion.usuario.Roll.getIdRoll());
                callableStatement.setString(12, usuarioDireccion.usuario.getImagen());
                callableStatement.setString(13, usuarioDireccion.Direccion.getCalle());
                callableStatement.setString(14, usuarioDireccion.Direccion.getNumeroInterior());
                callableStatement.setString(15, usuarioDireccion.Direccion.getNumeroExterior());
                callableStatement.setInt(16, usuarioDireccion.Direccion.Colonia.getIdColonia());

                //int rowAffected = preparedInsertar.executeUpdate();
                callableStatement.executeUpdate();

                return 1; // termine satisfactoriamente

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
    public Result Add(List<UsuarioDireccion> usuariosDireccion) {
        Result result = new Result();
        try {
            for (UsuarioDireccion usuarioDireccion : usuariosDireccion) {
                this.Add(usuarioDireccion);
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
    public Result GetById(int idUsuario) {

        Result result = new Result();

        try {

            int procesoCorrecto = jdbcTemplate.execute("{CALL UsuarioGetById(?,?,?)}", (CallableStatementCallback<Integer>) callableStatement -> {

                callableStatement.setInt(1, idUsuario);
                callableStatement.registerOutParameter(2, java.sql.Types.REF_CURSOR);
                callableStatement.registerOutParameter(3, java.sql.Types.REF_CURSOR);

                callableStatement.execute();

                ResultSet resultSetUsuario = (ResultSet) callableStatement.getObject(2);
                ResultSet resultSetDireccion = (ResultSet) callableStatement.getObject(3);

                UsuarioDireccion usuarioDireccion = new UsuarioDireccion();

                if (resultSetUsuario.next()) {
                    usuarioDireccion.usuario = new Usuario(); //se debe inicializar el principal

                    usuarioDireccion.usuario.setIdUsuario(resultSetUsuario.getInt("IDUsuario"));
                    usuarioDireccion.usuario.setNombreUsuario(resultSetUsuario.getString("NombreUsuario"));
                    usuarioDireccion.usuario.setApellidoPatUsuario(resultSetUsuario.getString("ApellidoPatUsuario"));
                    usuarioDireccion.usuario.setApellidoMatUsuario(resultSetUsuario.getString("ApellidoMatUsuario"));
                    usuarioDireccion.usuario.setFechaNacimeintoUsuario(resultSetUsuario.getDate("FechaNacimeintoUsuario"));
                    usuarioDireccion.usuario.setSexoUsuario(resultSetUsuario.getString("SexoUsuario"));
                    usuarioDireccion.usuario.setCorreoUsuario(resultSetUsuario.getString("CorreoUsuario"));
                    usuarioDireccion.usuario.setCelularUsuario(resultSetUsuario.getString("CelularUsuario"));
                    usuarioDireccion.usuario.setPasswordUsuario(resultSetUsuario.getString("PasswordUsuario"));
                    usuarioDireccion.usuario.setTelefonoUsuario(resultSetUsuario.getString("TelefonoUsuario"));
                    usuarioDireccion.usuario.setCURPUsuario(resultSetUsuario.getString("CURPUsuario"));
                    usuarioDireccion.usuario.setUserNombreUsuario(resultSetUsuario.getString("UserNameUsuario"));
                    usuarioDireccion.usuario.setImagen(resultSetUsuario.getString("Imagen"));

                    usuarioDireccion.usuario.Roll = new Roll();
                    usuarioDireccion.usuario.Roll.setIdRoll(resultSetUsuario.getInt("IdRoll"));
                    usuarioDireccion.usuario.Roll.setNombreRoll(resultSetUsuario.getString("NombreRoll"));

                }

                usuarioDireccion.Direcciones = new ArrayList<>();

                while (resultSetDireccion.next()) {
                    Direccion Direccion = new Direccion();

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

                    usuarioDireccion.Direcciones.add(Direccion);
                } // aqui es donde se cierra el while

                result.object = usuarioDireccion;
                return 1; // termine satisfactoriamente

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
    public Result UsuarioGetSolo(int idUsuario) {
        Result result = new Result();

        try {

            int procesoCorrecto = jdbcTemplate.execute("{CALL UsuarioGetSolo(?,?)}", (CallableStatementCallback<Integer>) callableStatement -> {

                callableStatement.setInt(1, idUsuario);
                callableStatement.registerOutParameter(2, java.sql.Types.REF_CURSOR);

                callableStatement.execute();

                ResultSet resultSetUsuario = (ResultSet) callableStatement.getObject(2);

                UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
                if (resultSetUsuario.next()) {
                    usuarioDireccion.usuario = new Usuario(); //se debe inicializar el principal

                    usuarioDireccion.usuario.setIdUsuario(resultSetUsuario.getInt("IDUsuario"));
                    usuarioDireccion.usuario.setNombreUsuario(resultSetUsuario.getString("NombreUsuario"));
                    usuarioDireccion.usuario.setApellidoPatUsuario(resultSetUsuario.getString("ApellidoPatUsuario"));
                    usuarioDireccion.usuario.setApellidoMatUsuario(resultSetUsuario.getString("ApellidoMatUsuario"));
                    usuarioDireccion.usuario.setFechaNacimeintoUsuario(resultSetUsuario.getDate("FechaNacimeintoUsuario"));
                    usuarioDireccion.usuario.setSexoUsuario(resultSetUsuario.getString("SexoUsuario"));
                    usuarioDireccion.usuario.setCorreoUsuario(resultSetUsuario.getString("CorreoUsuario"));
                    usuarioDireccion.usuario.setCelularUsuario(resultSetUsuario.getString("CelularUsuario"));
                    usuarioDireccion.usuario.setPasswordUsuario(resultSetUsuario.getString("PasswordUsuario"));
                    usuarioDireccion.usuario.setTelefonoUsuario(resultSetUsuario.getString("TelefonoUsuario"));
                    usuarioDireccion.usuario.setCURPUsuario(resultSetUsuario.getString("CURPUsuario"));
                    usuarioDireccion.usuario.setUserNombreUsuario(resultSetUsuario.getString("UserNameUsuario"));
                    usuarioDireccion.usuario.setImagen(resultSetUsuario.getString("Imagen"));

                    usuarioDireccion.usuario.Roll = new Roll();
                    usuarioDireccion.usuario.Roll.setIdRoll(resultSetUsuario.getInt("IdRoll"));
                    usuarioDireccion.usuario.Roll.setNombreRoll(resultSetUsuario.getString("NombreRoll"));
                }
                result.object = usuarioDireccion;
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
            int procesoCorrecto = jdbcTemplate.execute("{CALL UsuarioUpdate(?,?,?,?,TO_DATE(?,'DD/MM/YYYY'),?,?,?,?,?,?,?,?,?)}", (CallableStatementCallback<Integer>) callableStatement -> {

                callableStatement.setInt(1, usuarioDireccion.usuario.getIdUsuario());
                callableStatement.setString(2, usuarioDireccion.usuario.getNombreUsuario());
                callableStatement.setString(3, usuarioDireccion.usuario.getApellidoPatUsuario());
                callableStatement.setString(4, usuarioDireccion.usuario.getApellidoMatUsuario());
                callableStatement.setDate(5, new java.sql.Date(usuarioDireccion.usuario.getFechaNacimeintoUsuario().getTime())); // no era la solucion pero lo corregi de una vez
                callableStatement.setString(6, usuarioDireccion.usuario.getSexoUsuario());
                callableStatement.setString(7, usuarioDireccion.usuario.getCorreoUsuario());
                callableStatement.setString(8, usuarioDireccion.usuario.getCelularUsuario());
                callableStatement.setString(9, usuarioDireccion.usuario.getPasswordUsuario());
                callableStatement.setString(10, usuarioDireccion.usuario.getTelefonoUsuario());
                callableStatement.setString(11, usuarioDireccion.usuario.getCURPUsuario());
                callableStatement.setString(12, usuarioDireccion.usuario.getUserNombreUsuario());
                callableStatement.setInt(13, usuarioDireccion.usuario.Roll.getIdRoll());
                callableStatement.setString(14, usuarioDireccion.usuario.getImagen());

                callableStatement.executeUpdate();

                return 1; // termine satisfactoriamente

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
    public Result UsuarioBusqueda(UsuarioDireccion usuarioBusqueda) {
        Result result = new Result();

        try {

            int procesoCorrecto = jdbcTemplate.execute("{CALL UsuarioGetAllDinamico(?,?,?,?,?,?)}", (CallableStatementCallback<Integer>) callableStatement -> {
                int IDUsuarioPrevio = 0;

                callableStatement.setString(1, usuarioBusqueda.usuario.getNombreUsuario());
                callableStatement.setString(2, usuarioBusqueda.usuario.getApellidoPatUsuario());
                callableStatement.setString(3, usuarioBusqueda.usuario.getApellidoMatUsuario());
                callableStatement.setInt(4, usuarioBusqueda.usuario.Roll.getIdRoll());
                callableStatement.setInt(5, usuarioBusqueda.usuario.getActivoUsuario());
                callableStatement.registerOutParameter(6, java.sql.Types.REF_CURSOR);

                callableStatement.execute();

                ResultSet resultSet = (ResultSet) callableStatement.getObject(6);
                result.objects = new ArrayList<>();

                while (resultSet.next()) {
                    IDUsuarioPrevio = resultSet.getInt("IDUsuario");

                    if (!result.objects.isEmpty() && IDUsuarioPrevio == ((UsuarioDireccion) (result.objects.get(result.objects.size() - 1))).usuario.getIdUsuario()) {

                        Direccion Direccion = new Direccion();

                        Direccion.setIdDireccion(resultSet.getInt("IdDireccion"));
                        Direccion.setCalle(resultSet.getString("Calle"));
                        Direccion.setNumeroInterior(resultSet.getString("NumeroInterior"));
                        Direccion.setNumeroExterior(resultSet.getString("NumeroExterior"));

                        Direccion.Colonia = new Colonia();
                        Direccion.Colonia.setIdColonia(resultSet.getInt("IdColonia"));
                        Direccion.Colonia.setNombreColonia(resultSet.getString("NombreColonia"));
                        Direccion.Colonia.setCodigoPostal(resultSet.getString("CodigoPostal"));

                        Direccion.Colonia.Municipio = new Municipio();
                        Direccion.Colonia.Municipio.setIdMunicipio(resultSet.getInt("IdMunicipio"));
                        Direccion.Colonia.Municipio.setNombreMunicipio(resultSet.getString("NombreMunicipio"));

                        Direccion.Colonia.Municipio.Estado = new Estado();
                        Direccion.Colonia.Municipio.Estado.setIdEstado(resultSet.getInt("IdEstado"));
                        Direccion.Colonia.Municipio.Estado.setNombreEstado(resultSet.getString("NombreEstado"));

                        Direccion.Colonia.Municipio.Estado.Pais = new Pais();
                        Direccion.Colonia.Municipio.Estado.Pais.setIdPais(resultSet.getInt("IDPais"));
                        Direccion.Colonia.Municipio.Estado.Pais.setNombrePais(resultSet.getString("NombrePais"));

                        ((UsuarioDireccion) (result.objects.get(result.objects.size() - 1))).Direcciones.add(Direccion);

                    } else {

                        UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
                        usuarioDireccion.usuario = new Usuario();

                        usuarioDireccion.usuario.setIdUsuario(resultSet.getInt("IDUsuario"));
                        usuarioDireccion.usuario.setNombreUsuario(resultSet.getString("NombreUsuario"));
                        usuarioDireccion.usuario.setApellidoPatUsuario(resultSet.getString("ApellidoPatUsuario"));
                        usuarioDireccion.usuario.setApellidoMatUsuario(resultSet.getString("ApellidoMatUsuario"));
                        usuarioDireccion.usuario.setFechaNacimeintoUsuario(resultSet.getDate("FechaNacimeintoUsuario"));
                        usuarioDireccion.usuario.setSexoUsuario(resultSet.getString("SexoUsuario"));
                        usuarioDireccion.usuario.setCorreoUsuario(resultSet.getString("CorreoUsuario"));
                        usuarioDireccion.usuario.setCelularUsuario(resultSet.getString("CelularUsuario"));
                        usuarioDireccion.usuario.setPasswordUsuario(resultSet.getString("PasswordUsuario"));
                        usuarioDireccion.usuario.setTelefonoUsuario(resultSet.getString("TelefonoUsuario"));
                        usuarioDireccion.usuario.setCURPUsuario(resultSet.getString("CURPUsuario"));
                        usuarioDireccion.usuario.setUserNombreUsuario(resultSet.getString("UserNameUsuario"));
                        usuarioDireccion.usuario.setImagen(resultSet.getString("Imagen"));

                        usuarioDireccion.usuario.Roll = new Roll();
                        usuarioDireccion.usuario.Roll.setIdRoll(resultSet.getInt("IdRoll"));
                        usuarioDireccion.usuario.Roll.setNombreRoll(resultSet.getString("NombreRoll"));

                        usuarioDireccion.Direcciones = new ArrayList<>();
                        Direccion Direccion = new Direccion();

                        Direccion.setIdDireccion(resultSet.getInt("IdDireccion"));
                        Direccion.setCalle(resultSet.getString("Calle"));
                        Direccion.setNumeroInterior(resultSet.getString("NumeroInterior"));
                        Direccion.setNumeroExterior(resultSet.getString("NumeroExterior"));

                        Direccion.Colonia = new Colonia();
                        Direccion.Colonia.setIdColonia(resultSet.getInt("IdColonia"));
                        Direccion.Colonia.setNombreColonia(resultSet.getString("NombreColonia"));
                        Direccion.Colonia.setCodigoPostal(resultSet.getString("CodigoPostal"));

                        Direccion.Colonia.Municipio = new Municipio();
                        Direccion.Colonia.Municipio.setIdMunicipio(resultSet.getInt("IdMunicipio"));
                        Direccion.Colonia.Municipio.setNombreMunicipio(resultSet.getString("NombreMunicipio"));

                        Direccion.Colonia.Municipio.Estado = new Estado();
                        Direccion.Colonia.Municipio.Estado.setIdEstado(resultSet.getInt("IdEstado"));
                        Direccion.Colonia.Municipio.Estado.setNombreEstado(resultSet.getString("NombreEstado"));

                        Direccion.Colonia.Municipio.Estado.Pais = new Pais();
                        Direccion.Colonia.Municipio.Estado.Pais.setIdPais(resultSet.getInt("IDPais"));
                        Direccion.Colonia.Municipio.Estado.Pais.setNombrePais(resultSet.getString("NombrePais"));

                        usuarioDireccion.Direcciones.add(Direccion);
                        result.objects.add(usuarioDireccion);

                    }

                }

                return 1;

            });

            if (procesoCorrecto == 1) {
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
    public Result UpdateActivo(int IdUsuario, int ActivoUsuario) {
        Result result = new Result();

        try {
            int procesoCorrecto = jdbcTemplate.execute("{CALL UsuarioUpdateActivo(?,?)}", (CallableStatementCallback<Integer>) callableStatement -> {

                callableStatement.setInt(1, IdUsuario);
                callableStatement.setInt(2, ActivoUsuario);

                callableStatement.executeUpdate();

                return 1;

            });
            if (procesoCorrecto == 1) {
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
    public Result Delete(int idUsuario) {
        
         Result result = new Result();

        try {
            int procesoCorrecto = jdbcTemplate.execute("{CALL UsuarioDelete(?)}", (CallableStatementCallback<Integer>) callableStatement -> {

                callableStatement.setInt(1, idUsuario);

                callableStatement.executeUpdate();

                return 1;

            });
            if (procesoCorrecto == 1) {
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
    public Result DeleteAddress(int idDireccion) {
        
         Result result = new Result();

        try {
            int procesoCorrecto = jdbcTemplate.execute("{CALL DireccionDelete(?)}", (CallableStatementCallback<Integer>) callableStatement -> {

                callableStatement.setInt(1, idDireccion);

                callableStatement.executeUpdate();

                return 1;

            });
            if (procesoCorrecto == 1) {
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

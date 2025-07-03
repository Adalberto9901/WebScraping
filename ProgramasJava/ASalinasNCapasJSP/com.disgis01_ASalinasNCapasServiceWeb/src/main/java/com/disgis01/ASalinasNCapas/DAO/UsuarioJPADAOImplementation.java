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
import com.disgis01.ASalinasNCapas.JPA.Roll;
import com.disgis01.ASalinasNCapas.JPA.Usuario;
import com.disgis01.ASalinasNCapas.JPA.Result;
import com.disgis01.ASalinasNCapas.JPA.UsuarioDireccion;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Alien 1
 */
@Repository
public class UsuarioJPADAOImplementation implements IUsuarioJPADAO {

    @Autowired
    private EntityManager entityManager;

    @Override
    public Result GetAll() {
        Result result = new Result();
        result.objects = new ArrayList<>();

        try {
            TypedQuery<Usuario> UsuarioQuery = entityManager.createQuery("FROM Usuario WHERE ActivoUsuario = 1 order by id ASC", Usuario.class);
            List<Usuario> usuariosJPA = UsuarioQuery.getResultList();

            for (Usuario usuario : usuariosJPA) {
                UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
                usuarioDireccion.Usuario = usuario;

                TypedQuery<Direccion> direccionesQuery = entityManager.createQuery("FROM Direccion WHERE Usuario.IdUsuario = :idusuario AND ActivoDireccion = 1", Direccion.class);
                direccionesQuery.setParameter("idusuario", usuario.getIdUsuario());
                List<Direccion> direccionesJPA = direccionesQuery.getResultList();

                if (direccionesJPA.size() != 0) {
                    usuarioDireccion.Direcciones = direccionesJPA;

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

    @Transactional
    @Override
    public Result Add(UsuarioDireccion usuarioDireccion) {
        Result result = new Result();

        try {
            Usuario usuarioJPA = new Usuario();
            usuarioJPA.setNombreUsuario(usuarioDireccion.Usuario.getNombreUsuario());
            usuarioJPA.setApellidoPatUsuario(usuarioDireccion.Usuario.getApellidoPatUsuario());
            usuarioJPA.setApellidoMatUsuario(usuarioDireccion.Usuario.getApellidoMatUsuario());
            usuarioJPA.setFechaNacimeintoUsuario(usuarioDireccion.Usuario.getFechaNacimeintoUsuario());
            usuarioJPA.setSexoUsuario(usuarioDireccion.Usuario.getSexoUsuario());
            usuarioJPA.setCorreoUsuario(usuarioDireccion.Usuario.getCorreoUsuario());
            usuarioJPA.setCelularUsuario(usuarioDireccion.Usuario.getCelularUsuario());
            usuarioJPA.setPasswordUsuario(usuarioDireccion.Usuario.getPasswordUsuario());
            usuarioJPA.setTelefonoUsuario(usuarioDireccion.Usuario.getTelefonoUsuario());
            usuarioJPA.setCURPUsuario(usuarioDireccion.Usuario.getCURPUsuario());
            usuarioJPA.setUserNombreUsuario(usuarioDireccion.Usuario.getUserNombreUsuario());
            usuarioJPA.setImagen(usuarioDireccion.Usuario.getImagen());
            usuarioJPA.setActivoUsuario(1);

            Roll roll = entityManager.find(Roll.class, usuarioDireccion.Usuario.Roll.getIdRoll());
            usuarioJPA.Roll = new Roll();
            usuarioJPA.Roll.setIdRoll(roll.getIdRoll());
            entityManager.persist(usuarioJPA);
            entityManager.flush();

            Direccion direccionJPA = new Direccion();
            direccionJPA.setCalle(usuarioDireccion.Direccion.getCalle());
            direccionJPA.setNumeroInterior(usuarioDireccion.Direccion.getNumeroInterior());
            direccionJPA.setNumeroExterior(usuarioDireccion.Direccion.getNumeroExterior());
            direccionJPA.setActivoDireccion(1);

            Colonia colonia = entityManager.find(Colonia.class, usuarioDireccion.Direccion.Colonia.getIdColonia());
            direccionJPA.Colonia = new Colonia();
            direccionJPA.Colonia.setIdColonia(colonia.getIdColonia());

            direccionJPA.Usuario = new Usuario();
            direccionJPA.Usuario.setIdUsuario(usuarioJPA.getIdUsuario());
            entityManager.persist(direccionJPA);

            result.correct = true;
        } catch (Exception ex) {
            result.correct = false;
            result.errorMasassge = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }

    @Transactional
    @Override
    public Result Add(List<UsuarioDireccion> usuariosDireccion) {
        Result result = new Result();
        try {
            for (UsuarioDireccion usuarioDireccion : usuariosDireccion) {
                Usuario usuarioJPA = new Usuario();
                usuarioJPA.setNombreUsuario(usuarioDireccion.Usuario.getNombreUsuario());
                usuarioJPA.setApellidoPatUsuario(usuarioDireccion.Usuario.getApellidoPatUsuario());
                usuarioJPA.setApellidoMatUsuario(usuarioDireccion.Usuario.getApellidoMatUsuario());
                usuarioJPA.setFechaNacimeintoUsuario(usuarioDireccion.Usuario.getFechaNacimeintoUsuario());
                usuarioJPA.setSexoUsuario(usuarioDireccion.Usuario.getSexoUsuario());
                usuarioJPA.setCorreoUsuario(usuarioDireccion.Usuario.getCorreoUsuario());
                usuarioJPA.setCelularUsuario(usuarioDireccion.Usuario.getCelularUsuario());
                usuarioJPA.setPasswordUsuario(usuarioDireccion.Usuario.getPasswordUsuario());
                usuarioJPA.setTelefonoUsuario(usuarioDireccion.Usuario.getTelefonoUsuario());
                usuarioJPA.setCURPUsuario(usuarioDireccion.Usuario.getCURPUsuario());
                usuarioJPA.setUserNombreUsuario(usuarioDireccion.Usuario.getUserNombreUsuario());
                usuarioJPA.setImagen(usuarioDireccion.Usuario.getImagen());
                usuarioJPA.setActivoUsuario(1);

                usuarioDireccion.Usuario.Roll = new Roll();
                usuarioDireccion.Usuario.Roll.setIdRoll(usuarioJPA.Roll.getIdRoll());
                entityManager.persist(usuarioJPA);
                entityManager.flush();

                Direccion direccionJPA = new Direccion();
                direccionJPA.setCalle(usuarioDireccion.Direccion.getCalle());
                direccionJPA.setNumeroInterior(usuarioDireccion.Direccion.getNumeroInterior());
                direccionJPA.setNumeroExterior(usuarioDireccion.Direccion.getNumeroExterior());
                direccionJPA.setActivoDireccion(1);

                usuarioDireccion.Direccion.Colonia = new Colonia();
                direccionJPA.Colonia.setIdColonia(usuarioDireccion.Direccion.Colonia.getIdColonia());

                direccionJPA.Usuario.setIdUsuario(usuarioJPA.getIdUsuario());
                entityManager.persist(direccionJPA);
            }
            result.correct = true;
        } catch (Exception ex) {
            result.correct = false;
            result.errorMasassge = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;
    }

    @Transactional
    @Override
    public Result Update(UsuarioDireccion usuarioDireccion) {

        Result result = new Result();

        try {
            Usuario usuarioJPA = entityManager.find(Usuario.class, usuarioDireccion.Usuario.getIdUsuario());

            if (usuarioJPA != null) {
                usuarioJPA.setNombreUsuario(usuarioDireccion.Usuario.getNombreUsuario());
                usuarioJPA.setApellidoPatUsuario(usuarioDireccion.Usuario.getApellidoPatUsuario());
                usuarioJPA.setApellidoMatUsuario(usuarioDireccion.Usuario.getApellidoMatUsuario());
                usuarioJPA.setFechaNacimeintoUsuario(usuarioDireccion.Usuario.getFechaNacimeintoUsuario());
                usuarioJPA.setSexoUsuario(usuarioDireccion.Usuario.getSexoUsuario());
                usuarioJPA.setCorreoUsuario(usuarioDireccion.Usuario.getCorreoUsuario());
                usuarioJPA.setCelularUsuario(usuarioDireccion.Usuario.getCelularUsuario());
                usuarioJPA.setPasswordUsuario(usuarioDireccion.Usuario.getPasswordUsuario());
                usuarioJPA.setTelefonoUsuario(usuarioDireccion.Usuario.getTelefonoUsuario());
                usuarioJPA.setCURPUsuario(usuarioDireccion.Usuario.getCURPUsuario());
                usuarioJPA.setUserNombreUsuario(usuarioDireccion.Usuario.getUserNombreUsuario());
                usuarioJPA.setImagen(usuarioDireccion.Usuario.getImagen());

                usuarioJPA.Roll = new Roll();
                usuarioJPA.Roll.setIdRoll(usuarioDireccion.Usuario.Roll.getIdRoll());

                entityManager.merge(usuarioJPA);

                result.correct = true;
            } else {
                result.correct = false;
                result.errorMasassge = "Usuario no encontrado con ID: " + usuarioDireccion.Usuario.getIdUsuario();
            }
        } catch (Exception ex) {
            result.correct = false;
            result.errorMasassge = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }

    @Transactional
    @Override
    public Result Delete(int idUsuario) {
        Result result = new Result();

        try {
            Usuario usuarioJPA = entityManager.find(Usuario.class, idUsuario);

            if (usuarioJPA != null) {
                usuarioJPA.setActivoUsuario(0);
                entityManager.merge(usuarioJPA);

                result.correct = true;
            } else {
                result.correct = false;
                result.errorMasassge = "Usuario no encontrado con ID: " + idUsuario;
            }
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
//        result.objects = new ArrayList<>();
        try {
            TypedQuery<Usuario> UsuarioQuery = entityManager.createQuery("FROM Usuario  WHERE IdUsuario = :idUsuario", Usuario.class);
            UsuarioQuery.setParameter("idUsuario", idUsuario);
            List<Usuario> usuariosJPA = UsuarioQuery.getResultList();

            for (Usuario usuario : usuariosJPA) {
                UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
                usuarioDireccion.Usuario = usuario;

                TypedQuery<Direccion> direccionesQuery = entityManager.createQuery("FROM Direccion WHERE Usuario.IdUsuario = :idusuario AND ActivoDireccion = 1", Direccion.class);
                direccionesQuery.setParameter("idusuario", usuario.getIdUsuario());
                List<Direccion> direccionesJPA = direccionesQuery.getResultList();

                if (direccionesJPA.size() != 0) {
                    usuarioDireccion.Direcciones = direccionesJPA;

                }
                result.object = usuarioDireccion;
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
    public Result UsuarioGetSolo(int idUsuario) {
        Result result = new Result();
        try {
            TypedQuery<Usuario> UsuarioQuery = entityManager.createQuery("FROM Usuario  WHERE IdUsuario = :idUsuario", Usuario.class);
            UsuarioQuery.setParameter("idUsuario", idUsuario);
            Usuario usuarioJPA = UsuarioQuery.getSingleResult();

            if (usuarioJPA != null) {
                UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
                usuarioDireccion.Usuario = new Usuario();

                usuarioDireccion.Usuario.setIdUsuario(usuarioJPA.getIdUsuario());
                usuarioDireccion.Usuario.setNombreUsuario(usuarioJPA.getNombreUsuario());
                usuarioDireccion.Usuario.setApellidoPatUsuario(usuarioJPA.getApellidoPatUsuario());
                usuarioDireccion.Usuario.setApellidoMatUsuario(usuarioJPA.getApellidoMatUsuario());
                usuarioDireccion.Usuario.setFechaNacimeintoUsuario(usuarioJPA.getFechaNacimeintoUsuario());
                usuarioDireccion.Usuario.setSexoUsuario(usuarioJPA.getSexoUsuario());
                usuarioDireccion.Usuario.setCorreoUsuario(usuarioJPA.getCorreoUsuario());
                usuarioDireccion.Usuario.setCelularUsuario(usuarioJPA.getCelularUsuario());
                usuarioDireccion.Usuario.setPasswordUsuario(usuarioJPA.getPasswordUsuario());
                usuarioDireccion.Usuario.setTelefonoUsuario(usuarioJPA.getTelefonoUsuario());
                usuarioDireccion.Usuario.setCURPUsuario(usuarioJPA.getCURPUsuario());
                usuarioDireccion.Usuario.setUserNombreUsuario(usuarioJPA.getUserNombreUsuario());
                usuarioDireccion.Usuario.setImagen(usuarioJPA.getImagen());
                usuarioDireccion.Usuario.setActivoUsuario(usuarioJPA.getActivoUsuario());

                usuarioDireccion.Usuario.Roll = new Roll();
                usuarioDireccion.Usuario.Roll.setIdRoll(usuarioJPA.Roll.getIdRoll());
                usuarioDireccion.Usuario.Roll.setNombreRoll(usuarioJPA.Roll.getNombreRoll());

                result.object = usuarioDireccion;
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
    public Result UsuarioBusqueda(UsuarioDireccion usuarioBusqueda) {
        Result result = new Result();
        try {
            String Consulta = "FROM Usuario WHERE UPPER(NombreUsuario) LIKE UPPER(:nombreusuario) "
                    + " AND LOWER(ApellidoPatUsuario) LIKE LOWER(:apepaterno) "
                    + "AND LOWER(ApellidoMatUsuario) LIKE LOWER(:apematerno) ";
            Consulta += (usuarioBusqueda.Usuario.Roll.getIdRoll() != 0 ? " AND Roll.IdRoll = : idroll" : "");
            TypedQuery<Usuario> UsuarioQuery = entityManager.createQuery(Consulta + " order by id ASC", Usuario.class);

            UsuarioQuery.setParameter("nombreusuario", "%" + usuarioBusqueda.Usuario.getNombreUsuario() + "%");
            UsuarioQuery.setParameter("apepaterno", "%" + usuarioBusqueda.Usuario.getApellidoPatUsuario() + "%");
            UsuarioQuery.setParameter("apematerno", "%" + usuarioBusqueda.Usuario.getApellidoMatUsuario() + "%");
            if (usuarioBusqueda.Usuario.Roll.getIdRoll() > 0) {
                UsuarioQuery.setParameter("idroll", usuarioBusqueda.Usuario.Roll.getIdRoll());
            }
//            if (usuarioBusqueda.usuario.getActivoUsuario() > 0) {
//                UsuarioQuery.setParameter("idroll", usuarioBusqueda.usuario.Roll.getIdRoll());
//            }
//            UsuarioQuery.setParameter("activousuario", usuarioBusqueda.usuario.getActivoUsuario());
            List<Usuario> usuarios = UsuarioQuery.getResultList();
            result.objects = new ArrayList<>();

            for (Usuario usuarioJPA : usuarios) {
                UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
                usuarioDireccion.Usuario = new Usuario();

                usuarioDireccion.Usuario.setIdUsuario(usuarioJPA.getIdUsuario());
                usuarioDireccion.Usuario.setNombreUsuario(usuarioJPA.getNombreUsuario());
                usuarioDireccion.Usuario.setApellidoPatUsuario(usuarioJPA.getApellidoPatUsuario());
                usuarioDireccion.Usuario.setApellidoMatUsuario(usuarioJPA.getApellidoMatUsuario());
                usuarioDireccion.Usuario.setFechaNacimeintoUsuario(usuarioJPA.getFechaNacimeintoUsuario());
                usuarioDireccion.Usuario.setSexoUsuario(usuarioJPA.getSexoUsuario());
                usuarioDireccion.Usuario.setCorreoUsuario(usuarioJPA.getCorreoUsuario());
                usuarioDireccion.Usuario.setCelularUsuario(usuarioJPA.getCelularUsuario());
                usuarioDireccion.Usuario.setPasswordUsuario(usuarioJPA.getPasswordUsuario());
                usuarioDireccion.Usuario.setTelefonoUsuario(usuarioJPA.getTelefonoUsuario());
                usuarioDireccion.Usuario.setCURPUsuario(usuarioJPA.getCURPUsuario());
                usuarioDireccion.Usuario.setUserNombreUsuario(usuarioJPA.getUserNombreUsuario());
                usuarioDireccion.Usuario.setImagen(usuarioJPA.getImagen());
                usuarioDireccion.Usuario.setActivoUsuario(usuarioJPA.getActivoUsuario());

                usuarioDireccion.Usuario.Roll = new Roll();
                usuarioDireccion.Usuario.Roll.setIdRoll(usuarioJPA.Roll.getIdRoll());
                usuarioDireccion.Usuario.Roll.setNombreRoll(usuarioJPA.Roll.getNombreRoll());

                TypedQuery<Direccion> direccionesQuery = entityManager.createQuery("FROM Direccion WHERE Usuario.IdUsuario = :idusuario", Direccion.class);
                direccionesQuery.setParameter("idusuario", usuarioJPA.getIdUsuario());
                List<Direccion> direccionesJPA = direccionesQuery.getResultList();

                if (direccionesJPA.size() != 0) {
                    usuarioDireccion.Direcciones = new ArrayList<>();

                    for (Direccion direccionJPA : direccionesJPA) {
                        Direccion direccion = new Direccion();

                        direccion.setIdDireccion(direccionJPA.getIdDireccion());
                        direccion.setCalle(direccionJPA.getCalle());
                        direccion.setNumeroInterior(direccionJPA.getNumeroInterior());
                        direccion.setNumeroExterior(direccionJPA.getNumeroExterior());

                        direccion.Colonia = new Colonia();
                        direccion.Colonia.setIdColonia(direccionJPA.Colonia.getIdColonia());
                        direccion.Colonia.setNombreColonia(direccionJPA.Colonia.getNombreColonia());
                        direccion.Colonia.setCodigoPostal(direccionJPA.Colonia.getCodigoPostal());

                        direccion.Colonia.Municipio = new Municipio();
                        direccion.Colonia.Municipio.setIdMunicipio(direccionJPA.Colonia.Municipio.getIdMunicipio());
                        direccion.Colonia.Municipio.setNombreMunicipio(direccionJPA.Colonia.Municipio.getNombreMunicipio());

                        direccion.Colonia.Municipio.Estado = new Estado();
                        direccion.Colonia.Municipio.Estado.setIdEstado(direccionJPA.Colonia.Municipio.Estado.getIdEstado());
                        direccion.Colonia.Municipio.Estado.setNombreEstado(direccionJPA.Colonia.Municipio.Estado.getNombreEstado());

                        direccion.Colonia.Municipio.Estado.Pais = new Pais();
                        direccion.Colonia.Municipio.Estado.Pais.setIdPais(direccionJPA.Colonia.Municipio.Estado.Pais.getIdPais());
                        direccion.Colonia.Municipio.Estado.Pais.setNombrePais(direccionJPA.Colonia.Municipio.Estado.Pais.getNombrePais());

                        usuarioDireccion.Direcciones.add(direccion);
                    }
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

    @Transactional
    @Override
    public Result UpdateActivo(int IdUsuario, int ActivoUsuario) {
        Result result = new Result();

        try {
            Usuario usuarioJPA = entityManager.find(Usuario.class, IdUsuario);

            if (usuarioJPA != null) {
                usuarioJPA.setActivoUsuario(ActivoUsuario);
                entityManager.merge(usuarioJPA);

                result.correct = true;
            } else {
                result.correct = false;
                result.errorMasassge = "Usuario no encontrado con ID: " + IdUsuario;
            }
        } catch (Exception ex) {
            result.correct = false;
            result.errorMasassge = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }

}

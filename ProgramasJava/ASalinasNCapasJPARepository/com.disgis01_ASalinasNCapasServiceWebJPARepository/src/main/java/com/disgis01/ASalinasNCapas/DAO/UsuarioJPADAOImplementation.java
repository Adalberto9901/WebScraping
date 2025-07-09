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
                usuarioDireccion.usuario = usuario;

                TypedQuery<Direccion> direccionesQuery = entityManager.createQuery("FROM Direccion WHERE Usuario.IdUsuario = :idusuario AND ActivoDireccion = 1", Direccion.class);
                direccionesQuery.setParameter("idusuario", usuario.getIdUsuario());
//                direccionesQuery.setParameter("idusuario", usuario.getId());
                List<Direccion> direccionesJPA = direccionesQuery.getResultList();

                if (direccionesJPA.size() != 0) {
                    usuarioDireccion.direcciones = direccionesJPA;

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
            usuarioJPA.setNombreUsuario(usuarioDireccion.usuario.getNombreUsuario());
            usuarioJPA.setApellidoPatUsuario(usuarioDireccion.usuario.getApellidoPatUsuario());
            usuarioJPA.setApellidoMatUsuario(usuarioDireccion.usuario.getApellidoMatUsuario());
            usuarioJPA.setFechaNacimeintoUsuario(usuarioDireccion.usuario.getFechaNacimeintoUsuario());
            usuarioJPA.setSexoUsuario(usuarioDireccion.usuario.getSexoUsuario());
            usuarioJPA.setCorreoUsuario(usuarioDireccion.usuario.getCorreoUsuario());
            usuarioJPA.setCelularUsuario(usuarioDireccion.usuario.getCelularUsuario());
            usuarioJPA.setPasswordUsuario(usuarioDireccion.usuario.getPasswordUsuario());
            usuarioJPA.setTelefonoUsuario(usuarioDireccion.usuario.getTelefonoUsuario());
            usuarioJPA.setCURPUsuario(usuarioDireccion.usuario.getCURPUsuario());
            usuarioJPA.setUserNombreUsuario(usuarioDireccion.usuario.getUserNombreUsuario());
            usuarioJPA.setImagen(usuarioDireccion.usuario.getImagen());
            usuarioJPA.setActivoUsuario(1);

            Roll roll = entityManager.find(Roll.class, usuarioDireccion.usuario.roll.getIdRoll());
            usuarioJPA.roll = new Roll();
            usuarioJPA.roll.setIdRoll(roll.getIdRoll());
            entityManager.persist(usuarioJPA);
            entityManager.flush();

            Direccion direccionJPA = new Direccion();
            direccionJPA.setCalle(usuarioDireccion.direccion.getCalle());
            direccionJPA.setNumeroInterior(usuarioDireccion.direccion.getNumeroInterior());
            direccionJPA.setNumeroExterior(usuarioDireccion.direccion.getNumeroExterior());
            direccionJPA.setActivoDireccion(1);

            Colonia colonia = entityManager.find(Colonia.class, usuarioDireccion.direccion.colonia.getIdColonia());
            direccionJPA.colonia = new Colonia();
            direccionJPA.colonia.setIdColonia(colonia.getIdColonia());

            direccionJPA.usuario = new Usuario();
            direccionJPA.usuario.setIdUsuario(usuarioJPA.getIdUsuario());
//            direccionJPA.usuario.setId(usuarioJPA.getId());
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
                usuarioJPA.setNombreUsuario(usuarioDireccion.usuario.getNombreUsuario());
                usuarioJPA.setApellidoPatUsuario(usuarioDireccion.usuario.getApellidoPatUsuario());
                usuarioJPA.setApellidoMatUsuario(usuarioDireccion.usuario.getApellidoMatUsuario());
                usuarioJPA.setFechaNacimeintoUsuario(usuarioDireccion.usuario.getFechaNacimeintoUsuario());
                usuarioJPA.setSexoUsuario(usuarioDireccion.usuario.getSexoUsuario());
                usuarioJPA.setCorreoUsuario(usuarioDireccion.usuario.getCorreoUsuario());
                usuarioJPA.setCelularUsuario(usuarioDireccion.usuario.getCelularUsuario());
                usuarioJPA.setPasswordUsuario(usuarioDireccion.usuario.getPasswordUsuario());
                usuarioJPA.setTelefonoUsuario(usuarioDireccion.usuario.getTelefonoUsuario());
                usuarioJPA.setCURPUsuario(usuarioDireccion.usuario.getCURPUsuario());
                usuarioJPA.setUserNombreUsuario(usuarioDireccion.usuario.getUserNombreUsuario());
                usuarioJPA.setImagen(usuarioDireccion.usuario.getImagen());
                usuarioJPA.setActivoUsuario(1);

                usuarioDireccion.usuario.roll = new Roll();
                usuarioDireccion.usuario.roll.setIdRoll(usuarioJPA.roll.getIdRoll());
                entityManager.persist(usuarioJPA);
                entityManager.flush();

                Direccion direccionJPA = new Direccion();
                direccionJPA.setCalle(usuarioDireccion.direccion.getCalle());
                direccionJPA.setNumeroInterior(usuarioDireccion.direccion.getNumeroInterior());
                direccionJPA.setNumeroExterior(usuarioDireccion.direccion.getNumeroExterior());
                direccionJPA.setActivoDireccion(1);

                usuarioDireccion.direccion.colonia = new Colonia();
                direccionJPA.colonia.setIdColonia(usuarioDireccion.direccion.colonia.getIdColonia());

                direccionJPA.usuario.setIdUsuario(usuarioJPA.getIdUsuario());
//                direccionJPA.usuario.setId(usuarioJPA.getId());
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
//            Usuario usuarioJPA = entityManager.find(Usuario.class, usuarioDireccion.usuario.getId());
            Usuario usuarioJPA = entityManager.find(Usuario.class, usuarioDireccion.usuario.getIdUsuario());

            if (usuarioJPA != null) {
                usuarioJPA.setNombreUsuario(usuarioDireccion.usuario.getNombreUsuario());
                usuarioJPA.setApellidoPatUsuario(usuarioDireccion.usuario.getApellidoPatUsuario());
                usuarioJPA.setApellidoMatUsuario(usuarioDireccion.usuario.getApellidoMatUsuario());
                usuarioJPA.setFechaNacimeintoUsuario(usuarioDireccion.usuario.getFechaNacimeintoUsuario());
                usuarioJPA.setSexoUsuario(usuarioDireccion.usuario.getSexoUsuario());
                usuarioJPA.setCorreoUsuario(usuarioDireccion.usuario.getCorreoUsuario());
                usuarioJPA.setCelularUsuario(usuarioDireccion.usuario.getCelularUsuario());
                usuarioJPA.setPasswordUsuario(usuarioDireccion.usuario.getPasswordUsuario());
                usuarioJPA.setTelefonoUsuario(usuarioDireccion.usuario.getTelefonoUsuario());
                usuarioJPA.setCURPUsuario(usuarioDireccion.usuario.getCURPUsuario());
                usuarioJPA.setUserNombreUsuario(usuarioDireccion.usuario.getUserNombreUsuario());
                usuarioJPA.setImagen(usuarioDireccion.usuario.getImagen());

                usuarioJPA.roll = new Roll();
                usuarioJPA.roll.setIdRoll(usuarioDireccion.usuario.roll.getIdRoll());

                entityManager.merge(usuarioJPA);

                result.correct = true;
            } else {
                result.correct = false;
                result.errorMasassge = "Usuario no encontrado con ID: " + usuarioDireccion.usuario.getIdUsuario();
//                result.errorMasassge = "Usuario no encontrado con ID: " + usuarioDireccion.usuario.getId();
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
                usuarioDireccion.usuario = usuario;

                TypedQuery<Direccion> direccionesQuery = entityManager.createQuery("FROM Direccion WHERE Usuario.IdUsuario = :idusuario AND ActivoDireccion = 1", Direccion.class);
//                direccionesQuery.setParameter("idusuario", usuario.getId());
                direccionesQuery.setParameter("idusuario", usuario.getIdUsuario());
                List<Direccion> direccionesJPA = direccionesQuery.getResultList();

                if (direccionesJPA.size() != 0) {
                    usuarioDireccion.direcciones = direccionesJPA;

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
                usuarioDireccion.usuario = new Usuario();

                usuarioDireccion.usuario.setIdUsuario(usuarioJPA.getIdUsuario());
//                usuarioDireccion.usuario.setId(usuarioJPA.getId());
                usuarioDireccion.usuario.setNombreUsuario(usuarioJPA.getNombreUsuario());
                usuarioDireccion.usuario.setApellidoPatUsuario(usuarioJPA.getApellidoPatUsuario());
                usuarioDireccion.usuario.setApellidoMatUsuario(usuarioJPA.getApellidoMatUsuario());
                usuarioDireccion.usuario.setFechaNacimeintoUsuario(usuarioJPA.getFechaNacimeintoUsuario());
                usuarioDireccion.usuario.setSexoUsuario(usuarioJPA.getSexoUsuario());
                usuarioDireccion.usuario.setCorreoUsuario(usuarioJPA.getCorreoUsuario());
                usuarioDireccion.usuario.setCelularUsuario(usuarioJPA.getCelularUsuario());
                usuarioDireccion.usuario.setPasswordUsuario(usuarioJPA.getPasswordUsuario());
                usuarioDireccion.usuario.setTelefonoUsuario(usuarioJPA.getTelefonoUsuario());
                usuarioDireccion.usuario.setCURPUsuario(usuarioJPA.getCURPUsuario());
                usuarioDireccion.usuario.setUserNombreUsuario(usuarioJPA.getUserNombreUsuario());
                usuarioDireccion.usuario.setImagen(usuarioJPA.getImagen());
                usuarioDireccion.usuario.setActivoUsuario(usuarioJPA.getActivoUsuario());

                usuarioDireccion.usuario.roll = new Roll();
                usuarioDireccion.usuario.roll.setIdRoll(usuarioJPA.roll.getIdRoll());
                usuarioDireccion.usuario.roll.setNombreRoll(usuarioJPA.roll.getNombreRoll());

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
            Consulta += (usuarioBusqueda.usuario.roll.getIdRoll() != 0 ? " AND Roll.IdRoll = : idroll" : "");
            TypedQuery<Usuario> UsuarioQuery = entityManager.createQuery(Consulta + " order by id ASC", Usuario.class);

            UsuarioQuery.setParameter("nombreusuario", "%" + usuarioBusqueda.usuario.getNombreUsuario() + "%");
            UsuarioQuery.setParameter("apepaterno", "%" + usuarioBusqueda.usuario.getApellidoPatUsuario() + "%");
            UsuarioQuery.setParameter("apematerno", "%" + usuarioBusqueda.usuario.getApellidoMatUsuario() + "%");
            if (usuarioBusqueda.usuario.roll.getIdRoll() > 0) {
                UsuarioQuery.setParameter("idroll", usuarioBusqueda.usuario.roll.getIdRoll());
            }
//            if (usuarioBusqueda.usuario.getActivoUsuario() > 0) {
//                UsuarioQuery.setParameter("idroll", usuarioBusqueda.usuario.Roll.getIdRoll());
//            }
//            UsuarioQuery.setParameter("activousuario", usuarioBusqueda.usuario.getActivoUsuario());
            List<Usuario> usuarios = UsuarioQuery.getResultList();
            result.objects = new ArrayList<>();

            for (Usuario usuarioJPA : usuarios) {
                UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
                usuarioDireccion.usuario = new Usuario();

                usuarioDireccion.usuario.setIdUsuario(usuarioJPA.getIdUsuario());
//                usuarioDireccion.usuario.setId(usuarioJPA.getId());
                usuarioDireccion.usuario.setNombreUsuario(usuarioJPA.getNombreUsuario());
                usuarioDireccion.usuario.setApellidoPatUsuario(usuarioJPA.getApellidoPatUsuario());
                usuarioDireccion.usuario.setApellidoMatUsuario(usuarioJPA.getApellidoMatUsuario());
                usuarioDireccion.usuario.setFechaNacimeintoUsuario(usuarioJPA.getFechaNacimeintoUsuario());
                usuarioDireccion.usuario.setSexoUsuario(usuarioJPA.getSexoUsuario());
                usuarioDireccion.usuario.setCorreoUsuario(usuarioJPA.getCorreoUsuario());
                usuarioDireccion.usuario.setCelularUsuario(usuarioJPA.getCelularUsuario());
                usuarioDireccion.usuario.setPasswordUsuario(usuarioJPA.getPasswordUsuario());
                usuarioDireccion.usuario.setTelefonoUsuario(usuarioJPA.getTelefonoUsuario());
                usuarioDireccion.usuario.setCURPUsuario(usuarioJPA.getCURPUsuario());
                usuarioDireccion.usuario.setUserNombreUsuario(usuarioJPA.getUserNombreUsuario());
                usuarioDireccion.usuario.setImagen(usuarioJPA.getImagen());
                usuarioDireccion.usuario.setActivoUsuario(usuarioJPA.getActivoUsuario());

                usuarioDireccion.usuario.roll = new Roll();
                usuarioDireccion.usuario.roll.setIdRoll(usuarioJPA.roll.getIdRoll());
                usuarioDireccion.usuario.roll.setNombreRoll(usuarioJPA.roll.getNombreRoll());

                TypedQuery<Direccion> direccionesQuery = entityManager.createQuery("FROM Direccion WHERE Usuario.IdUsuario = :idusuario", Direccion.class);
                direccionesQuery.setParameter("idusuario", usuarioJPA.getIdUsuario());
//                direccionesQuery.setParameter("idusuario", usuarioJPA.getId());
                List<Direccion> direccionesJPA = direccionesQuery.getResultList();

                if (direccionesJPA.size() != 0) {
                    usuarioDireccion.direcciones = new ArrayList<>();

                    for (Direccion direccionJPA : direccionesJPA) {
                        Direccion direccion = new Direccion();

                        direccion.setIdDireccion(direccionJPA.getIdDireccion());
                        direccion.setCalle(direccionJPA.getCalle());
                        direccion.setNumeroInterior(direccionJPA.getNumeroInterior());
                        direccion.setNumeroExterior(direccionJPA.getNumeroExterior());

                        direccion.colonia = new Colonia();
                        direccion.colonia.setIdColonia(direccionJPA.colonia.getIdColonia());
                        direccion.colonia.setNombreColonia(direccionJPA.colonia.getNombreColonia());
                        direccion.colonia.setCodigoPostal(direccionJPA.colonia.getCodigoPostal());

                        direccion.colonia.municipio = new Municipio();
                        direccion.colonia.municipio.setIdMunicipio(direccionJPA.colonia.municipio.getIdMunicipio());
                        direccion.colonia.municipio.setNombreMunicipio(direccionJPA.colonia.municipio.getNombreMunicipio());

                        direccion.colonia.municipio.estado = new Estado();
                        direccion.colonia.municipio.estado.setIdEstado(direccionJPA.colonia.municipio.estado.getIdEstado());
                        direccion.colonia.municipio.estado.setNombreEstado(direccionJPA.colonia.municipio.estado.getNombreEstado());

                        direccion.colonia.municipio.estado.pais = new Pais();
                        direccion.colonia.municipio.estado.pais.setIdPais(direccionJPA.colonia.municipio.estado.pais.getIdPais());
                        direccion.colonia.municipio.estado.pais.setNombrePais(direccionJPA.colonia.municipio.estado.pais.getNombrePais());

                        usuarioDireccion.direcciones.add(direccion);
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

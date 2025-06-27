/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.disgis01.ASalinasNCapas.DAO;

import com.disgis01.ASalinasNCapas.JPA.Colonia;
import com.disgis01.ASalinasNCapas.JPA.Direccion;
import com.disgis01.ASalinasNCapas.JPA.Roll;
import com.disgis01.ASalinasNCapas.JPA.Usuario;
import com.disgis01.ASalinasNCapas.ML.Result;
import com.disgis01.ASalinasNCapas.ML.UsuarioDireccion;
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

        try {
            TypedQuery<Usuario> UsuarioQuery = entityManager.createQuery("FROM Usuario WHERE ActivoUsuario = 1 order by id ASC", Usuario.class);
            List<Usuario> usuarios = UsuarioQuery.getResultList();
            result.objects = new ArrayList<>();

            for (Usuario usuarioJPA : usuarios) {
                UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
                usuarioDireccion.usuario = new com.disgis01.ASalinasNCapas.ML.Usuario();

                usuarioDireccion.usuario.setIdUsuario(usuarioJPA.getIdUsuario());
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

                usuarioDireccion.usuario.Roll = new com.disgis01.ASalinasNCapas.ML.Roll();
                usuarioDireccion.usuario.Roll.setIdRoll(usuarioJPA.Roll.getIdRoll());
                usuarioDireccion.usuario.Roll.setNombreRoll(usuarioJPA.Roll.getNombreRoll());

                TypedQuery<Direccion> direccionesQuery = entityManager.createQuery("FROM Direccion WHERE Usuario.IdUsuario = :idusuario AND ActivoDireccion = 1", Direccion.class);
                direccionesQuery.setParameter("idusuario", usuarioJPA.getIdUsuario());
                List<Direccion> direccionesJPA = direccionesQuery.getResultList();

                if (direccionesJPA.size() != 0) {
                    usuarioDireccion.Direcciones = new ArrayList<>();

                    for (Direccion direccionJPA : direccionesJPA) {
                        com.disgis01.ASalinasNCapas.ML.Direccion direccion = new com.disgis01.ASalinasNCapas.ML.Direccion();

                        direccion.setIdDireccion(direccionJPA.getIdDireccion());
                        direccion.setCalle(direccionJPA.getCalle());
                        direccion.setNumeroInterior(direccionJPA.getNumeroInterior());
                        direccion.setNumeroExterior(direccionJPA.getNumeroExterior());

                        direccion.Colonia = new com.disgis01.ASalinasNCapas.ML.Colonia();
                        direccion.Colonia.setIdColonia(direccionJPA.Colonia.getIdColonia());
                        direccion.Colonia.setNombreColonia(direccionJPA.Colonia.getNombreColonia());
                        direccion.Colonia.setCodigoPostal(direccionJPA.Colonia.getCodigoPostal());

                        direccion.Colonia.Municipio = new com.disgis01.ASalinasNCapas.ML.Municipio();
                        direccion.Colonia.Municipio.setIdMunicipio(direccionJPA.Colonia.Municipio.getIdMunicipio());
                        direccion.Colonia.Municipio.setNombreMunicipio(direccionJPA.Colonia.Municipio.getNombreMunicipio());

                        direccion.Colonia.Municipio.Estado = new com.disgis01.ASalinasNCapas.ML.Estado();
                        direccion.Colonia.Municipio.Estado.setIdEstado(direccionJPA.Colonia.Municipio.Estado.getIdEstado());
                        direccion.Colonia.Municipio.Estado.setNombreEstado(direccionJPA.Colonia.Municipio.Estado.getNombreEstado());

                        direccion.Colonia.Municipio.Estado.Pais = new com.disgis01.ASalinasNCapas.ML.Pais();
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
            usuarioJPA.setActivoUsuario(1);

//            usuarioJPA.Roll = new Roll();
//            usuarioJPA.Roll.setIdRoll(usuarioDireccion.usuario.Roll.getIdRoll());
            Roll roll = entityManager.find(Roll.class, usuarioDireccion.getUsuario().getRoll().getIdRoll());
            usuarioJPA.setRoll(roll);
            entityManager.persist(usuarioJPA);
            entityManager.flush();

            Direccion direccionJPA = new Direccion();
            direccionJPA.setCalle(usuarioDireccion.Direccion.getCalle());
            direccionJPA.setNumeroInterior(usuarioDireccion.Direccion.getNumeroInterior());
            direccionJPA.setNumeroExterior(usuarioDireccion.Direccion.getNumeroExterior());
            direccionJPA.setActivoDireccion(1);

//            direccionJPA.Colonia = new Colonia();
//            direccionJPA.Colonia.setIdColonia(usuarioDireccion.Direccion.Colonia.getIdColonia());
            Colonia colonia = entityManager.find(Colonia.class, usuarioDireccion.getDireccion().getColonia().getIdColonia());
            direccionJPA.setColonia(colonia);

            direccionJPA.setUsuario(usuarioJPA);
            entityManager.persist(direccionJPA);

            result.correct = true;
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

    @Transactional
    @Override
    public Result Update(UsuarioDireccion usuarioDireccion) {

        Result result = new Result();

        try {
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

                usuarioJPA.Roll = new Roll();
                usuarioJPA.Roll.setIdRoll(usuarioDireccion.usuario.Roll.getIdRoll());
//                Roll roll = entityManager.find(Roll.class, usuarioDireccion.getUsuario().getRoll().getIdRoll());
//                usuarioJPA.setRoll(roll);

                entityManager.merge(usuarioJPA);

                result.correct = true;
            } else {
                result.correct = false;
                result.errorMasassge = "Usuario no encontrado con ID: " + usuarioDireccion.usuario.getIdUsuario();
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
        try {
            TypedQuery<Usuario> UsuarioQuery = entityManager.createQuery("FROM Usuario  WHERE IdUsuario = :idUsuario", Usuario.class);
            UsuarioQuery.setParameter("idUsuario", idUsuario);
            Usuario usuarioJPA = UsuarioQuery.getSingleResult();

            if (usuarioJPA != null) {
                UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
                usuarioDireccion.usuario = new com.disgis01.ASalinasNCapas.ML.Usuario();

                usuarioDireccion.usuario.setIdUsuario(usuarioJPA.getIdUsuario());
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

                usuarioDireccion.usuario.Roll = new com.disgis01.ASalinasNCapas.ML.Roll();
                usuarioDireccion.usuario.Roll.setIdRoll(usuarioJPA.Roll.getIdRoll());
                usuarioDireccion.usuario.Roll.setNombreRoll(usuarioJPA.Roll.getNombreRoll());

                TypedQuery<Direccion> direccionesQuery = entityManager.createQuery("FROM Direccion WHERE Usuario.IdUsuario = :idusuario AND ActivoDireccion = 1", Direccion.class);
                direccionesQuery.setParameter("idusuario", usuarioJPA.getIdUsuario());
                List<Direccion> direccionesJPA = direccionesQuery.getResultList();

                if (direccionesJPA.size() != 0) {
                    usuarioDireccion.Direcciones = new ArrayList<>();

                    for (Direccion direccionJPA : direccionesJPA) {
                        com.disgis01.ASalinasNCapas.ML.Direccion direccion = new com.disgis01.ASalinasNCapas.ML.Direccion();

                        direccion.setIdDireccion(direccionJPA.getIdDireccion());
                        direccion.setCalle(direccionJPA.getCalle());
                        direccion.setNumeroInterior(direccionJPA.getNumeroInterior());
                        direccion.setNumeroExterior(direccionJPA.getNumeroExterior());

                        direccion.Colonia = new com.disgis01.ASalinasNCapas.ML.Colonia();
                        direccion.Colonia.setIdColonia(direccionJPA.Colonia.getIdColonia());
                        direccion.Colonia.setNombreColonia(direccionJPA.Colonia.getNombreColonia());
                        direccion.Colonia.setCodigoPostal(direccionJPA.Colonia.getCodigoPostal());

                        direccion.Colonia.Municipio = new com.disgis01.ASalinasNCapas.ML.Municipio();
                        direccion.Colonia.Municipio.setIdMunicipio(direccionJPA.Colonia.Municipio.getIdMunicipio());
                        direccion.Colonia.Municipio.setNombreMunicipio(direccionJPA.Colonia.Municipio.getNombreMunicipio());

                        direccion.Colonia.Municipio.Estado = new com.disgis01.ASalinasNCapas.ML.Estado();
                        direccion.Colonia.Municipio.Estado.setIdEstado(direccionJPA.Colonia.Municipio.Estado.getIdEstado());
                        direccion.Colonia.Municipio.Estado.setNombreEstado(direccionJPA.Colonia.Municipio.Estado.getNombreEstado());

                        direccion.Colonia.Municipio.Estado.Pais = new com.disgis01.ASalinasNCapas.ML.Pais();
                        direccion.Colonia.Municipio.Estado.Pais.setIdPais(direccionJPA.Colonia.Municipio.Estado.Pais.getIdPais());
                        direccion.Colonia.Municipio.Estado.Pais.setNombrePais(direccionJPA.Colonia.Municipio.Estado.Pais.getNombrePais());

                        usuarioDireccion.Direcciones.add(direccion);
                    }
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
                usuarioDireccion.usuario = new com.disgis01.ASalinasNCapas.ML.Usuario();

                usuarioDireccion.usuario.setIdUsuario(usuarioJPA.getIdUsuario());
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

                usuarioDireccion.usuario.Roll = new com.disgis01.ASalinasNCapas.ML.Roll();
                usuarioDireccion.usuario.Roll.setIdRoll(usuarioJPA.getIdUsuario());
                usuarioDireccion.usuario.Roll.setNombreRoll(usuarioJPA.getNombreUsuario());

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
                    + "AND LOWER(ApellidoMatUsuario) LIKE LOWER(:apematerno) "
                    + " order by id ASC";
            Consulta += (usuarioBusqueda.usuario.Roll.getIdRoll() != 0 ? " AND Roll.IdRoll = : idroll" : "");
            TypedQuery<Usuario> UsuarioQuery = entityManager.createQuery(Consulta, Usuario.class);

            UsuarioQuery.setParameter("nombreusuario", "%" + usuarioBusqueda.usuario.getNombreUsuario() + "%");
            UsuarioQuery.setParameter("apepaterno", "%" + usuarioBusqueda.usuario.getApellidoPatUsuario() + "%");
            UsuarioQuery.setParameter("apematerno", "%" + usuarioBusqueda.usuario.getApellidoMatUsuario() + "%");
            if (usuarioBusqueda.usuario.Roll.getIdRoll() > 0) {
                UsuarioQuery.setParameter("idroll", usuarioBusqueda.usuario.Roll.getIdRoll());
            }
//            if (usuarioBusqueda.usuario.getActivoUsuario() > 0) {
//                UsuarioQuery.setParameter("idroll", usuarioBusqueda.usuario.Roll.getIdRoll());
//            }
//            UsuarioQuery.setParameter("activousuario", usuarioBusqueda.usuario.getActivoUsuario());
            List<Usuario> usuarios = UsuarioQuery.getResultList();
            result.objects = new ArrayList<>();

            for (Usuario usuarioJPA : usuarios) {
                UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
                usuarioDireccion.usuario = new com.disgis01.ASalinasNCapas.ML.Usuario();

                usuarioDireccion.usuario.setIdUsuario(usuarioJPA.getIdUsuario());
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

                usuarioDireccion.usuario.Roll = new com.disgis01.ASalinasNCapas.ML.Roll();
                usuarioDireccion.usuario.Roll.setIdRoll(usuarioJPA.Roll.getIdRoll());
                usuarioDireccion.usuario.Roll.setNombreRoll(usuarioJPA.Roll.getNombreRoll());

                TypedQuery<Direccion> direccionesQuery = entityManager.createQuery("FROM Direccion WHERE Usuario.IdUsuario = :idusuario", Direccion.class);
                direccionesQuery.setParameter("idusuario", usuarioJPA.getIdUsuario());
                List<Direccion> direccionesJPA = direccionesQuery.getResultList();

                if (direccionesJPA.size() != 0) {
                    usuarioDireccion.Direcciones = new ArrayList<>();

                    for (Direccion direccionJPA : direccionesJPA) {
                        com.disgis01.ASalinasNCapas.ML.Direccion direccion = new com.disgis01.ASalinasNCapas.ML.Direccion();

                        direccion.setIdDireccion(direccionJPA.getIdDireccion());
                        direccion.setCalle(direccionJPA.getCalle());
                        direccion.setNumeroInterior(direccionJPA.getNumeroInterior());
                        direccion.setNumeroExterior(direccionJPA.getNumeroExterior());

                        direccion.Colonia = new com.disgis01.ASalinasNCapas.ML.Colonia();
                        direccion.Colonia.setIdColonia(direccionJPA.Colonia.getIdColonia());
                        direccion.Colonia.setNombreColonia(direccionJPA.Colonia.getNombreColonia());
                        direccion.Colonia.setCodigoPostal(direccionJPA.Colonia.getCodigoPostal());

                        direccion.Colonia.Municipio = new com.disgis01.ASalinasNCapas.ML.Municipio();
                        direccion.Colonia.Municipio.setIdMunicipio(direccionJPA.Colonia.Municipio.getIdMunicipio());
                        direccion.Colonia.Municipio.setNombreMunicipio(direccionJPA.Colonia.Municipio.getNombreMunicipio());

                        direccion.Colonia.Municipio.Estado = new com.disgis01.ASalinasNCapas.ML.Estado();
                        direccion.Colonia.Municipio.Estado.setIdEstado(direccionJPA.Colonia.Municipio.Estado.getIdEstado());
                        direccion.Colonia.Municipio.Estado.setNombreEstado(direccionJPA.Colonia.Municipio.Estado.getNombreEstado());

                        direccion.Colonia.Municipio.Estado.Pais = new com.disgis01.ASalinasNCapas.ML.Pais();
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

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.disgis01.ASalinasNCapas;

import com.disgis01.ASalinasNCapas.DAO.RollJPADAOImplementation;
import com.disgis01.ASalinasNCapas.DAO.UsuarioJPADAOImplementation;
import com.disgis01.ASalinasNCapas.JPA.Colonia;
import com.disgis01.ASalinasNCapas.JPA.Direccion;
import com.disgis01.ASalinasNCapas.JPA.Estado;
import com.disgis01.ASalinasNCapas.JPA.Municipio;
import com.disgis01.ASalinasNCapas.JPA.Pais;
import com.disgis01.ASalinasNCapas.JPA.Result;
import com.disgis01.ASalinasNCapas.JPA.Roll;
import com.disgis01.ASalinasNCapas.JPA.Usuario;
import com.disgis01.ASalinasNCapas.JPA.UsuarioDireccion;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import org.springframework.boot.test.context.SpringBootTest;

/**
 *
 * @author Alien 1
 */
@SpringBootTest
public class UsuarioMockitoTest {

    @Mock
    private EntityManager entityManager;

    @Mock
    private TypedQuery<UsuarioDireccion> typedQuery;

    @InjectMocks
    private UsuarioJPADAOImplementation usuarioJPADAOImplementation;

    @InjectMocks
    private RollJPADAOImplementation rollJPADAOImplementation;

    @Test
    public void GetAll() throws IOException {
        Usuario Usuario = new Usuario();
        Usuario.setIdUsuario(1);
        Usuario.setNombreUsuario("Adal");
        Usuario.setApellidoPatUsuario("Salinas");
        Usuario.setApellidoMatUsuario("Jose");
//        usuarioDireccion.usuario.setFechaNacimeintoUsuario(usuarioDireccion.usuario.getFechaNacimeintoUsuario());
        Usuario.setSexoUsuario("H");
        Usuario.setCorreoUsuario("asalinasjoses@gmail.com");
        Usuario.setCelularUsuario("55-1984-4862");
        Usuario.setPasswordUsuario("MockitoPass");
        Usuario.setTelefonoUsuario("54-4651-4893");
        Usuario.setCURPUsuario("OAS040404");
        Usuario.setUserNombreUsuario("Cuenta_Adal");
        Usuario.setActivoUsuario(1);

        Usuario.Roll = new Roll();
        Usuario.Roll.setIdRoll(4);

        List<Usuario> usuariosMock = List.of(Usuario);

        Direccion Direccion = new Direccion();
        Direccion.setIdDireccion(1);
        Direccion.setCalle("Calle falsa");
        Direccion.setNumeroInterior("1465");
        Direccion.setNumeroExterior("279");
        Direccion.setActivoDireccion(1);

//            Colonia colonia = com.disgis01.ASalinasNCapas.ML.Colonia();
        Direccion.Colonia = new Colonia();
        Direccion.Colonia.setIdColonia(5);

        Direccion.Usuario = new Usuario();
        Direccion.Usuario.setIdUsuario(24);
//        usuarioDireccion.usuario.setIdUsuario(24);
        List<Direccion> direccionesMock = List.of(Direccion);
//        UsuarioDireccion usuario1 = new UsuarioDireccion(Usuario, Direccion);
//        List<UsuarioDireccion> usuariosDireccion = List.of(usuario1);

        TypedQuery<Usuario> usuarioQueryMock = mock(TypedQuery.class);
        when(entityManager.createQuery("FROM Usuario WHERE ActivoUsuario = 1 order by id ASC", Usuario.class))
                .thenReturn(usuarioQueryMock);
        when(usuarioQueryMock.getResultList()).thenReturn(usuariosMock);

        TypedQuery<Direccion> direccionQueryMock = mock(TypedQuery.class);
        when(entityManager.createQuery("FROM Direccion WHERE Usuario.IdUsuario = :idusuario AND ActivoDireccion = 1", Direccion.class))
                .thenReturn(direccionQueryMock);
        when(direccionQueryMock.setParameter("idusuario", 1)).thenReturn(direccionQueryMock); // encadenamiento
        when(direccionQueryMock.getResultList()).thenReturn(direccionesMock);

        Result result = usuarioJPADAOImplementation.GetAll();

        Assertions.assertNotNull(result.correct);
        Assertions.assertNull(result.ex);
        Assertions.assertNull(result.errorMasassge);
        Assertions.assertNull(result.object);
        Assertions.assertNotNull(result.objects);

        verify(entityManager).createQuery("FROM Usuario WHERE ActivoUsuario = 1 order by id ASC", Usuario.class);
        verify(usuarioQueryMock).getResultList();

        verify(entityManager).createQuery("FROM Direccion WHERE Usuario.IdUsuario = :idusuario AND ActivoDireccion = 1", Direccion.class);
        verify(direccionQueryMock).setParameter("idusuario", 1);
        verify(direccionQueryMock).getResultList();

        verifyNoMoreInteractions(entityManager, usuarioQueryMock, direccionQueryMock);
    }

    @Test
    public void UsuarioGetSolo() throws IOException {
        Usuario Usuario = new Usuario();
        Usuario.setIdUsuario(1);
        Usuario.setNombreUsuario("Adalberto");
        Usuario.setApellidoPatUsuario("Salinas");
        Usuario.setApellidoMatUsuario("Jose");
//        usuarioDireccion.usuario.setFechaNacimeintoUsuario(usuarioDireccion.usuario.getFechaNacimeintoUsuario());
        Usuario.setSexoUsuario("H");
        Usuario.setCorreoUsuario("asalinasjoses@gmail.com");
        Usuario.setCelularUsuario("55-1984-4862");
        Usuario.setPasswordUsuario("MockitoPass");
        Usuario.setTelefonoUsuario("54-4651-4893");
        Usuario.setCURPUsuario("OAS040404");
        Usuario.setUserNombreUsuario("Cuenta_Adal");
        Usuario.setActivoUsuario(1);

        Usuario.Roll = new Roll();
        Usuario.Roll.setIdRoll(4);

        TypedQuery<Usuario> mockQuery = mock(TypedQuery.class);
        when(entityManager.createQuery(anyString(), eq(Usuario.class))).thenReturn(mockQuery);
        when(mockQuery.setParameter(anyString(), anyInt())).thenReturn(mockQuery);
        when(mockQuery.getSingleResult()).thenReturn(Usuario);

        Result result = usuarioJPADAOImplementation.UsuarioGetSolo(1);

        assertTrue(result.correct);
        assertNotNull(result.object);
        UsuarioDireccion usuarioDireccion = (UsuarioDireccion) result.object;
        assertEquals("Adalberto", usuarioDireccion.Usuario.getNombreUsuario());

        verify(entityManager).createQuery("FROM Usuario WHERE IdUsuario = :idUsuario", Usuario.class);
        verify(mockQuery).setParameter("idUsuario", 1);
        verify(mockQuery).getSingleResult();
    }

    @Test
    public void UsuarioGetById() throws IOException {
        Usuario Usuario = new Usuario();
        Usuario.setIdUsuario(1);
        Usuario.setNombreUsuario("Adal");
        Usuario.setApellidoPatUsuario("Salinas");
        Usuario.setApellidoMatUsuario("Jose");
//        usuarioDireccion.usuario.setFechaNacimeintoUsuario(usuarioDireccion.usuario.getFechaNacimeintoUsuario());
        Usuario.setSexoUsuario("H");
        Usuario.setCorreoUsuario("asalinasjoses@gmail.com");
        Usuario.setCelularUsuario("55-1984-4862");
        Usuario.setPasswordUsuario("MockitoPass");
        Usuario.setTelefonoUsuario("54-4651-4893");
        Usuario.setCURPUsuario("OAS040404");
        Usuario.setUserNombreUsuario("Cuenta_Adal");
        Usuario.setActivoUsuario(1);

        Usuario.Roll = new Roll();
        Usuario.Roll.setIdRoll(4);

        List<Usuario> usuariosMock = List.of(Usuario);

        Direccion Direccion = new Direccion();
        Direccion.setIdDireccion(1);
        Direccion.setCalle("Calle falsa");
        Direccion.setNumeroInterior("1465");
        Direccion.setNumeroExterior("279");
        Direccion.setActivoDireccion(1);

//            Colonia colonia = com.disgis01.ASalinasNCapas.ML.Colonia();
        Direccion.Colonia = new Colonia();
        Direccion.Colonia.setIdColonia(5);

        Direccion.Usuario = new Usuario();
        Direccion.Usuario.setIdUsuario(24);
//        usuarioDireccion.usuario.setIdUsuario(24);
        List<Direccion> direccionesMock = List.of(Direccion);

        TypedQuery<Usuario> usuarioQueryMock = mock(TypedQuery.class);
        when(entityManager.createQuery(anyString(), eq(Usuario.class))).thenReturn(usuarioQueryMock);
        when(usuarioQueryMock.setParameter(anyString(), anyInt())).thenReturn(usuarioQueryMock);
        when(usuarioQueryMock.getResultList()).thenReturn(usuariosMock);

        TypedQuery<Direccion> direccionQueryMock = mock(TypedQuery.class);
        when(entityManager.createQuery("FROM Direccion WHERE Usuario.IdUsuario = :idusuario AND ActivoDireccion = 1", Direccion.class))
                .thenReturn(direccionQueryMock);
        when(direccionQueryMock.setParameter("idusuario", 1)).thenReturn(direccionQueryMock); // encadenamiento
        when(direccionQueryMock.getResultList()).thenReturn(direccionesMock);

        Result result = usuarioJPADAOImplementation.GetById(1);

        Assertions.assertNotNull(result.correct);
        Assertions.assertNull(result.ex);
        Assertions.assertNull(result.errorMasassge);
        Assertions.assertNotNull(result.object);
        Assertions.assertNull(result.objects);

        verify(entityManager).createQuery("FROM Usuario WHERE IdUsuario = :idUsuario", Usuario.class);
        verify(usuarioQueryMock).setParameter("idUsuario", 1);
        verify(usuarioQueryMock).getResultList();

        verify(entityManager).createQuery("FROM Direccion WHERE Usuario.IdUsuario = :idusuario AND ActivoDireccion = 1", Direccion.class);
        verify(direccionQueryMock).setParameter("idusuario", 1);
        verify(direccionQueryMock).getResultList();

        verifyNoMoreInteractions(entityManager, usuarioQueryMock, direccionQueryMock);
    }

    @Test
    public void BusquedaDinamica() throws IOException {
        Usuario Usuario = new Usuario();
        Usuario.setIdUsuario(1);
        Usuario.setNombreUsuario("Adalberto");
        Usuario.setApellidoPatUsuario("Salinas");
        Usuario.setApellidoMatUsuario("Jose");
//        usuarioDireccion.usuario.setFechaNacimeintoUsuario(usuarioDireccion.usuario.getFechaNacimeintoUsuario());
        Usuario.setSexoUsuario("H");
        Usuario.setCorreoUsuario("asalinasjoses@gmail.com");
        Usuario.setCelularUsuario("55-1984-4862");
        Usuario.setPasswordUsuario("MockitoPass");
        Usuario.setTelefonoUsuario("54-4651-4893");
        Usuario.setCURPUsuario("OAS040404");
        Usuario.setUserNombreUsuario("Cuenta_Adal");
        Usuario.setActivoUsuario(1);

        Usuario.Roll = new Roll();
        Usuario.Roll.setIdRoll(4);

        List<Usuario> usuariosMock = List.of(Usuario);

        Direccion Direccion = new Direccion();
        Direccion.setIdDireccion(1);
        Direccion.setCalle("Calle falsa");
        Direccion.setNumeroInterior("1465");
        Direccion.setNumeroExterior("279");
        Direccion.setActivoDireccion(1);

        Direccion.Colonia = new Colonia();
        Direccion.Colonia.setIdColonia(5);
        Direccion.Colonia.setNombreColonia("Unidad Vivienda");
        Direccion.Colonia.setCodigoPostal("54900");

        Direccion.Colonia.Municipio = new Municipio();
        Direccion.Colonia.Municipio.setIdMunicipio(1);
        Direccion.Colonia.Municipio.setNombreMunicipio("Cuautitlan");

        Direccion.Colonia.Municipio.Estado = new Estado();
        Direccion.Colonia.Municipio.Estado.setIdEstado(1);
        Direccion.Colonia.Municipio.Estado.setNombreEstado("DF");

        Direccion.Colonia.Municipio.Estado.Pais = new Pais();
        Direccion.Colonia.Municipio.Estado.Pais.setIdPais(1);
        Direccion.Colonia.Municipio.Estado.Pais.setNombrePais("Mexico");

        Direccion.Usuario = new Usuario();
        Direccion.Usuario.setIdUsuario(1);
        Direccion.Usuario.setIdUsuario(1);
        List<Direccion> direccionesMock = List.of(Direccion);

        String Consulta = "FROM Usuario WHERE UPPER(NombreUsuario) LIKE UPPER(:nombreusuario) "
                + " AND LOWER(ApellidoPatUsuario) LIKE LOWER(:apepaterno) "
                + "AND LOWER(ApellidoMatUsuario) LIKE LOWER(:apematerno) ";
        Consulta += (Usuario.Roll.getIdRoll() != 0 ? " AND Roll.IdRoll = : idroll" : "");
        TypedQuery<Usuario> usuarioQueryMock = mock(TypedQuery.class);
        usuarioQueryMock.setParameter("nombreusuario", "%be%");
        usuarioQueryMock.setParameter("apepaterno", "%al%");
        usuarioQueryMock.setParameter("apematerno", "%%");
        if (Usuario.Roll.getIdRoll() > 0) {
            usuarioQueryMock.setParameter("idroll", 1);
        }
        when(entityManager.createQuery(Consulta + " order by id ASC", Usuario.class))
                .thenReturn(usuarioQueryMock);
        when(usuarioQueryMock.getResultList()).thenReturn(usuariosMock);

        TypedQuery<Direccion> direccionQueryMock = mock(TypedQuery.class);
        when(entityManager.createQuery("FROM Direccion WHERE Usuario.IdUsuario = :idusuario", Direccion.class))
                .thenReturn(direccionQueryMock);
        when(direccionQueryMock.setParameter("idusuario", 1)).thenReturn(direccionQueryMock); // encadenamiento
        when(direccionQueryMock.getResultList()).thenReturn(direccionesMock);

        UsuarioDireccion usuarioBusqueda = new UsuarioDireccion();
        usuarioBusqueda.Usuario = Usuario;
        usuarioBusqueda.Direccion = Direccion;
        Result result = usuarioJPADAOImplementation.UsuarioBusqueda(usuarioBusqueda);

        Assertions.assertNotNull(result.correct);
        Assertions.assertNull(result.ex);
        Assertions.assertNull(result.errorMasassge);
        Assertions.assertNull(result.object);
        Assertions.assertNotNull(result.objects);

        verify(entityManager).createQuery(Consulta + " order by id ASC", Usuario.class);
        verify(usuarioQueryMock).getResultList();

        verify(entityManager).createQuery("FROM Direccion WHERE Usuario.IdUsuario = :idusuario", Direccion.class);
        verify(direccionQueryMock).setParameter("idusuario", 1);
        verify(direccionQueryMock).getResultList();

//        verifyNoMoreInteractions(entityManager, result);
        verifyNoMoreInteractions(entityManager, usuarioQueryMock, direccionQueryMock);
    }

    @Test
    public void GetAllRoll() throws IOException {
        Roll Roll = new Roll();
        Roll.setIdRoll(1);
        Roll.setNombreRoll("Administrador");

        List<Roll> rollesMock = List.of(Roll);

        TypedQuery<Roll> RollQueryMock = mock(TypedQuery.class);
        when(entityManager.createQuery("FROM Roll order by id ASC", Roll.class))
                .thenReturn(RollQueryMock);
        when(RollQueryMock.getResultList()).thenReturn(rollesMock);

        Result result = rollJPADAOImplementation.GetAllRoll();

        Assertions.assertNotNull(result.correct);
        Assertions.assertNull(result.ex);
        Assertions.assertNull(result.errorMasassge);
        Assertions.assertNull(result.object);
        Assertions.assertNotNull(result.objects);

        verify(entityManager).createQuery("FROM Roll order by id ASC", Roll.class);
        verify(RollQueryMock).getResultList();

        verifyNoMoreInteractions(entityManager, RollQueryMock);
    }

    @Test
    public void AddUsuairoDireccion() throws IOException {
        UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
        usuarioDireccion.Usuario = new Usuario();
        usuarioDireccion.Usuario.setNombreUsuario("Adal");
        usuarioDireccion.Usuario.setApellidoPatUsuario("Salinas");
        usuarioDireccion.Usuario.setApellidoMatUsuario("Jose");
//        usuarioDireccion.usuario.setFechaNacimeintoUsuario(usuarioDireccion.usuario.getFechaNacimeintoUsuario());
        usuarioDireccion.Usuario.setSexoUsuario("H");
        usuarioDireccion.Usuario.setCorreoUsuario("asalinasjoses@gmail.com");
        usuarioDireccion.Usuario.setCelularUsuario("55-1984-4862");
        usuarioDireccion.Usuario.setPasswordUsuario("MockitoPass");
        usuarioDireccion.Usuario.setTelefonoUsuario("54-4651-4893");
        usuarioDireccion.Usuario.setCURPUsuario("OAS040404");
        usuarioDireccion.Usuario.setUserNombreUsuario("Cuenta_Adal");
        usuarioDireccion.Usuario.setActivoUsuario(1);

        usuarioDireccion.Usuario.Roll = new Roll();
        usuarioDireccion.Usuario.Roll.setIdRoll(4);

        usuarioDireccion.Direccion = new Direccion();
        usuarioDireccion.Direccion.setCalle("Calle falsa");
        usuarioDireccion.Direccion.setNumeroInterior("1465");
        usuarioDireccion.Direccion.setNumeroExterior("279");
        usuarioDireccion.Direccion.setActivoDireccion(1);

//            Colonia colonia = com.disgis01.ASalinasNCapas.ML.Colonia();
        usuarioDireccion.Direccion.Colonia = new Colonia();
        usuarioDireccion.Direccion.Colonia.setIdColonia(5);

        usuarioDireccion.Direccion.Usuario = new Usuario();
        usuarioDireccion.Direccion.Usuario.setIdUsuario(24);
//        usuarioDireccion.usuario.setIdUsuario(24);

        Result result = new Result();

        usuarioJPADAOImplementation.Add(usuarioDireccion);

        Assertions.assertNotNull(result.correct);
        Assertions.assertNull(result.ex);
        Assertions.assertNull(result.errorMasassge);
        Assertions.assertNull(result.object);
        Assertions.assertNull(result.objects);

//        OutputStream mockStream = mock(OutputStream.class);
//        doThrow(new IOException()).when(usuarioDireccion) ;
        verify(entityManager, times(1)).persist(Mockito.any(usuarioDireccion.Usuario.getClass()));
        verify(entityManager, times(1)).persist(Mockito.any(usuarioDireccion.Direccion.getClass()));
    }

    @Test
    public void UpdateUsuairo() throws IOException {
        UsuarioDireccion usuarioDireccion = new UsuarioDireccion();

        usuarioDireccion.Usuario = new Usuario();
        usuarioDireccion.Usuario.setIdUsuario(14);
        usuarioDireccion.Usuario.setNombreUsuario("Adal");
        usuarioDireccion.Usuario.setApellidoPatUsuario("Salinas");
        usuarioDireccion.Usuario.setApellidoMatUsuario("Jose");
//        usuarioDireccion.usuario.setFechaNacimeintoUsuario(usuarioDireccion.usuario.getFechaNacimeintoUsuario());
        usuarioDireccion.Usuario.setSexoUsuario("H");
        usuarioDireccion.Usuario.setCorreoUsuario("asalinasjoses@gmail.com");
        usuarioDireccion.Usuario.setCelularUsuario("55-1984-4862");
        usuarioDireccion.Usuario.setPasswordUsuario("MockitoPass");
        usuarioDireccion.Usuario.setTelefonoUsuario("54-4651-4893");
        usuarioDireccion.Usuario.setCURPUsuario("OAS040404");
        usuarioDireccion.Usuario.setUserNombreUsuario("Cuenta_Adal");
        usuarioDireccion.Usuario.setActivoUsuario(1);

        usuarioDireccion.Usuario.Roll = new Roll();
        usuarioDireccion.Usuario.Roll.setIdRoll(4);

        Result result = new Result();

        Assertions.assertNotNull(result.correct);
        Assertions.assertNull(result.ex);
        Assertions.assertNull(result.errorMasassge);
        Assertions.assertNull(result.object);
        Assertions.assertNull(result.objects);

        usuarioJPADAOImplementation.Update(usuarioDireccion);

        verify(entityManager, times(1)).merge(Mockito.any(usuarioDireccion.Usuario.getClass()));
    }

    @Test
    public void DeleteUsuairo() throws IOException {
        UsuarioDireccion usuarioDireccion = new UsuarioDireccion();

        usuarioDireccion.Usuario = new Usuario();
        usuarioDireccion.Usuario.setIdUsuario(15);

        Result result = new Result();

        Assertions.assertNotNull(result.correct);
        Assertions.assertNull(result.ex);
        Assertions.assertNull(result.errorMasassge);
        Assertions.assertNull(result.object);
        Assertions.assertNull(result.objects);

        usuarioJPADAOImplementation.Delete(15);

        verify(entityManager, times(1)).merge(Mockito.any(usuarioDireccion.Usuario.getClass()));
    }

}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.disgis01.ASalinasNCapas;

import com.disgis01.ASalinasNCapas.DAO.ColoniaJPADAOImplementation;
import com.disgis01.ASalinasNCapas.DAO.DireccionJPADAOImplementation;
import com.disgis01.ASalinasNCapas.DAO.EstadoJPADAOImplementation;
import com.disgis01.ASalinasNCapas.DAO.MunicipioJPADAOImplementation;
import com.disgis01.ASalinasNCapas.DAO.PaisJPADAOImplementation;
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
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
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
public class DireccionMockitoTest {

    @Mock
    private EntityManager entityManager;

    @InjectMocks
    private DireccionJPADAOImplementation direccionJPADAOImplementation;
    @InjectMocks
    private PaisJPADAOImplementation paisJPADAOImplementation;
    @InjectMocks
    private EstadoJPADAOImplementation estadoJPADAOImplementation;
    @InjectMocks
    private MunicipioJPADAOImplementation municipioJPADAOImplementation;
    @InjectMocks
    private ColoniaJPADAOImplementation coloniaJPADAOImplementation;

    @Test
    public void AddDireccion() throws IOException {
        UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
        usuarioDireccion.Usuario = new Usuario();
        usuarioDireccion.Usuario.setIdUsuario(24);

        usuarioDireccion.Direccion = new Direccion();
        usuarioDireccion.Direccion.setCalle("Calle falsa");
        usuarioDireccion.Direccion.setNumeroInterior("1465");
        usuarioDireccion.Direccion.setNumeroExterior("279");
        usuarioDireccion.Direccion.setActivoDireccion(1);

//            Colonia colonia = com.disgis01.ASalinasNCapas.ML.Colonia();
        usuarioDireccion.Direccion.Colonia = new Colonia();
        usuarioDireccion.Direccion.Colonia.setIdColonia(5);

        Result result = new Result();

        Assertions.assertNotNull(result.correct);
        Assertions.assertNull(result.ex);
        Assertions.assertNull(result.errorMasassge);
        Assertions.assertNull(result.object);
        Assertions.assertNull(result.objects);

        direccionJPADAOImplementation.Add(usuarioDireccion);

        verify(entityManager, times(1)).persist(Mockito.any(usuarioDireccion.Direccion.getClass()));
    }

    @Test
    public void UpdateDireccion() throws IOException {
        UsuarioDireccion usuarioDireccion = new UsuarioDireccion();

        usuarioDireccion.Direccion = new Direccion();
        usuarioDireccion.Direccion.setIdDireccion(4);
        usuarioDireccion.Direccion.setCalle("Calle falsa");
        usuarioDireccion.Direccion.setNumeroInterior("1465");
        usuarioDireccion.Direccion.setNumeroExterior("279");
        usuarioDireccion.Direccion.setActivoDireccion(1);

        usuarioDireccion.Direccion.Colonia = new Colonia();
        usuarioDireccion.Direccion.Colonia.setIdColonia(5);

        Result result = new Result();

        Assertions.assertNotNull(result.correct);
        Assertions.assertNull(result.ex);
        Assertions.assertNull(result.errorMasassge);
        Assertions.assertNull(result.object);
        Assertions.assertNull(result.objects);

        direccionJPADAOImplementation.Update(usuarioDireccion);

        verify(entityManager, times(1)).merge(Mockito.any(usuarioDireccion.Direccion.getClass()));
    }

    @Test
    public void DeleteDireccion() throws IOException {
        UsuarioDireccion usuarioDireccion = new UsuarioDireccion();

        usuarioDireccion.Direccion = new Direccion();
        usuarioDireccion.Direccion.setIdDireccion(15);

        Result result = new Result();

        Assertions.assertNotNull(result.correct);
        Assertions.assertNull(result.ex);
        Assertions.assertNull(result.errorMasassge);
        Assertions.assertNull(result.object);
        Assertions.assertNull(result.objects);

        direccionJPADAOImplementation.Delete(15);

        verify(entityManager, times(1)).merge(Mockito.any(usuarioDireccion.Direccion.getClass()));
    }

    @Test
    public void DireccionGetById() throws IOException {
        UsuarioDireccion usuarioDireccion = new UsuarioDireccion();

        usuarioDireccion.Direccion = new Direccion();
        usuarioDireccion.Direccion.setIdDireccion(15);

        Result result = new Result();

        Assertions.assertNotNull(result.correct);
        Assertions.assertNull(result.ex);
        Assertions.assertNull(result.errorMasassge);
        Assertions.assertNull(result.object);
        Assertions.assertNull(result.objects);

        direccionJPADAOImplementation.Delete(15);

        verify(entityManager, times(1)).merge(Mockito.any(usuarioDireccion.Direccion.getClass()));
    }

    @Test
    public void GetAllPais() throws IOException {
        Pais Pais = new Pais();
        Pais.setIdPais(1);
        Pais.setNombrePais("Mexico");

        List<Pais> rollesMock = List.of(Pais);

        TypedQuery<Pais> PaisQueryMock = mock(TypedQuery.class);
        when(entityManager.createQuery("FROM Pais order by id ASC", Pais.class))
                .thenReturn(PaisQueryMock);
        when(PaisQueryMock.getResultList()).thenReturn(rollesMock);

        Result result = paisJPADAOImplementation.GetAllPais();

        Assertions.assertNotNull(result.correct);
        Assertions.assertNull(result.ex);
        Assertions.assertNull(result.errorMasassge);
        Assertions.assertNull(result.object);
        Assertions.assertNotNull(result.objects);

        verify(entityManager).createQuery("FROM Pais order by id ASC", Pais.class);
        verify(PaisQueryMock).getResultList();

        verifyNoMoreInteractions(entityManager, PaisQueryMock);
    }

    @Test
    public void EstadoGetByIdPais() throws IOException {
        Estado Estado = new Estado();
        Estado.setIdEstado(1);
        Estado.setNombreEstado("Oaxaca");

        List<Estado> estadosMock = List.of(Estado);

        TypedQuery<Estado> estadoQueryMock = mock(TypedQuery.class);
        when(entityManager.createQuery("FROM Estado WHERE Pais.IdPais = :idPais", Estado.class))
                .thenReturn(estadoQueryMock);
        when(estadoQueryMock.setParameter("idPais", 1)).thenReturn(estadoQueryMock); // encadenamiento
        when(estadoQueryMock.getResultList()).thenReturn(estadosMock);

        Result result = estadoJPADAOImplementation.GetByIdEstados(1);

        Assertions.assertNotNull(result.correct);
        Assertions.assertNull(result.ex);
        Assertions.assertNull(result.errorMasassge);
        Assertions.assertNull(result.object);
        Assertions.assertNotNull(result.objects);

        verify(entityManager).createQuery("FROM Estado WHERE Pais.IdPais = :idPais", Estado.class);
        verify(estadoQueryMock).setParameter("idPais", 1);
        verify(estadoQueryMock).getResultList();

        verifyNoMoreInteractions(entityManager, estadoQueryMock);
    }

    @Test
    public void MunicipioGetByIdEstado() throws IOException {
        Municipio Municipio = new Municipio();
        Municipio.setIdMunicipio(1);
        Municipio.setNombreMunicipio("Oaxaca");

        List<Municipio> municipiosMock = List.of(Municipio);

        TypedQuery<Municipio> municipioQueryMock = mock(TypedQuery.class);
        when(entityManager.createQuery("FROM Municipio WHERE Estado.IdEstado = :idEstado order by id ASC", Municipio.class))
                .thenReturn(municipioQueryMock);
        when(municipioQueryMock.setParameter("idEstado", 1)).thenReturn(municipioQueryMock); // encadenamiento
        when(municipioQueryMock.getResultList()).thenReturn(municipiosMock);

        Result result = municipioJPADAOImplementation.GetByIdMunicipios(1);

        Assertions.assertNotNull(result.correct);
        Assertions.assertNull(result.ex);
        Assertions.assertNull(result.errorMasassge);
        Assertions.assertNull(result.object);
        Assertions.assertNotNull(result.objects);

        verify(entityManager).createQuery("FROM Municipio WHERE Estado.IdEstado = :idEstado order by id ASC", Municipio.class);
        verify(municipioQueryMock).setParameter("idEstado", 1);
        verify(municipioQueryMock).getResultList();

        verifyNoMoreInteractions(entityManager, municipioQueryMock);
    }

    @Test
    public void ColoniaGetByIdMunicipio() throws IOException {
        Colonia Colonia = new Colonia();
        Colonia.setIdColonia(1);
        Colonia.setNombreColonia("Oaxaca");
        Colonia.setCodigoPostal("54900");

        List<Colonia> coloniaMock = List.of(Colonia);

        TypedQuery<Colonia> coloniaQueryMock = mock(TypedQuery.class);
        when(entityManager.createQuery("FROM Colonia WHERE Municipio.IdMunicipio=: idMunicipio order by id ASC", Colonia.class))
                .thenReturn(coloniaQueryMock);
        when(coloniaQueryMock.setParameter("idMunicipio", 1)).thenReturn(coloniaQueryMock); // encadenamiento
        when(coloniaQueryMock.getResultList()).thenReturn(coloniaMock);

        Result result = coloniaJPADAOImplementation.GetByIdColonias(1);

        Assertions.assertNotNull(result.correct);
        Assertions.assertNull(result.ex);
        Assertions.assertNull(result.errorMasassge);
        Assertions.assertNull(result.object);
        Assertions.assertNotNull(result.objects);

        verify(entityManager).createQuery("FROM Colonia WHERE Municipio.IdMunicipio=: idMunicipio order by id ASC", Colonia.class);
        verify(coloniaQueryMock).setParameter("idMunicipio", 1);
        verify(coloniaQueryMock).getResultList();

        verifyNoMoreInteractions(entityManager, coloniaQueryMock);
    }

}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.disgis01.ASalinasNCapas;

import com.disgis01.ASalinasNCapas.Controller.MercadoController;
import com.disgis01.ASalinasNCapas.JPA.Estadistica;
import com.disgis01.ASalinasNCapas.JPA.Extraer;
import com.disgis01.ASalinasNCapas.JPA.ScraperService;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import static org.codehaus.groovy.tools.shell.util.Preferences.get;
import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.stereotype.Controller;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Alien 1
 */
@Controller
@RequestMapping("/test")
public class NewsControllerTest {

    @InjectMocks
    private MercadoController mercadoController;

    @Mock
    private ScraperService scraperService;

    @Mock
    private Model model;

    // Test para /api/seccion
    @Test
    void testSeccion() {
        Extraer extraer = new Extraer();
        List<Extraer> mockProductos = List.of(new Extraer(), new Extraer());
        extraer.setId(1L);
        extraer.setTitulo("titulo d eejemplo");
        extraer.setDescripcion("descripcion de prueba");
        extraer.setPrecio("precio de prueba");
        extraer.setUrl("url de prueba");
        extraer.setImagen("imagen de prueba");

        when(scraperService.listarTodos()).thenReturn(mockProductos);

        String viewName = mercadoController.Seccion(model);

        assertNotNull("IndexMercado", viewName);
        verify(model).addAttribute("productos", mockProductos);
    }

    // Test para /api/seccion/search
    @Test
    void testSearch() {
        String query = "manzana";
        List<Extraer> mockProductos = List.of(new Extraer());

        when(scraperService.buscarPorPalabra(query)).thenReturn(mockProductos);

        String viewName = mercadoController.Search(query, model);

        assertEquals("redirect:/api/seccion", viewName);
        verify(model).addAttribute("productos", mockProductos);
    }

    // Test para /api/seccion/scrape
    @Test
    void testScrape() throws IOException {
        String busqueda = "banana";
        List<Extraer> mockProductos = List.of(new Extraer());

        when(scraperService.scrape(busqueda)).thenReturn(mockProductos);

        String viewName = mercadoController.scrape(busqueda, model);

        assertEquals("redirect:/api/seccion", viewName);
        verify(model).addAttribute("productos", mockProductos);
    }

    // Test para /api/estadistica
    @Test
    void testEstadistica() {
        Estadistica estadisticaMock = new Estadistica();

        estadisticaMock.setMaxLongitud(20);
        estadisticaMock.setMinLongitud(3);
        estadisticaMock.setPromedioLongitud(12);
        estadisticaMock.setTotal((long) 5468.245);
        when(scraperService.obtenerEstadisticas()).thenReturn(estadisticaMock);

        Estadistica result = mercadoController.Estadistica();

        assertEquals(estadisticaMock, result);
    }

}

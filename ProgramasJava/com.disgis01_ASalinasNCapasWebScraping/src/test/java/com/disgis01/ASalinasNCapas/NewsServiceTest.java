/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.disgis01.ASalinasNCapas;

import com.disgis01.ASalinasNCapas.DAO.ExtraerRepository;
import com.disgis01.ASalinasNCapas.JPA.Estadistica;
import com.disgis01.ASalinasNCapas.JPA.Extraer;
import com.disgis01.ASalinasNCapas.JPA.ScraperService;
import com.disgis01.ASalinasNCapas.ML.Result;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Alien 1
 */
@ExtendWith(MockitoExtension.class)
public class NewsServiceTest {
    
     @Mock
    private ExtraerRepository extraerRepository;

     @InjectMocks
    private ScraperService scraperService;
     
     @Test
    void testListarTodos() {
        List<Extraer> mockLista = List.of(new Extraer(), new Extraer());
        when(extraerRepository.findAll()).thenReturn(mockLista);

        List<Extraer> resultado = scraperService.listarTodos();

        assertEquals(2, resultado.size());
        verify(extraerRepository).findAll();
    }

    @Test
    public void BuscarPorPalabra() {
        String palabraClave = "lenovo";
 
         List<Extraer> result = scraperService.buscarPorPalabra(palabraClave);
 
        Assertions.assertNotNull(result);
//        Assertions.assertTrue(result.correct);
//        Assertions.assertNotNull(result.objects);
//        Assertions.assertNull(result.object);
//        Assertions.assertNull(result.ex);
    }
    @Test
    public void Estadisticas(){
        Estadistica result = scraperService.obtenerEstadisticas();
 
        Assertions.assertNotNull(result);
        Assertions.assertNotNull(result.getMinLongitud());
        Assertions.assertNotNull(result.getMaxLongitud()); 
        Assertions.assertNotNull(result.getMaximo());
//        Assertions.assertNuassertNotNullll(result.ex);
    }
}

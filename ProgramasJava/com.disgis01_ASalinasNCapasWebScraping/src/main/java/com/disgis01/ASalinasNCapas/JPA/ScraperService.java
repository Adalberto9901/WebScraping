/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.disgis01.ASalinasNCapas.JPA;

import com.disgis01.ASalinasNCapas.DAO.ExtraerRepository;
import java.io.IOException;
import java.util.ArrayList;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.stream.Collectors;
import org.jsoup.nodes.Document;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Alien 1
 */
@Service
public class ScraperService {

    @Autowired
    private ExtraerRepository extraerRepository;

    public List<Extraer> listarTodos() {
        return extraerRepository.findAll();
    }

    public List<Extraer> buscarPorPalabra(String q) {
//        extraerRepository.deleteAll();
        return extraerRepository.findByTituloContainingIgnoreCase(q);
    }

    public List<Element> scrape(String busqueda) throws IOException {
        extraerRepository.deleteAll();
        String url = "https://listado.mercadolibre.com.mx/" + busqueda.replace(" ", "-");
//        String url = "https://listado.mercadolibre.com.mx/" + busqueda;
        Document doc = Jsoup.connect(url).get();

//        List<Element> elements = doc.getElementsByClass("poly-card");
        List<Element> elements = doc.getElementsByClass("ui-search-result__wrapper");

        for (Element element : elements) {
            System.out.println(element);
            String titulo = element.select(".ui-search-item__title").text();
            String precio = element.select(".andes-money-amount__fraction").text();
            String link = element.select(".ui-search-link").attr("href");
        }

//        List<Extraer> resultados = doc.select("div.poly-card poly-card--list poly-card--large poly-card--CORE")
//                .stream()
//                .limit(20)
//                .map(element -> {
//                    String titulo = element.text().replaceAll("[^\\p{L}\\p{N}\\s]", "").trim();
//                    String descripcion = element.text().replaceAll(".andes-money-amount__fraction", "").trim();
//                    String precio = element.text().replaceAll(".andes-money-amount__fraction", "").trim();
//                    String link = element.absUrl("href");
//                    String imagen = element.absUrl(".poly-component__picture");
////                    String titulo = productElement.select(".ui-search-item__title").text();
////                    String precio = productElement.select(".andes-money-amount__fraction").text();
////                    String link = productElement.select(".ui-search-link").attr("href");
//                    return new Extraer(null, titulo, descripcion, precio, link, imagen);
//                })
//                .filter(s -> s.getTitulo() != null && !s.getTitulo().isBlank())
//                .filter(s -> s.getUrl() != null && !s.getUrl().isBlank())
//                .filter(s -> !extraerRepository.existsByUrl(s.getUrl()))
//                .collect(Collectors.toList());
//
//        return extraerRepository.saveAll(resultados);
        return elements;
    }

    public Estadistica obtenerEstadisticas() {
        List<Extraer> lista = extraerRepository.findAll();
        IntSummaryStatistics stats = lista.stream()
                .mapToInt(s -> s.getTitulo().length())
                .summaryStatistics();

        return new Estadistica(lista.size(), stats.getAverage(), stats.getMax(), stats.getMin());
    }
}

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
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Alien 1
 */
@Service
public class SessionServicie {
    @Autowired
    private ExtraerRepository extraerRepository;

    public List<Extraer> listarTodos() {
        return extraerRepository.findAll();
    }

    public List<Extraer> scrapeDesdeBusqueda(String busqueda) {
        List<Extraer> resultados = new ArrayList<>();

        extraerRepository.deleteAll();
        try {
            String url = "https://listado.mercadolibre.com.mx/" + busqueda.replace(" ", "-");
            Document doc = Jsoup.connect(url)
//                    .userAgent("Mozilla/5.0")
                    .timeout(10000)
                    .get();

            Elements productos = doc.select("div.andes-carousel-snapped__controls-wrapper");

            for (Element producto : productos) {
                Extraer item = new Extraer();

                Element tituloEl = producto.selectFirst("span.poly-component__brand");
                item.setTitulo(tituloEl != null ? tituloEl.text() : "Sin título");

                Element descripcionEl = producto.selectFirst("a.poly-component__title");
                item.setDescripcion(descripcionEl != null ? descripcionEl.text() : "Sin descripción");

                Element precioEl = producto.selectFirst("div.poly-component__price");
                item.setPrecio(precioEl != null ? precioEl.text() : "Sin precio");

                Element linkEl = producto.selectFirst("a.poly-component__title");
                item.setUrl(linkEl != null ? linkEl.absUrl("href") : "#");

                Element portada = producto.selectFirst("div.poly-card__portada img");
                item.setImagen(portada != null ? portada.absUrl("src") : "sin imagen");

                extraerRepository.save(item);
                resultados.add(item);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return resultados;
    }

    public List<Extraer> buscarPorPalabra(String q) {
//        extraerRepository.deleteAll();
       return extraerRepository.findByTituloContainingIgnoreCase(q);
    }
    
    public List<Extraer> scrape2(String busqueda) throws IOException {
        extraerRepository.deleteAll();
        String url = "https://listado.mercadolibre.com.mx/" + busqueda.replace(" ", "-");
        Document doc = Jsoup.connect(url).userAgent("Mozilla/5.0").get();
 
        List<Extraer> resultados = doc.select("a.ui-search-link") 
                .stream()
                .limit(20)
                .map(element -> {
                    String titulo = element.text().replaceAll("[^\\p{L}\\p{N}\\s]", "").trim();
                    String link = element.absUrl("href");
                    return new Extraer(null, titulo, link);
                })
                .filter(s -> s.getTitulo() != null && !s.getTitulo().isBlank())
                .filter(s -> s.getUrl() != null && !s.getUrl().isBlank())
                .filter(s -> !extraerRepository.existsByUrl(s.getUrl()))
                .collect(Collectors.toList());
 
        return extraerRepository.saveAll(resultados);
    }
    
        public Estadistica obtenerEstadisticas() {
        List<Extraer> lista = extraerRepository.findAll();
        IntSummaryStatistics stats = lista.stream()
                .mapToInt(s -> s.getTitulo().length())
                .summaryStatistics();
 
        return new Estadistica(lista.size(), stats.getAverage(), stats.getMax(), stats.getMin());
    }
        //**********
        
        public List<Element> scrape(String busqueda) throws IOException {
        extraerRepository.deleteAll();
        String url = "https://listado.mercadolibre.com.mx/" + busqueda.replace(" ", "-");
        Document doc = Jsoup.connect(url).get();

        List<Element> elementsGeneral = doc.getElementsByClass("ui-search-layout"); 

        for (Element elementCompleto : elementsGeneral) {
            
                    List<Element> elementsCajas = doc.getElementsByClass("poly-component__title");//por cada elemento caja
                    for (Element elementCaja : elementsCajas) {
                        System.out.println("======== Seccion Url, Desc=========");
                        System.out.println(elementCaja);
            }
                    List<Element> elementsPrecios = doc.getElementsByClass("andes-money-amount__fraction");//por cada elemento caja
                    for (Element elementPrecio : elementsPrecios) {
                        System.out.println("======== Seccion Precios=========");
                        System.out.println(elementPrecio);
            }
                    List<Element> elementsInfos = doc.getElementsByClass("ui-search-result__wrapper");//por info
                    for (Element elementInfo : elementsInfos) {
                        System.out.println("======== Seccion Informaciones=========");
                        System.out.println(elementInfo);
                        
            }
                    List<Element> elementsImgs = doc.getElementsByClass("poly-component__picture");//por imagen
                    for (Element elementImg : elementsImgs) {
                        System.out.println("======== Seccion Img=========");
                        System.out.println(elementImg);
            }
                    List<Element> productTitles = doc.getElementsByClass("poly-component__brand");
                for (Element title : productTitles) {
                        System.out.println("======== Seccion Titulo=========");
                    System.out.println(title);
                }
        }

        return elementsGeneral;
    }

}

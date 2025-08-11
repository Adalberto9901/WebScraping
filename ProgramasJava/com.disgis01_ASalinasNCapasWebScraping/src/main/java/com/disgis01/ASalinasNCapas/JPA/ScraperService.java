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
        extraerRepository.deleteAll();
        return extraerRepository.findByTituloContainingIgnoreCase(q);
    }

    public List<Extraer> scrape(String busqueda) throws IOException {
        extraerRepository.deleteAll();
        String url = "https://listado.mercadolibre.com.mx/" + busqueda.replace(" ", "-");
        Document doc = Jsoup.connect(url).get();

        List<Extraer> productosGuardados = new ArrayList<>();
        Elements productos = doc.getElementsByClass("ui-search-layout__item");

        for (Element producto : productos) {
            try {
                Element tituloLink = producto.selectFirst("h3.poly-component__title-wrapper > a.poly-component__title");
                String titulo = tituloLink != null ? tituloLink.text() : "";
                String productoUrl = tituloLink != null ? tituloLink.absUrl("href") : "";

                Element descripcionElement = producto.selectFirst(".poly-component__brand");
                String descripcion = descripcionElement != null ? descripcionElement.text() : ""; 

                Element precioElement = producto.selectFirst(".andes-money-amount__fraction");
                String precio = precioElement != null ? precioElement.text() : "";

                Element imagenElement = producto.selectFirst("img");
                String imagen = imagenElement != null ? imagenElement.attr("data-src") : "";

                Extraer extraer = new Extraer();
                extraer.setTitulo(titulo);
                extraer.setDescripcion(descripcion);
                extraer.setPrecio(precio);
                extraer.setUrl(productoUrl);
                extraer.setImagen(imagen);

                extraerRepository.save(extraer);
                productosGuardados.add(extraer);

            } catch (Exception e) {
                System.out.println("Error al procesar un producto: " + e.getMessage());
            }
        }

        return productosGuardados;
    }

    public Estadistica obtenerEstadisticas() {
        List<Extraer> lista = extraerRepository.findAll();
        IntSummaryStatistics stats = lista.stream()
                .mapToInt(s -> s.getTitulo().length())
                .summaryStatistics();

        return new Estadistica(lista.size(), stats.getAverage(), stats.getMax(), stats.getMin());
    }
}

//<ol class="ui-search-layout ui-search-layout--grid" data-cols="3">
// <li class="ui-search-layout__item">
//  <div style="display:contents">
//   <div class="ui-search-result__wrapper">
//    <div class="andes-card poly-card poly-card--grid-card poly-card--large poly-card--MOT andes-card--flat andes-card--padding-0 andes-card--animated" id=":R50llie:">
//     <div class="poly-card__portada">
//      <img title="Land Rover Evoque 5p Bronze Collection P250 L4/2.0/t/250 Aut Mhev" width="150" height="150" aria-hidden="true" decoding="async" src="data:image/gif;base64,R0lGODlhAQABAIAAAAAAAP///yH5BAEAAAAALAAAAAABAAEAAAIBRAA7" class="poly-component__picture lazy-loadable" data-src="https://http2.mlstatic.com/D_NQ_NP_2X_669397-MLM84017777427_042025-E-land-rover-evoque-5p-bronze-collection-p250-l420t250-au.webp" alt="Land Rover Evoque 5p Bronze Collection P250 L4/2.0/t/250 Aut Mhev">
//     </div>
//     <div class="poly-card__content">
//      <div class="poly-component__pill">
//       <div class="poly-pill__wrapper">
//        <span style="color:#3483FA;background-color:#DAE7FA" class="poly-pill__pill">AUTO VERIFICADO</span>
//       </div>
//      </div>
//      <h3 class="poly-component__title-wrapper"><a href="https://auto.mercadolibre.com.mx/MLM-2291017251-land-rover-evoque-5p-bronze-collection-p250-l420t250-au-_JM#polycard_client=search-nordic&amp;position=1&amp;search_layout=grid&amp;type=item&amp;tracking_id=e76184fe-2d4b-43b4-affe-6855041d2c8a" target="_blank" class="poly-component__title">Land Rover Evoque 5p Bronze Collection P250 L4/2.0/t/250 Aut Mhev</a></h3><span class="poly-component__seller">Por Grupo CEVER 
//       <svg aria-label="Tienda oficial" width="12" height="12" viewBox="0 0 12 12" role="img">
//        <use href="#poly_cockade"></use>
//       </svg></span>
//      <div class="poly-component__price">
//       <div class="poly-price__current">
//        <span class="andes-money-amount andes-money-amount--cents-superscript" style="font-size:24px" role="img" aria-label="899000 pesos mexicanos" aria-roledescription="Monto"><span class="andes-money-amount__currency-symbol" aria-hidden="true">$</span><span class="andes-money-amount__fraction" aria-hidden="true">899,000</span></span>
//       </div>
//      </div>
//      <div class="poly-component__attributes-list">
//       <ul class="poly-attributes_list" style="--separator-content:&quot;|&quot;;gap:4px">
//        <li class="poly-attributes_list__item poly-attributes_list__separator">2023</li>
//        <li class="poly-attributes_list__item poly-attributes_list__separator">36,738 Km</li>
//       </ul>
//      </div><span class="poly-component__location">Cuajimalpa De Morelos - Distrito Federal</span>
//     </div>
//     <div class="poly-component__bookmark" data-testid="bookmark">
//      <button type="button" class="poly-bookmark__btn" role="switch" aria-checked="false" aria-label="Favorito">
//       <svg class="poly-bookmark__icon-full" width="20" height="20" viewBox="0 0 20 20">
//        <use href="#poly_bookmark"></use>
//       </svg>
//       <svg class="poly-bookmark__icon-empty" width="20" height="20" viewBox="0 0 20 20">
//        <use href="#poly_bookmark"></use>
//       </svg></button>
//     </div>
//    </div>
//   </div>
//  </div></li>

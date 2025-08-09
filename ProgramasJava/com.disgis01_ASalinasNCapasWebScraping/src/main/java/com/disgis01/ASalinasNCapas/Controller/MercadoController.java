/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.disgis01.ASalinasNCapas.Controller;

import com.disgis01.ASalinasNCapas.JPA.Estadistica;
import com.disgis01.ASalinasNCapas.JPA.Extraer;
import com.disgis01.ASalinasNCapas.JPA.ScraperService;
import java.io.IOException;
import java.util.List;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author Alien 1
 */
@Controller
@RequestMapping("/api")
public class MercadoController {
    
    @Autowired
    private ScraperService scraperService;
    
    @GetMapping("/seccion")
    public String Seccion(Model model){
        List<Extraer> productos = scraperService.listarTodos();
        model.addAttribute("productos", productos);
        return "IndexMercado";
    }
    
    @GetMapping("/seccion/search")
        public String Search(@RequestParam String q, Model model) {
            List<Extraer> productos = scraperService.buscarPorPalabra(q);
        model.addAttribute("productos", productos);
        return "redirect:/api/seccion";
    }
        
     @PostMapping("/seccion/scrape")
    public String scrape(@RequestParam String busqueda, Model model) throws IOException {
        List<Element> productos = scraperService.scrape(busqueda);
        model.addAttribute("productos", productos);
        return "redirect:/api/seccion";
}
    
    @GetMapping("/estadistica")
    public Estadistica Estadistica() {
        return (Estadistica) scraperService.obtenerEstadisticas();
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.disgis01.ASalinasNCapas.DAO;

import com.disgis01.ASalinasNCapas.JPA.Extraer;
import java.util.List;
import java.util.stream.Stream;
import org.jsoup.select.Elements;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Alien 1
 */
public interface ExtraerRepository extends JpaRepository<Extraer, Long> {

    public List<Extraer> findByTituloContainingIgnoreCase(String q);

    boolean existsByUrl(String url);

//    public List<Extraer> saveAll(Elements datos);
    
}

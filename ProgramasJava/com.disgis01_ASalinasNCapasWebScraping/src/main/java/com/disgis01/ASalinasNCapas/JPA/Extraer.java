/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.disgis01.ASalinasNCapas.JPA;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 *
 * @author Alien 1
 */
@Entity
public class Extraer {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "titulo")
    private String titulo;
    
    @Column(name = "descripcion")
    private String descripcion;
    
    @Column(name = "precio")
    private String precio;
    
    @Column(name = "url")
    private String url;
    
    @Column(name = "imagen")
    private String imagen;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
    
    
     public Extraer() {
    }
    
    public Extraer(Long id) {
        this.id =id;
    }
    
    public Extraer(Long id,String titulo) {
        this.id =id;
        this.titulo = titulo;
    }
    
    public Extraer(Long id,String titulo, String url) {
        this.id =id;
        this.titulo = titulo;
        this.url = url;
    }
    
//    public Extraer(Long id,String titulo, String descripcion, String precio) {
//        this.id =id;
//        this.titulo = titulo;
//        this.descripcion = descripcion;
//        this.precio = precio;
//    }
    
    public Extraer(Long id,String titulo, String precio, String url) {
        this.id =id;
        this.titulo = titulo;
        this.precio = precio;
        this.url = url;
    }
    public Extraer(Long id,String titulo, String descripcion, String precio, String url) {
        this.id =id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.precio = precio;
        this.url = url;
    }
    public Extraer(Long id,String titulo, String descripcion, String precio, String url, String imagen) {
        this.id =id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.precio = precio;
        this.url = url;
        this.imagen = imagen;
    }
}

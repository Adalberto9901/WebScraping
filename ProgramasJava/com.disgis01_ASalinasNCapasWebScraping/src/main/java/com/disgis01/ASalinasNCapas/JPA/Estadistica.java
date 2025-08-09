/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.disgis01.ASalinasNCapas.JPA;

/**
 *
 * @author Alien 1
 */
public class Estadistica {

    private long total;
    private double promedioLongitud;
    private int maxLongitud;
    private int minLongitud;

    public Estadistica() {
    }
    
    public Estadistica(long total, double promedioLongitud, int maxLongitud, int minLongitud) {
        this.total = total;
        this.promedioLongitud = promedioLongitud;
        this.maxLongitud = maxLongitud;
        this.minLongitud = minLongitud;
    }
    
    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public double getPromedioLongitud() {
        return promedioLongitud;
    }

    public void setPromedioLongitud(double promedioLongitud) {
        this.promedioLongitud = promedioLongitud;
    }

    public int getMaxLongitud() {
        return maxLongitud;
    }

    public void setMaxLongitud(int maxLongitud) {
        this.maxLongitud = maxLongitud;
    }

    public int getMinLongitud() {
        return minLongitud;
    }

    public void setMinLongitud(int minLongitud) {
        this.minLongitud = minLongitud;
    }
    
    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.disgis01.ASalinasNCapas.ML;

import java.util.List;

/**
 *
 * @author Alien 1
 */
public class Result<T> {

    public boolean correct;
    public String errorMasassge;
    public Exception ex;
    public T object;
    public List<T> objects;
    
    public boolean success;
    public String request_token;
    public int page;
    public T result;
    public List<T> results;
    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.disgis01.ASalinasNCapas.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Alien 1
 */
@RestController
@RequestMapping("/demoapi")
public class DemoRestController {

    //  hola mundo
    @GetMapping("/saludar")
    public String HolaMundo() {
        return "Hola Mundo";
    }

    //  suma de a y b por url
    @GetMapping("/suma/{num1}/{num2}")
    public int Sumar(@PathVariable int num1, @PathVariable int num2) {
        return num1 + num2;
    }

    // suma de n numeros que lleguen por el cuerpo de la peticion
    @PostMapping("/sumaLista")
    public ResponseEntity<String> SumaNumeros(@RequestBody List<Integer> numeros) {

        int suma = 0;
        for (Integer numero : numeros) {
            suma += numero;
        }
        return ResponseEntity.ok("Se recibieron " + numeros.size() + " números y la suma es: " + suma + ".");
    }

    //  actualizacion de dato n en una lista de elementos recuperados por el cuerpo
    @PatchMapping(path = "/actualizacion/{num1}")
    public ResponseEntity<?> Actualizacion(@PathVariable int num1) {
        
//            int[] lista = {17, 2, 3, 56, 7, 91, 101, 5};
            List<Integer> lista = new ArrayList<>(Arrays.asList(17, 2, 3, 56, 7, 91, 101, 5));
            if (num1 > 0 || num1 < lista.size()) {
                lista.set(num1, 0);
                
                return ResponseEntity.ok(lista);
            }
            return ResponseEntity.badRequest().body("Posicion no valida");
    }
}

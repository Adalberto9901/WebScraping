/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.disgis01.ASalinasNCapas.Controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 *
 * @author Alien 1
 */
//@RestControllerAdvice
//@Controller
@RequestMapping("/demo")
@ResponseBody
public class DemoController {

    @GetMapping("saludo/{persona}")
    //PathVariable
        public String Hello(Model model, @RequestParam(name = "persona", defaultValue  = "todas", required = false) String persona){
        model.addAttribute("persona", persona);
    return "HolaMundo" ;
}


}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.disgis01.ASalinasNCapas.Controller;

import com.disgis01.ASalinasNCapas.DAO.IUsuarioJPADAORepository;
import com.disgis01.ASalinasNCapas.DAO.UsuarioJPADAOImplementation;
import com.disgis01.ASalinasNCapas.ML.Result;
import com.disgis01.ASalinasNCapas.ML.Usuario;
import com.disgis01.ASalinasNCapas.ML.UsuarioDireccion;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 *
 * @author Alien 1
 */
//@RestControllerAdvice
@Controller
@RequestMapping("/demo")
//@ResponseBody
public class DemoController {

    @Autowired
    private UsuarioJPADAOImplementation usuarioJPADAOImplementation;
    @Autowired
    private IUsuarioJPADAORepository iUsuarioJPADAORepository;

//    @RequestMapping("hello")
//    public String helloWorld(@RequestParam(value = "username", defaultValue = "World") String username) {
//        return "Hello " + username + "!!";
//    }

    @GetMapping("/prueba")
    public String Pruebas() {
        return "Pruebas";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam("username") String username,
            @RequestParam("password") String password,
            HttpServletResponse response,
            Model model) {

        com.disgis01.ASalinasNCapas.JPA.Usuario usuario = iUsuarioJPADAORepository.findAllByUserNombreUsuario(username);

//    if (usuario == null || !passwordEncoder().matches(pwd, usuario.getPasswordUsuario())) {
//        model.addAttribute("error", "Credenciales inválidas");
//        return "usuario/login"; // O redirecciona con error
//    }
        
         List<GrantedAuthority> grantedAuthorities = AuthorityUtils
            .commaSeparatedStringToAuthorityList(usuario.getRoll().getNombreRoll());

    String secretKey = "mySuperSecretKeyThatIsAtLeast32CharsLong"; // Mover a application.properties idealmente

    String createToken = Jwts
            .builder()
            .setId("softtekJWT")
            .setSubject(username)
            .claim("authorities",
                    grantedAuthorities.stream()
                            .map(GrantedAuthority::getAuthority)
                            .collect(Collectors.toList()))
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + 600000))
            .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()), SignatureAlgorithm.HS512)
            .compact();

    Cookie jwtCookie = new Cookie("token", createToken);
    jwtCookie.setHttpOnly(true);
    jwtCookie.setPath("/");
    jwtCookie.setMaxAge(600); // 10 min
    response.addCookie(jwtCookie);

        return "redirect:index";

    }

//    private String getJWTToken(String username) {
//        String secretKey = "mySecretKey";
//        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
//                .commaSeparatedStringToAuthorityList("Programador");
//
//        String token = Jwts
//                .builder()
//                .setId("softtekJWT")
//                .setSubject(username)
//                .claim("authorities",
//                        grantedAuthorities.stream()
//                                .map(GrantedAuthority::getAuthority)
//                                .collect(Collectors.toList()))
//                .setIssuedAt(new Date(System.currentTimeMillis()))
//                .setExpiration(new Date(System.currentTimeMillis() + 600000))
//                .signWith(SignatureAlgorithm.HS512,
//                        secretKey.getBytes()).compact();
//
//        return "Bearer " + token;
//    }
    @GetMapping("index")
    public String Index(Model model) {
        Result result = usuarioJPADAOImplementation.GetAll(); //se atrapa el reusltado el sera un 1 o un 0

        if (result.correct) {
            model.addAttribute("usuariosDireccion", result.objects);
            model.addAttribute("usuarioDireccion", new UsuarioDireccion());
        }
        return "IndexUsuario";
    }
}

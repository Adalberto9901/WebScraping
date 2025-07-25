/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.disgis01.ASalinasNCapas.Controller;

import com.disgis01.ASalinasNCapas.ML.Pelicula;
import com.disgis01.ASalinasNCapas.ML.Result;
import com.disgis01.ASalinasNCapas.ML.ResultValidarDatos;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Alien 1
 */
@Controller
@RequestMapping("/movie")
public class UsuarioController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/requestToken")
    public ResponseEntity<String> CreateRequestToken() {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJiZTcyNTdiZGU1MzdjOWIwMmQyZjFhZTY3NmU5NWU3NSIsIm5iZiI6MTc1MzQ2MjU1OS4xNzIsInN1YiI6IjY4ODNiNzFmOThjNTk3ZjExYThhNjJlYSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.z3tTRDwVy052xB0bwNJLoOEb1FhTQivTPWzaw2zrMkU";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(token, httpHeaders);
        ResponseEntity<Result<T>> response = restTemplate.exchange("https://api.themoviedb.org/3/authentication/token/new",
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<Result<T>>() {
        });
        Result result = response.getBody();
        String url = "http://localhost:8080/movie/login?forlogin=" + result.errorMasassge;

        RestTemplate restTemplateUrl = new RestTemplate();
        ResponseEntity<String> responseUrl = restTemplateUrl.getForEntity(url, String.class);

        return responseUrl;
    }

    @PostMapping("/login")
    public ResponseEntity<String> CreateSessionLogin(@RequestParam String encriptado) {

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(encriptado, httpHeaders);
        ResponseEntity<Result<T>> response = restTemplate.exchange("https://api.themoviedb.org/3/authentication/token/validate_with_login",
                HttpMethod.POST,
                requestEntity,
                new ParameterizedTypeReference<Result<T>>() {
        });
//        List<String> resultado = (List<String>) response.getBody();
        Result result = response.getBody();
        String url = "http://localhost:8080/movie/guestSession?guetsSession=" + result.errorMasassge;

        RestTemplate restTemplateUrl = new RestTemplate();
        ResponseEntity<String> responseUrl = restTemplateUrl.getForEntity(url, String.class);

        return responseUrl;
    }

    @GetMapping("/guestSession")
    public String CreateGuestSession() {

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Result<T>> response = restTemplate.exchange("https://api.themoviedb.org/3/authentication/guest_session/new",
                HttpMethod.GET,
                HttpEntity.EMPTY,
                new ParameterizedTypeReference<Result<T>>() {
        });
        return "redirect:index";
    }

    @GetMapping("index")
    public String Index(Model model) {
Result result = new Result();
        
        try {
            String token = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJiZTcyNTdiZGU1MzdjOWIwMmQyZjFhZTY3NmU5NWU3NSIsIm5iZiI6MTc1MzQ2MjU1OS4xNzIsInN1YiI6IjY4ODNiNzFmOThjNTk3ZjExYThhNjJlYSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.z3tTRDwVy052xB0bwNJLoOEb1FhTQivTPWzaw2zrMkU";
        
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization","Bearer " + token);
        HttpEntity<String> requestEntity = new HttpEntity<>( httpHeaders);
        ResponseEntity<Result<Pelicula>> response = restTemplate.exchange("https://api.themoviedb.org/3/movie/popular",
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<Result<Pelicula>>() {
        });
        List<Pelicula> Pelicula = response.getBody().results;
        
        model.addAttribute("peliculas", Pelicula);
        return "index";
        } catch (Exception ex) {
            result.errorMasassge= ex.getLocalizedMessage();
            result.ex = ex;
        }
        return "index";
    }

    @PostMapping("index")
    public String Index(Model model, @ModelAttribute RestTemplate usuarioBusqueda) {

//        RestTemplate restTemplate = new RestTemplate();
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
//        HttpEntity<UsuarioDireccion> requestEntity = new HttpEntity<>(usuarioBusqueda, httpHeaders);
//        ResponseEntity<Result<UsuarioDireccion>> response = restTemplate.exchange("http://localhost:8080/usuarioapi/busqueda",
//                HttpMethod.POST,
//                requestEntity,
//                new ParameterizedTypeReference<Result<UsuarioDireccion>>() {
//        });
//        List<UsuarioDireccion> usuariosDireccion = response.getBody().objects;
////        Result result = responseBusqueda.getBody();
//        model.addAttribute("usuariosDireccion", usuariosDireccion); 
        return "Index";
    }

    @GetMapping("popularMovie")
    public String PopularMovie(Model model) {
        Result result = new Result();
        
        try {
            String token = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJiZTcyNTdiZGU1MzdjOWIwMmQyZjFhZTY3NmU5NWU3NSIsIm5iZiI6MTc1MzQ2MjU1OS4xNzIsInN1YiI6IjY4ODNiNzFmOThjNTk3ZjExYThhNjJlYSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.z3tTRDwVy052xB0bwNJLoOEb1FhTQivTPWzaw2zrMkU";
        
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization","Bearer " + token);
        HttpEntity<String> requestEntity = new HttpEntity<>( httpHeaders);
        ResponseEntity<Result<Pelicula>> response = restTemplate.exchange("https://api.themoviedb.org/3/movie/popular",
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<Result<Pelicula>>() {
        });
        List<Pelicula> Pelicula = response.getBody().results;
        
        model.addAttribute("peliculas", Pelicula);
        return "PopularMovie";
        } catch (Exception ex) {
            result.errorMasassge= ex.getLocalizedMessage();
            result.ex = ex;
        }
        return "index";
    }
}

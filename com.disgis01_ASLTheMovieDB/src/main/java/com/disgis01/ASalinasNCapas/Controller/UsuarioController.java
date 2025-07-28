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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import org.springframework.http.HttpStatus;
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

    @GetMapping("/prueba")
    public String prueba(HttpSession session, Model model) {
        Result result = new Result();

        try {
            String token = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJiZTcyNTdiZGU1MzdjOWIwMmQyZjFhZTY3NmU5NWU3NSIsIm5iZiI6MTc1MzQ2MjU1OS4xNzIsInN1YiI6IjY4ODNiNzFmOThjNTk3ZjExYThhNjJlYSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.z3tTRDwVy052xB0bwNJLoOEb1FhTQivTPWzaw2zrMkU";

            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.set("Authorization", "Bearer " + token);
            HttpEntity<String> requestEntity = new HttpEntity<>(httpHeaders);
            ResponseEntity<Result<Pelicula>> response = restTemplate.exchange("https://api.themoviedb.org/3/movie/popular?language=es-MX",
                    HttpMethod.GET,
                    requestEntity,
                    new ParameterizedTypeReference<Result<Pelicula>>() {
            });
            List<Pelicula> Pelicula = response.getBody().results;

            String username = (String) session.getAttribute("username");
            String sessionId = (String) session.getAttribute("sessionId");

            model.addAttribute("peliculas", Pelicula);
            model.addAttribute("username", username);
            model.addAttribute("sessionId", sessionId);

            return "Pruebas";
        } catch (Exception ex) {
            result.errorMasassge = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return "redirect:login";
    }
    
    @PostMapping("/login")
    public String CreateSessionLogin(@RequestParam String username,
            @RequestParam String password,
            Model model,
            HttpSession session) {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJiZTcyNTdiZGU1MzdjOWIwMmQyZjFhZTY3NmU5NWU3NSIsIm5iZiI6MTc1MzQ2MjU1OS4xNzIsInN1YiI6IjY4ODNiNzFmOThjNTk3ZjExYThhNjJlYSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.z3tTRDwVy052xB0bwNJLoOEb1FhTQivTPWzaw2zrMkU";

        try {
            String apiKey = "be7257bde537c9b02d2f1ae676e95e75";
            String tokenUrl = "https://api.themoviedb.org/3/authentication/token/new?api_key=" + apiKey;

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<Map> tokenResponse = restTemplate.getForEntity(tokenUrl, Map.class);
            String requestToken = (String) tokenResponse.getBody().get("request_token");

            String validateUrl = "https://api.themoviedb.org/3/authentication/token/validate_with_login?api_key=" + apiKey;
            Map<String, String> credentials = Map.of(
                    "username", username,
                    "password", password,
                    "request_token", requestToken
            );

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Map<String, String>> entity = new HttpEntity<>(credentials, headers);

            ResponseEntity<Map> validateResponse = restTemplate.postForEntity(validateUrl, entity, Map.class);

            String sessionUrl = "https://api.themoviedb.org/3/authentication/session/new?api_key=" + apiKey;
            Map<String, String> tokenMap = Map.of("request_token", requestToken);
            HttpEntity<Map<String, String>> sessionEntity = new HttpEntity<>(tokenMap, headers);

            ResponseEntity<Map> sessionResponse = restTemplate.postForEntity(sessionUrl, sessionEntity, Map.class);
            String sessionId = (String) sessionResponse.getBody().get("session_id");

            model.addAttribute("guestSessionId", sessionId);

            session.setAttribute("username", username);
            session.setAttribute("sessionId", sessionId);
            return "redirect:index";

        } catch (Exception ex) {
            model.addAttribute("error", "Error al iniciar sesión: " + ex.getMessage());
            return "login";
        }

    }

    @GetMapping("/guestSession")
    public String CreateGuestSession(Model model) {
        String url = "https://api.themoviedb.org/3/authentication/guest_session/new?api_key=be7257bde537c9b02d2f1ae676e95e75";

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            String guestSessionId = (String) response.getBody().get("guest_session_id");
            model.addAttribute("guestSessionId", guestSessionId);
        }

        return "redirect:index";
    }

    @GetMapping("index")
    public String Index(HttpSession session, Model model) {
        Result result = new Result();

        try {
            String token = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJiZTcyNTdiZGU1MzdjOWIwMmQyZjFhZTY3NmU5NWU3NSIsIm5iZiI6MTc1MzQ2MjU1OS4xNzIsInN1YiI6IjY4ODNiNzFmOThjNTk3ZjExYThhNjJlYSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.z3tTRDwVy052xB0bwNJLoOEb1FhTQivTPWzaw2zrMkU";

            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.set("Authorization", "Bearer " + token);
            HttpEntity<String> requestEntity = new HttpEntity<>(httpHeaders);
            ResponseEntity<Result<Pelicula>> response = restTemplate.exchange("https://api.themoviedb.org/3/movie/popular?language=es-MX",
                    HttpMethod.GET,
                    requestEntity,
                    new ParameterizedTypeReference<Result<Pelicula>>() {
            });
            List<Pelicula> Pelicula = response.getBody().results;

            String username = (String) session.getAttribute("username");
            String sessionId = (String) session.getAttribute("sessionId");

            model.addAttribute("peliculas", Pelicula);
            model.addAttribute("username", username);
            model.addAttribute("sessionId", sessionId);

            return "index";
        } catch (Exception ex) {
            result.errorMasassge = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return "index";
    }

    @GetMapping("popularMovie")
    public String PopularMovie(HttpSession session, Model model) {
        Result result = new Result();

        try {
            String token = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJiZTcyNTdiZGU1MzdjOWIwMmQyZjFhZTY3NmU5NWU3NSIsIm5iZiI6MTc1MzQ2MjU1OS4xNzIsInN1YiI6IjY4ODNiNzFmOThjNTk3ZjExYThhNjJlYSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.z3tTRDwVy052xB0bwNJLoOEb1FhTQivTPWzaw2zrMkU";

            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.set("Authorization", "Bearer " + token);
            HttpEntity<String> requestEntity = new HttpEntity<>(httpHeaders);
            ResponseEntity<Result<Pelicula>> response = restTemplate.exchange("https://api.themoviedb.org/3/movie/popular?language=es-MX",
                    HttpMethod.GET,
                    requestEntity,
                    new ParameterizedTypeReference<Result<Pelicula>>() {
            });
            List<Pelicula> Pelicula = response.getBody().results;

            String username = (String) session.getAttribute("username");
            String sessionId = (String) session.getAttribute("sessionId");

            model.addAttribute("peliculas", Pelicula);
            model.addAttribute("username", username);
            model.addAttribute("sessionId", sessionId);

            return "PopularMovie";
        } catch (Exception ex) {
            result.errorMasassge = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return "redirect:index";
    }

    @GetMapping("peliculaFavorita")
    public String PeliculaFavorita(HttpSession session, Model model) {
        Result result = new Result();

        try {
            String apiKey = "be7257bde537c9b02d2f1ae676e95e75";
            String sessionId = (String) session.getAttribute("sessionId");

            if (sessionId == null) {
                model.addAttribute("error", "Debes iniciar sesión para ver tus películas favoritas.");
                return "redirect:login";
            }

            String accountUrl = "https://api.themoviedb.org/3/account?api_key=" + apiKey + "&session_id=" + sessionId;
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<Map> accountResponse = restTemplate.getForEntity(accountUrl, Map.class);
            Integer accountId = (Integer) accountResponse.getBody().get("id");

            String favoriteUrl = String.format(
                    "https://api.themoviedb.org/3/account/%d/favorite/movies?api_key=%s&session_id=%s&language=es-MX",
                    accountId, apiKey, sessionId
            );

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + apiKey); 
            HttpEntity<String> requestEntity = new HttpEntity<>(headers);

            ResponseEntity<Result<Pelicula>> response = restTemplate.exchange(
                    favoriteUrl,
                    HttpMethod.GET,
                    requestEntity,
                    new ParameterizedTypeReference<Result<Pelicula>>() {
            }
            );
            List<Pelicula> Pelicula = response.getBody().results;

            String username = (String) session.getAttribute("username");
            sessionId = (String) session.getAttribute("sessionId");

            model.addAttribute("peliculas", Pelicula);
            model.addAttribute("username", username);
            model.addAttribute("sessionId", sessionId);

            return "PeliculaFavorita";
        } catch (Exception ex) {
            result.errorMasassge = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return "redirect:index";
    }

    @PostMapping("/favorito")
    @ResponseBody
    public ResponseEntity<String> marcarFavorito(@RequestParam("mediaId") String mediaId, 
            @RequestParam("favorite") String favorite, 
            HttpSession session) {
//        , @RequestParam("favorite") boolean favorite
        try {
//            int mediaId2 = Integer.parseInt(mediaId);
            boolean favoriteBl;
            
            if (favorite.equals("true")) {
                favoriteBl = true;
            } else {
                favoriteBl = false;
            }
            String apiKey = "be7257bde537c9b02d2f1ae676e95e75";
            String sessionId = (String) session.getAttribute("sessionId");

            if (sessionId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No hay sesión iniciada");
            }

            String accountUrl = "https://api.themoviedb.org/3/account?api_key=" + apiKey + "&session_id=" + sessionId;
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<Map> accountResponse = restTemplate.getForEntity(accountUrl, Map.class);
            Integer accountId = (Integer) accountResponse.getBody().get("id");

            String url = String.format("https://api.themoviedb.org/3/account/%d/favorite?api_key=%s&session_id=%s",
                    accountId, apiKey, sessionId);

            Map<String, Object> payload = new HashMap<>();
            payload.put("media_type", "movie");
            payload.put("media_id", mediaId);
            payload.put("favorite", favoriteBl);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(payload, headers);

            ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);
            return ResponseEntity.ok("Película agregada a favoritos");

        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al agregar favorito: " + ex.getMessage());
        }
    }

    @GetMapping("/logout")
    public String cerrarSesion(HttpSession session) {
        session.invalidate();  
        return "redirect:/movie/index";
    }
}

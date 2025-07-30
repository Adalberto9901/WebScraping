/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.disgis01.ASalinasNCapas.Controller;

import com.disgis01.ASalinasNCapas.ML.Pelicula;
import com.disgis01.ASalinasNCapas.ML.Result;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author Alien 1
 */
//@RestControllerAdvice
@Controller
@RequestMapping("/demo")
//@ResponseBody
public class DemoController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/prueba")
    public String prueba(@RequestParam(name = "lang", defaultValue = "es-MX") String lang,
            HttpSession session, Model model) {
        Result result = new Result();

        try {
            String token = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJiZTcyNTdiZGU1MzdjOWIwMmQyZjFhZTY3NmU5NWU3NSIsIm5iZiI6MTc1MzQ2MjU1OS4xNzIsInN1YiI6IjY4ODNiNzFmOThjNTk3ZjExYThhNjJlYSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.z3tTRDwVy052xB0bwNJLoOEb1FhTQivTPWzaw2zrMkU";
            String username = (String) session.getAttribute("username");
            String sessionId = (String) session.getAttribute("sessionId");

            // 1. Obtener películas populares
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + token);
            HttpEntity<String> request = new HttpEntity<>(headers);

            ResponseEntity<Result<Pelicula>> response = restTemplate.exchange(
                    "https://api.themoviedb.org/3/movie/popular?language=" + lang,
                    HttpMethod.GET, request,
                    new ParameterizedTypeReference<Result<Pelicula>>() {
            }
            );
            List<Pelicula> peliculas = response.getBody().results;
            
            String accountUrl = "https://api.themoviedb.org/3/account?session_id=" + sessionId;
                ResponseEntity<Map> accountResponse = restTemplate.exchange(
                    accountUrl, HttpMethod.GET, request, Map.class
                );
            Integer accountId = (Integer) accountResponse.getBody().get("id");
            // 2. Obtener favoritas del usuario (según si está logueado o es invitado)
            List<Integer> peliculasFavoritas = new ArrayList<>();
            if (sessionId != null) {
                String favUrl = "https://api.themoviedb.org/3/account/" + accountId + "/favorite/movies?session_id=" + sessionId;
                ResponseEntity<Result<Pelicula>> favResponse = restTemplate.exchange(
                    favUrl, HttpMethod.GET, request,
                    new ParameterizedTypeReference<Result<Pelicula>>() {}
                );

                if (favResponse.getBody() != null && favResponse.getBody().results != null) {
                    peliculasFavoritas = (List<Integer>) favResponse.getBody().results
                            .stream()
                            .map(Pelicula::getId)
                            .collect(Collectors.toList());
                }
            }

            model.addAttribute("peliculas", peliculas);
            model.addAttribute("peliculasFavoritas", peliculasFavoritas);
            model.addAttribute("username", username);
            model.addAttribute("sessionId", sessionId);
            model.addAttribute("selectedLang", lang);

            return "Pruebas";
        } catch (Exception ex) {
            result.errorMasassge = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return "redirect:index";
    }

    @PostMapping("/login")
    public String CreateSessionLogin(@RequestParam String username,
            @RequestParam String password,
            Model model,
            HttpSession session) {

        try {
            String bearerToken = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJiZTcyNTdiZGU1MzdjOWIwMmQyZjFhZTY3NmU5NWU3NSIsIm5iZiI6MTc1MzQ2MjU1OS4xNzIsInN1YiI6IjY4ODNiNzFmOThjNTk3ZjExYThhNjJlYSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.z3tTRDwVy052xB0bwNJLoOEb1FhTQivTPWzaw2zrMkU";

            RestTemplate restTemplate = new RestTemplate();

            // 1. Obtener request_token
            String tokenUrl = "https://api.themoviedb.org/3/authentication/token/new";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(bearerToken);

            HttpEntity<Void> tokenEntity = new HttpEntity<>(headers);
            ResponseEntity<Map> tokenResponse = restTemplate.exchange(tokenUrl, HttpMethod.GET, tokenEntity, Map.class);
            String requestToken = (String) tokenResponse.getBody().get("request_token");

            // 2. Validar token con login
            String validateUrl = "https://api.themoviedb.org/3/authentication/token/validate_with_login";
            Map<String, String> credentials = Map.of(
                    "username", username,
                    "password", password,
                    "request_token", requestToken
            );
            HttpEntity<Map<String, String>> validateEntity = new HttpEntity<>(credentials, headers);
            restTemplate.postForEntity(validateUrl, validateEntity, Map.class);

            // 3. Crear sesión
            String sessionUrl = "https://api.themoviedb.org/3/authentication/session/new";
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
        String bearerToken = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJiZTcyNTdiZGU1MzdjOWIwMmQyZjFhZTY3NmU5NWU3NSIsIm5iZiI6MTc1MzQ2MjU1OS4xNzIsInN1YiI6IjY4ODNiNzFmOThjNTk3ZjExYThhNjJlYSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.z3tTRDwVy052xB0bwNJLoOEb1FhTQivTPWzaw2zrMkU";

        String url = "https://api.themoviedb.org/3/authentication/guest_session/new";

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(bearerToken); // Aquí va el token en el encabezado
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, entity, Map.class);

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
            ResponseEntity<Result<Pelicula>> response = restTemplate.exchange("https://api.themoviedb.org/3/movie/now_playing?language=es-MX",
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
            String bearerToken = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJiZTcyNTdiZGU1MzdjOWIwMmQyZjFhZTY3NmU5NWU3NSIsIm5iZiI6MTc1MzQ2MjU1OS4xNzIsInN1YiI6IjY4ODNiNzFmOThjNTk3ZjExYThhNjJlYSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.z3tTRDwVy052xB0bwNJLoOEb1FhTQivTPWzaw2zrMkU";
            String sessionId = (String) session.getAttribute("sessionId");

            if (sessionId == null) {
                model.addAttribute("error", "Debes iniciar sesión para ver tus películas favoritas.");
                return "redirect:login";
            }

            // Configurar headers con el token Bearer
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(bearerToken);
            headers.setContentType(MediaType.APPLICATION_JSON);

            RestTemplate restTemplate = new RestTemplate();

            // 1. Obtener información de la cuenta
            String accountUrl = "https://api.themoviedb.org/3/account?session_id=" + sessionId;

            HttpEntity<Void> accountEntity = new HttpEntity<>(headers);
            ResponseEntity<Map> accountResponse = restTemplate.exchange(accountUrl, HttpMethod.GET, accountEntity, Map.class);
            Integer accountId = (Integer) accountResponse.getBody().get("id");

            // 2. Obtener las películas favoritas
            String favoriteUrl = String.format(
                    "https://api.themoviedb.org/3/account/%d/favorite/movies?session_id=%s&language=es-MX",
                    accountId, sessionId
            );

            HttpEntity<Void> favoriteEntity = new HttpEntity<>(headers);
            ResponseEntity<Result<Pelicula>> response = restTemplate.exchange(
                    favoriteUrl,
                    HttpMethod.GET,
                    favoriteEntity,
                    new ParameterizedTypeReference<Result<Pelicula>>() {
            }
            );

            List<Pelicula> peliculas = response.getBody().results;

            String username = (String) session.getAttribute("username");

            model.addAttribute("peliculas", peliculas);
            model.addAttribute("username", username);
            model.addAttribute("sessionId", sessionId);

            return "PeliculaFavorita";
        } catch (Exception ex) {
            result.errorMasassge = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return "redirect:index";
    }

    @GetMapping("detalle")
    public String Detalle(HttpSession session,
            @RequestParam("movieId") String movieId,
            Model model) {
        Result result = new Result();
        try {
            String bearerToken = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJiZTcyNTdiZGU1MzdjOWIwMmQyZjFhZTY3NmU5NWU3NSIsIm5iZiI6MTc1MzQ2MjU1OS4xNzIsInN1YiI6IjY4ODNiNzFmOThjNTk3ZjExYThhNjJlYSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.z3tTRDwVy052xB0bwNJLoOEb1FhTQivTPWzaw2zrMkU";
            String sessionId = (String) session.getAttribute("sessionId");

            RestTemplate restTemplate = new RestTemplate();

            String url = "https://api.themoviedb.org/3/movie/" + movieId + "?language=es-MX";

            // Configurar headers con el token Bearer
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(bearerToken);
            headers.setContentType(MediaType.APPLICATION_JSON);

            // 1. Obtener información de la cuenta
            String accountUrl = "https://api.themoviedb.org/3/account?session_id=" + sessionId;

            HttpEntity<Void> accountEntity = new HttpEntity<>(headers);
            ResponseEntity<Map> accountResponse = restTemplate.exchange(accountUrl, HttpMethod.GET, accountEntity, Map.class);
            Integer accountId = (Integer) accountResponse.getBody().get("id");

            HttpEntity<Void> entity = new HttpEntity<>(headers);

            ResponseEntity<String> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    String.class
            );

            ObjectMapper mapper = new ObjectMapper();
            Pelicula pelicula = mapper.readValue(response.getBody(), Pelicula.class);

            String username = (String) session.getAttribute("username");

            model.addAttribute("pelicula", pelicula);
            model.addAttribute("username", username);
            model.addAttribute("sessionId", sessionId);

            return "Details";
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

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.disgis01.ASalinasNCapas.Controller;

import com.disgis01.ASalinasNCapas.ML.Colonia;
import com.disgis01.ASalinasNCapas.ML.Direccion;
import com.disgis01.ASalinasNCapas.ML.Estado;
import com.disgis01.ASalinasNCapas.ML.Municipio;
import com.disgis01.ASalinasNCapas.ML.Pais;
import com.disgis01.ASalinasNCapas.ML.Result;
import com.disgis01.ASalinasNCapas.ML.ResultValidarDatos;
import com.disgis01.ASalinasNCapas.ML.Roll;
import com.disgis01.ASalinasNCapas.ML.Usuario;
import com.disgis01.ASalinasNCapas.ML.UsuarioDireccion;
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
@RequestMapping("/usuario")
public class UsuarioController {

    @GetMapping("index")
    public String Index(Model model) {
        RestTemplate restTemplateRoll = new RestTemplate();
        ResponseEntity<Result<Roll>> responseRolles = restTemplateRoll.exchange("http://localhost:8080/usuarioapi/roll",
                HttpMethod.GET,
                HttpEntity.EMPTY,
                new ParameterizedTypeReference<Result<Roll>>() {
        });
        model.addAttribute("rolles", responseRolles.getBody().objects);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Result<UsuarioDireccion>> response = restTemplate.exchange("http://localhost:8080/usuarioapi",
                HttpMethod.GET,
                HttpEntity.EMPTY,
                new ParameterizedTypeReference<Result<UsuarioDireccion>>() {
        });
        List<UsuarioDireccion> usuariosDireccion = response.getBody().objects;

        model.addAttribute("usuariosDireccion", usuariosDireccion);
        model.addAttribute("usuarioDireccion", new UsuarioDireccion());
        return "IndexUsuario";
    }

    @PostMapping("index")
    public String Index(Model model, @ModelAttribute UsuarioDireccion usuarioBusqueda) {

        RestTemplate restTemplateRoll = new RestTemplate();
        ResponseEntity<Result<Roll>> responseRolles = restTemplateRoll.exchange("http://localhost:8080/usuarioapi/roll",
                HttpMethod.GET,
                HttpEntity.EMPTY,
                new ParameterizedTypeReference<Result<Roll>>() {
        });
        model.addAttribute("rolles", responseRolles.getBody().objects);

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<UsuarioDireccion> requestEntity = new HttpEntity<>(usuarioBusqueda, httpHeaders);
        ResponseEntity<Result<UsuarioDireccion>> response = restTemplate.exchange("http://localhost:8080/usuarioapi/busqueda",
                HttpMethod.POST,
                requestEntity,
                new ParameterizedTypeReference<Result<UsuarioDireccion>>() {
        });
        List<UsuarioDireccion> usuariosDireccion = response.getBody().objects;
//        Result result = responseBusqueda.getBody();
        model.addAttribute("usuariosDireccion", usuariosDireccion); //se manda la informacion a la vista a traves de una variable
        model.addAttribute("usuarioDireccion", new UsuarioDireccion());
        return "IndexUsuario";
    }

    @GetMapping("addUser/{idUsuario}")// prepara la vista del formulario
    public String Formulario(Model model, @PathVariable int idUsuario, @ModelAttribute UsuarioDireccion usuarioDireccion) {

        if (idUsuario < 1) {
            RestTemplate restTemplatePais = new RestTemplate();
            ResponseEntity<Result<Pais>> responsePais = restTemplatePais.exchange("http://localhost:8080/usuarioapi/pais",
                    HttpMethod.GET,
                    HttpEntity.EMPTY,
                    new ParameterizedTypeReference<Result<Pais>>() {
            });

            model.addAttribute("paises", responsePais.getBody().objects);

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<Result<Roll>> response = restTemplate.exchange("http://localhost:8080/usuarioapi/roll",
                    HttpMethod.GET,
                    HttpEntity.EMPTY,
                    new ParameterizedTypeReference<Result<Roll>>() {
            });

            model.addAttribute("rolles", response.getBody().objects);
            model.addAttribute("usuarioDireccion", new UsuarioDireccion());
            return "FormUsuario";

        } else {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<Result<UsuarioDireccion>> response = restTemplate.exchange("http://localhost:8080/usuarioapi/" + idUsuario,
                    HttpMethod.GET,
                    HttpEntity.EMPTY,
                    new ParameterizedTypeReference<Result<UsuarioDireccion>>() {
            });
            UsuarioDireccion usuariosDireccion = response.getBody().object;

            model.addAttribute("usuarioDireccion", usuariosDireccion);
            return "UsuarioDetail";
        }

    }

    @PostMapping("addUser")// recuperar los datos del formulario
    public String Formulario(@Valid @ModelAttribute UsuarioDireccion usuarioDireccion,
            BindingResult bindingResult, @RequestParam MultipartFile Imagen,
            Model model) {

        if (bindingResult.hasErrors()) {
            RestTemplate restTemplatePais = new RestTemplate();
            ResponseEntity<Result<Pais>> responsePais = restTemplatePais.exchange("http://localhost:8080/usuarioapi/pais",
                    HttpMethod.GET,
                    HttpEntity.EMPTY,
                    new ParameterizedTypeReference<Result<Pais>>() {
            });

            model.addAttribute("paises", responsePais.getBody().objects);
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<Result<Roll>> response = restTemplate.exchange("http://localhost:8080/usuarioapi/roll",
                    HttpMethod.GET,
                    HttpEntity.EMPTY,
                    new ParameterizedTypeReference<Result<Roll>>() {
            });

            model.addAttribute("rolles", response.getBody().objects);
            model.addAttribute("usuarioDireccion", usuarioDireccion);
            return "FormUsuario";
        }
        try {
            if (!Imagen.isEmpty()) {
                byte[] bytes = Imagen.getBytes();
                String imagenBase64 = Base64.getEncoder().encodeToString(bytes);
                usuarioDireccion.usuario.setImagen(imagenBase64);
            }
        } catch (Exception ex) {
            System.out.println(ex.getLocalizedMessage());
        }
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<UsuarioDireccion> requestEntity = new HttpEntity<>(usuarioDireccion, httpHeaders);
        ResponseEntity<Result<UsuarioDireccion>> response = restTemplate.exchange("http://localhost:8080/usuarioapi",
                HttpMethod.POST,
                requestEntity,
                new ParameterizedTypeReference<Result<UsuarioDireccion>>() {
        });
        Result result = response.getBody();
        if (result.correct) {
            return "redirect:index";//rediccionar a la vista getAll
        }
        return "redirect:index";//rediccionar de nuevo al formulario
    }

    @PostMapping("updateUser")// recuperar los datos del formulario
    public String UsuarioActualizar(@Valid @ModelAttribute UsuarioDireccion usuarioDireccion,
            BindingResult bindingResult, @RequestParam MultipartFile Imagen,
            Model model) {

        if (bindingResult.hasErrors()) {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<Result<Roll>> response = restTemplate.exchange("http://localhost:8080/usuarioapi/roll",
                    HttpMethod.GET,
                    HttpEntity.EMPTY,
                    new ParameterizedTypeReference<Result<Roll>>() {
            });

            model.addAttribute("rolles", response.getBody().objects);
            model.addAttribute("usuarioDireccion", usuarioDireccion);
            return "FormUsuario";
        }
        try {
            if (!Imagen.isEmpty()) {
                byte[] bytes = Imagen.getBytes();
                String imagenBase64 = Base64.getEncoder().encodeToString(bytes);
                usuarioDireccion.usuario.setImagen(imagenBase64);
            }
        } catch (Exception ex) {
            System.out.println(ex.getLocalizedMessage());
        }
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<UsuarioDireccion> requestEntity = new HttpEntity<>(usuarioDireccion, httpHeaders);
        ResponseEntity<Result<UsuarioDireccion>> response = restTemplate.exchange("http://localhost:8080/usuarioapi/" + usuarioDireccion.usuario.getIdUsuario(),
                HttpMethod.PUT,
                requestEntity,
                new ParameterizedTypeReference<Result<UsuarioDireccion>>() {
        });
        Result result = response.getBody();
        if (result.correct) {
            return "redirect:addUser/" + usuarioDireccion.usuario.getIdUsuario();
        }
        return "redirect:index";//rediccionar de nuevo al formulario
    }

    @GetMapping("deleteUser/{idUsuario}")// prepara la vista del formulario
    public String DeleteUser(Model model, @PathVariable int idUsuario) {

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Integer> requestEntity = new HttpEntity<>(idUsuario, httpHeaders);
        ResponseEntity<Result<UsuarioDireccion>> response = restTemplate.exchange("http://localhost:8080/usuarioapi/" + idUsuario,
                HttpMethod.DELETE,
                requestEntity,
                new ParameterizedTypeReference<Result<UsuarioDireccion>>() {
        });
        Result result = response.getBody();
        if (result.correct) {
            return "redirect:/usuario/index";
        }
        return "redirect:/usuario/index";
    }

    @PostMapping("addAddress")// recuperar los datos del formulario
    public String DireccionFormulario(@Valid @ModelAttribute UsuarioDireccion usuarioDireccion,
            BindingResult bindingResult,
            Model model) {

        if (bindingResult.hasErrors()) {
            RestTemplate restTemplatePais = new RestTemplate();
            ResponseEntity<Result<Pais>> responsePais = restTemplatePais.exchange("http://localhost:8080/usuarioapi/pais",
                    HttpMethod.GET,
                    HttpEntity.EMPTY,
                    new ParameterizedTypeReference<Result<Pais>>() {
            });
            model.addAttribute("paises", responsePais.getBody().objects);

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<Result<Roll>> response = restTemplate.exchange("http://localhost:8080/usuarioapi/roll",
                    HttpMethod.GET,
                    HttpEntity.EMPTY,
                    new ParameterizedTypeReference<Result<Roll>>() {
            });
            model.addAttribute("rolles", response.getBody().objects);

            model.addAttribute("usuarioDireccion", usuarioDireccion);
            return "FormUsuario";
        }
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<UsuarioDireccion> requestEntity = new HttpEntity<>(usuarioDireccion, httpHeaders);
        ResponseEntity<Result<UsuarioDireccion>> response = restTemplate.exchange("http://localhost:8080/usuarioapi/direccion/" + usuarioDireccion.usuario.getIdUsuario(),
                HttpMethod.POST,
                requestEntity,
                new ParameterizedTypeReference<Result<UsuarioDireccion>>() {
        });
        Result result = response.getBody();
        if (result.correct) {
            return "redirect:addUser/" + usuarioDireccion.usuario.getIdUsuario();//rediccionar a la vista getAll
        }
        return "redirect:index";//rediccionar de nuevo al formulario
    }

    @PostMapping("updateAddress")// recuperar los datos del formulario
    public String DireccionUpdate(@Valid @ModelAttribute UsuarioDireccion usuarioDireccion,
            BindingResult bindingResult,
            Model model) {

        if (bindingResult.hasErrors()) {
            RestTemplate restTemplatePais = new RestTemplate();
            ResponseEntity<Result<Pais>> responsePais = restTemplatePais.exchange("http://localhost:8080/usuarioapi/pais",
                    HttpMethod.GET,
                    HttpEntity.EMPTY,
                    new ParameterizedTypeReference<Result<Pais>>() {
            });
            model.addAttribute("paises", responsePais.getBody().objects);

////            model.addAttribute("estados", estadoDAOImplementation.GetByIdEstados(usuarioDireccion.Direccion.Colonia.Municipio.Estado.Pais.getIdPais()).objects);
//            model.addAttribute("estados", estadoJPADAOImplementation.GetByIdEstados(usuarioDireccion.Direccion.Colonia.Municipio.Estado.Pais.getIdPais()).objects);
////            model.addAttribute("municipios", municipioDAOImplementation.GetByIdMunicipios(usuarioDireccion.Direccion.Colonia.Municipio.Estado.getIdEstado()).objects);
//            model.addAttribute("municipios", municipioJPADAOImplementation.GetByIdMunicipios(usuarioDireccion.Direccion.Colonia.Municipio.Estado.getIdEstado()).objects);
////            model.addAttribute("colonias", coloniaDAOImplementation.GetByIdColonias(usuarioDireccion.Direccion.Colonia.Municipio.getIdMunicipio()).objects);
//            model.addAttribute("colonias", coloniaJPADAOImplementation.GetByIdColonias(usuarioDireccion.Direccion.Colonia.Municipio.getIdMunicipio()).objects);
            model.addAttribute("usuarioDireccion", usuarioDireccion);
            return "FormUsuario";
        }
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<UsuarioDireccion> requestEntity = new HttpEntity<>(usuarioDireccion, httpHeaders);
        ResponseEntity<Result<UsuarioDireccion>> response = restTemplate.exchange("http://localhost:8080/usuarioapi/direccion/" + usuarioDireccion.Direccion.getIdDireccion(),
                HttpMethod.PUT,
                requestEntity,
                new ParameterizedTypeReference<Result<UsuarioDireccion>>() {
        });
        Result result = response.getBody();
        if (result.correct) {
            return "redirect:addUser/" + usuarioDireccion.usuario.getIdUsuario();//rediccionar a la vista getAll
        }
        return "redirect:index";//rediccionar de nuevo al formulario
    }

    @GetMapping("deleteAddress")// prepara la vista del formulario
    public String DeleteAddress(Model model, @RequestParam int idUsuario, @RequestParam int IdDireccion) {

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Integer> requestEntity = new HttpEntity<>(IdDireccion, httpHeaders);
        ResponseEntity<Result<Integer>> response = restTemplate.exchange("http://localhost:8080/usuarioapi/direccion/" + IdDireccion,
                HttpMethod.DELETE,
                requestEntity,
                new ParameterizedTypeReference<Result<Integer>>() {
        });
        Result result = response.getBody();
        if (result.correct) {
            return "redirect:/usuario/addUser/" + idUsuario;
        }
        return "redirect:/usuario/addUser/" + idUsuario;
    }

    @GetMapping("RedireccionarFormulario")
    public String RedireccionarFormulario(@RequestParam int idUsuario, @RequestParam(required = false) Integer IdDireccion, Model model) {

        if (IdDireccion == -1) { // editarUsuario
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<Result<UsuarioDireccion>> response = restTemplate.exchange("http://localhost:8080/usuarioapi/soloUsuario/" + idUsuario,
                    HttpMethod.GET,
                    HttpEntity.EMPTY,
                    new ParameterizedTypeReference<Result<UsuarioDireccion>>() {
            });
            UsuarioDireccion usuariosDireccion = response.getBody().object;
            usuariosDireccion.Direccion = new Direccion();
            usuariosDireccion.Direccion.setIdDireccion(-1);
            model.addAttribute("usuarioDireccion", usuariosDireccion);

            RestTemplate restTemplateRoll = new RestTemplate();
            ResponseEntity<Result<Roll>> responseRolles = restTemplateRoll.exchange("http://localhost:8080/usuarioapi/roll",
                    HttpMethod.GET,
                    HttpEntity.EMPTY,
                    new ParameterizedTypeReference<Result<Roll>>() {
            });

            model.addAttribute("rolles", responseRolles.getBody().objects);

        } else if (IdDireccion == 0) { //  Agregar direccion
            UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
            usuarioDireccion.usuario = new Usuario();
            usuarioDireccion.usuario.setIdUsuario(idUsuario); // identifico a quien voy a darle nueva direccion
            usuarioDireccion.Direccion = new Direccion();
            model.addAttribute("usuarioDireccion", usuarioDireccion);
            RestTemplate restTemplatePais = new RestTemplate();
            ResponseEntity<Result<Pais>> responsePais = restTemplatePais.exchange("http://localhost:8080/usuarioapi/pais",
                    HttpMethod.GET,
                    HttpEntity.EMPTY,
                    new ParameterizedTypeReference<Result<Pais>>() {
            });
            model.addAttribute("paises", responsePais.getBody().objects);

        } else { // editar direccion
            RestTemplate restTemplatePais = new RestTemplate();
            ResponseEntity<Result<Pais>> responsePais = restTemplatePais.exchange("http://localhost:8080/usuarioapi/pais",
                    HttpMethod.GET,
                    HttpEntity.EMPTY,
                    new ParameterizedTypeReference<Result<Pais>>() {
            });
            model.addAttribute("paises", responsePais.getBody().objects);

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<Result<UsuarioDireccion>> response = restTemplate.exchange("http://localhost:8080/usuarioapi/direccion/" + IdDireccion,
                    HttpMethod.GET,
                    HttpEntity.EMPTY,
                    new ParameterizedTypeReference<Result<UsuarioDireccion>>() {
            });
            UsuarioDireccion usuariosDireccion = response.getBody().object;
            usuariosDireccion.usuario = new Usuario();
            usuariosDireccion.usuario.setIdUsuario(idUsuario);
            model.addAttribute("usuarioDireccion", usuariosDireccion);

            RestTemplate restTemplateEstado = new RestTemplate();
            ResponseEntity<Result<Estado>> responseEstados = restTemplateEstado.exchange("http://localhost:8080/usuarioapi/estado/" + usuariosDireccion.Direccion.Colonia.Municipio.Estado.Pais.getIdPais(),
                    HttpMethod.GET,
                    HttpEntity.EMPTY,
                    new ParameterizedTypeReference<Result<Estado>>() {
            });
            model.addAttribute("estados", responseEstados.getBody().objects);

            RestTemplate restTemplateMunicipio = new RestTemplate();
            ResponseEntity<Result<Municipio>> responseMunicipios = restTemplateMunicipio.exchange("http://localhost:8080/usuarioapi/municipio/" + usuariosDireccion.Direccion.Colonia.Municipio.Estado.getIdEstado(),
                    HttpMethod.GET,
                    HttpEntity.EMPTY,
                    new ParameterizedTypeReference<Result<Municipio>>() {
            });
            model.addAttribute("municipios", responseMunicipios.getBody().objects);

            RestTemplate restTemplateColonia = new RestTemplate();
            ResponseEntity<Result<Colonia>> responseColonias = restTemplateColonia.exchange("http://localhost:8080/usuarioapi/colonia/" + usuariosDireccion.Direccion.Colonia.Municipio.getIdMunicipio(),
                    HttpMethod.GET,
                    HttpEntity.EMPTY,
                    new ParameterizedTypeReference<Result<Colonia>>() {
            });
            model.addAttribute("colonias", responseColonias.getBody().objects);

        }
        return "FormUsuario";
    }

    @GetMapping("cargaMasiva")
    public String cargaMasiva() {
        return "CargaMasiva";
    }

    @PostMapping("cargaMasiva")
    public ResponseEntity<String> cargaMasiva(@RequestParam MultipartFile archivo, Model model, HttpSession session) throws IOException {

        if (archivo != null && !archivo.isEmpty()) {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders httpHeaders = new HttpHeaders();
            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("archivo", archivo.getResource());
            httpHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, httpHeaders);
            ResponseEntity<Result<MultipartFile>> response = restTemplate.exchange("http://localhost:8080/usuarioapi/cargaMasiva",
                    HttpMethod.POST,
                    requestEntity,
                    new ParameterizedTypeReference<Result<MultipartFile>>() {
            });
            Result result = response.getBody();
            if (result.correct) {
                String url = "http://localhost:8081/usuario/cargaMasiva/Procesar?encriptado=" + result.errorMasassge;

                RestTemplate restTemplateUrl = new RestTemplate();
                ResponseEntity<String> responseUrl = restTemplateUrl.getForEntity(url, String.class);

                return responseUrl;
            }
        }
        return null;
    }

    @GetMapping("/cargaMasiva/Procesar")
    public String ProcesarCargaMasiva(@RequestParam String encriptado) throws FileNotFoundException, IOException, UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
        final String claveEncriptacion = "¡secreto!";
        String desencriptado = desencriptar(encriptado, claveEncriptacion);
        String[] procesar = new String[4];
        try (BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\digis\\OneDrive\\Documentos\\Adalberto Salinas Jose\\ProgramasJava\\ASalinasNCapasJSP\\com.disgis01_ASalinasNCapasServiceWeb\\" + desencriptado))) {
            String linea;
            
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split("\\|");
                procesar = datos;
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (procesar[1].equals("true") && procesar[3].equals( "el documento esta listo para usar una vez") ) {
                RestTemplate restTemplate = new RestTemplate();
                HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.setContentType(MediaType.APPLICATION_JSON);
                HttpEntity<String> requestEntity = new HttpEntity<>(encriptado, httpHeaders);
                ResponseEntity<Result<String>> response = restTemplate.exchange("http://localhost:8080/usuarioapi/cargaMasiva/Procesar?encriptado=" + encriptado,
                        HttpMethod.GET,
                        requestEntity,
                        new ParameterizedTypeReference<Result<String>>() {
                });
                Result result = response.getBody();
                if (result.correct) {
                    return "redirect:/usuario/index";
                }
            } else if (procesar[1].equals( "false") && procesar[3].equals( "el documento subido correctamente")) {
                return "redirect:/usuario/index";
            } else if (procesar[1] == "false" && procesar[3] == "contienen un error el documento") {
                return "CargaMasiva";
            }
        return desencriptado;
    }

    @GetMapping("Estado/{idPais}")
    @ResponseBody // retorno de dato estructurado (objeto en JSON/XML)
    public Result Estado(@PathVariable int idPais) { // recibe el valor que esta siendo enviado desde el ajax en el html
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Result<Estado>> response = restTemplate.exchange("http://localhost:8080/usuarioapi/estado/" + idPais,
                HttpMethod.GET,
                HttpEntity.EMPTY,
                new ParameterizedTypeReference<Result<Estado>>() {
        });
//        List<Estado> estados = response.getBody().objects;

        return response.getBody(); // se manda la informacion a travez del metodo
    }

    @GetMapping("Municipio/{idEstado}")
    @ResponseBody
    public Result Municipio(@PathVariable int idEstado) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Result<Municipio>> response = restTemplate.exchange("http://localhost:8080/usuarioapi/municipio/" + idEstado,
                HttpMethod.GET,
                HttpEntity.EMPTY,
                new ParameterizedTypeReference<Result<Municipio>>() {
        });

        return response.getBody();
    }

    @GetMapping("Colonia/{idMunicipio}")
    @ResponseBody
    public Result Colonia(@PathVariable int idMunicipio) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Result<Colonia>> response = restTemplate.exchange("http://localhost:8080/usuarioapi/colonia/" + idMunicipio,
                HttpMethod.GET,
                HttpEntity.EMPTY,
                new ParameterizedTypeReference<Result<Colonia>>() {
        });

        return response.getBody();
    }

    @PostMapping("Activo")
    @ResponseBody
    public Result ActivoUsuario(@RequestParam int IdUsuario, @RequestParam int ActivoUsuario) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Result<Integer>> response = restTemplate.exchange("http://localhost:8080/usuarioapi/estatus?IdUsuario=" + IdUsuario + "&ActivoUsuario=" + ActivoUsuario,
                HttpMethod.GET,
                HttpEntity.EMPTY,
                new ParameterizedTypeReference<Result<Integer>>() {
        });

        return response.getBody();
    }

    @GetMapping("CodigoPostal/{CodigoPostal}")
    @ResponseBody
    public Result CodigoPostal(@PathVariable String CodigoPostal) {
//        return coloniaDAOImplementation.GetByIdColonias(idMunicipio);
//        return paisJPADAOImplementation.PaisGetByCodigoPostal(CodigoPostal);
        return null;
    }

    @GetMapping("pruebas")
    public String Purebas(Model model) {
        return "Pruebas";
    }

    private SecretKeySpec crearClave(String clave) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        byte[] claveEncriptacion = clave.getBytes("UTF-8");
        MessageDigest sha = MessageDigest.getInstance("SHA-1");
        claveEncriptacion = sha.digest(claveEncriptacion);
        claveEncriptacion = Arrays.copyOf(claveEncriptacion, 16);
        SecretKeySpec secretKey = new SecretKeySpec(claveEncriptacion, "AES");

        return secretKey;
    }

    public String desencriptar(String datosEncriptados, String claveSecreta) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
        SecretKeySpec secretKey = this.crearClave(claveSecreta);

        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);

        byte[] bytesEncriptados = Base64.getDecoder().decode(datosEncriptados);
        byte[] datosDesencriptados = cipher.doFinal(bytesEncriptados);
        String datos = new String(datosDesencriptados);

        return datos;
    }

    public String encriptar(String datos, String claveSecreta) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
        SecretKeySpec secretKey = this.crearClave(claveSecreta);

        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);

        byte[] datosEncriptar = datos.getBytes("UTF-8");
        byte[] bytesEncriptados = cipher.doFinal(datosEncriptar);
        String encriptado = Base64.getEncoder().encodeToString(bytesEncriptados);

        return encriptado;
    }
}

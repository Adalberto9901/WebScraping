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
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
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
        ResponseEntity<Result<UsuarioDireccion>> response = restTemplate.exchange("http://localhost:8080/usuarioapi/"+ usuarioDireccion.usuario.getIdUsuario(),
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
        ResponseEntity<Result<UsuarioDireccion>> response = restTemplate.exchange("http://localhost:8080/usuarioapi/"+ idUsuario,
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
        ResponseEntity<Result<UsuarioDireccion>> response = restTemplate.exchange("http://localhost:8080/usuarioapi/direccion/"+ usuarioDireccion.usuario.getIdUsuario(),
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
        ResponseEntity<Result<UsuarioDireccion>> response = restTemplate.exchange("http://localhost:8080/usuarioapi/direccion/"+ usuarioDireccion.Direccion.getIdDireccion(),
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
        ResponseEntity<Result<Integer>> response = restTemplate.exchange("http://localhost:8080/usuarioapi/direccion/"+ IdDireccion,
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
    public String cargaMasiva(@RequestParam MultipartFile archivo, Model model, HttpSession session) throws IOException {

        if (archivo != null && !archivo.isEmpty()) {
            String fileExtention = archivo.getOriginalFilename().split("\\.")[1];

            String root = System.getProperty("user.dir");
            String path = "src/main/resources/Archivos";
            String fecha = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
            String absolutePath = root + "/" + path + "/" + fecha + archivo.getOriginalFilename();

            List<UsuarioDireccion> usuariosDireccion = new ArrayList<>();

            if (fileExtention.equals("txt")) {
                usuariosDireccion = LecturaArchivoTXT(archivo);
                archivo.transferTo(new File(absolutePath));
            } else { //"xlsx"
                archivo.transferTo(new File(absolutePath));
                usuariosDireccion = LecturaArchivoXLSX(new File(absolutePath));
            }

            List<ResultValidarDatos> listaErrores = resultValidarDatos(usuariosDireccion);
            if (listaErrores.isEmpty()) {
                session.setAttribute("path", absolutePath);
                model.addAttribute("listaErrores", listaErrores);
                model.addAttribute("archivoCorrecto", true);
                return "redirect:/usuario/cargaMasiva/Procesar";
            } else {
                model.addAttribute("listaErrores", listaErrores);
                model.addAttribute("archivoCorrecto", false);
            }
        }
        return "CargaMasiva";
    }

    @GetMapping("/cargaMasiva/Procesar")
//    @ResponseBody
    public String ProcesarCargaMasiva(HttpSession session, Model model) throws FileNotFoundException, IOException {

        String ruta = session.getAttribute("path").toString();
        if (ruta != null && !ruta.isEmpty()) {
            String fileExtention = ruta.split("\\.")[1];

            List<UsuarioDireccion> usuariosDireccion = new ArrayList<>();

            if (fileExtention.equals("txt")) {
                MultipartFile multipartFile = convertFileToMultipartFile(new File(ruta));
                usuariosDireccion = LecturaArchivoTXT(multipartFile);
            } else {
                usuariosDireccion = LecturaArchivoXLSX(new File(ruta));
            }

            List<ResultValidarDatos> listaErrores = resultValidarDatos(usuariosDireccion);
            if (listaErrores.isEmpty()) {
//                model.addAttribute("usuariosDireccion", usuarioDAOImplementation.Add(usuariosDireccion).objects);
//                model.addAttribute("usuariosDireccion", usuarioJPADAOImplementation.Add(usuariosDireccion).objects);
                return "redirect:/usuario/index";
            } else {
                model.addAttribute("listaErrores", listaErrores);
                model.addAttribute("archivoCorrecto", false);
            }
        }
        session.removeAttribute("path");

        return "CargaMasiva";
    }

    public List<UsuarioDireccion> LecturaArchivoTXT(MultipartFile archivo) {
        List<UsuarioDireccion> usuariosDireccion = new ArrayList<>();

        try (InputStream inputStream = archivo.getInputStream(); BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));) {
            bufferedReader.readLine();
            String linea = "";
            while ((linea = bufferedReader.readLine()) != null) {
                String[] datos = linea.split("\\|");

                UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
                usuarioDireccion.usuario = new Usuario();

                usuarioDireccion.usuario.setNombreUsuario(datos[0]);
                usuarioDireccion.usuario.setApellidoPatUsuario(datos[1]);
                usuarioDireccion.usuario.setApellidoMatUsuario(datos[2]);
                usuarioDireccion.usuario.setFechaNacimeintoUsuario(new SimpleDateFormat("yyyy-MM-dd").parse(datos[3]));
                usuarioDireccion.usuario.setSexoUsuario(datos[4]);
                usuarioDireccion.usuario.setCorreoUsuario(datos[5]);
                usuarioDireccion.usuario.setCelularUsuario(datos[6]);
                usuarioDireccion.usuario.setPasswordUsuario(datos[7]);
                usuarioDireccion.usuario.setTelefonoUsuario(datos[8]);
                usuarioDireccion.usuario.setUserNombreUsuario(datos[9]);

                usuarioDireccion.usuario.Roll = new Roll();
                usuarioDireccion.usuario.Roll.setIdRoll(Integer.parseInt(datos[10]));

                usuarioDireccion.Direccion = new Direccion();
                usuarioDireccion.Direccion.setCalle(datos[11]);
                usuarioDireccion.Direccion.setNumeroInterior(datos[12]);
                usuarioDireccion.Direccion.setNumeroExterior(datos[13]);

                usuarioDireccion.Direccion.Colonia = new Colonia();
                usuarioDireccion.Direccion.Colonia.setIdColonia(Integer.parseInt(datos[14]));

                usuariosDireccion.add(usuarioDireccion);
            }
        } catch (Exception e) {
            usuariosDireccion = null;
        }
        return usuariosDireccion;
    }

    public List<UsuarioDireccion> LecturaArchivoXLSX(File archivo) {
        List<UsuarioDireccion> usuariosDireccion = new ArrayList<>();

        try (XSSFWorkbook workbook = new XSSFWorkbook(archivo);) {
            XSSFSheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
                usuarioDireccion.usuario = new Usuario();

                usuarioDireccion.usuario.setNombreUsuario(row.getCell(0) != null ? row.getCell(0).toString() : "");
                usuarioDireccion.usuario.setApellidoPatUsuario(row.getCell(1) != null ? row.getCell(1).toString() : "");
                usuarioDireccion.usuario.setApellidoMatUsuario(row.getCell(2) != null ? row.getCell(2).toString() : "");
                usuarioDireccion.usuario.setFechaNacimeintoUsuario(row.getCell(3).getDateCellValue());
                usuarioDireccion.usuario.setSexoUsuario(row.getCell(4) != null ? row.getCell(4).toString() : "");
                usuarioDireccion.usuario.setCorreoUsuario(row.getCell(5) != null ? row.getCell(5).toString() : "");
                usuarioDireccion.usuario.setCelularUsuario(row.getCell(6) != null ? row.getCell(6).toString() : "");
                usuarioDireccion.usuario.setPasswordUsuario(row.getCell(7) != null ? row.getCell(7).toString() : "");
                usuarioDireccion.usuario.setTelefonoUsuario(row.getCell(8) != null ? row.getCell(8).toString() : "");
                usuarioDireccion.usuario.setCURPUsuario(row.getCell(9) != null ? row.getCell(9).toString() : "");
                usuarioDireccion.usuario.setUserNombreUsuario(row.getCell(10) != null ? row.getCell(10).toString() : "");

                usuarioDireccion.usuario.Roll = new Roll();
                usuarioDireccion.usuario.Roll.setIdRoll((int) row.getCell(11).getNumericCellValue());

                usuarioDireccion.Direccion = new Direccion();
                usuarioDireccion.Direccion.setCalle(row.getCell(12) != null ? row.getCell(12).toString() : "");
                usuarioDireccion.Direccion.setNumeroInterior(row.getCell(13) != null ? row.getCell(13).toString() : "");
                usuarioDireccion.Direccion.setNumeroExterior(row.getCell(14) != null ? row.getCell(14).toString() : "");

                usuarioDireccion.Direccion.Colonia = new Colonia();
                usuarioDireccion.Direccion.Colonia.setIdColonia((int) row.getCell(15).getNumericCellValue());

                usuariosDireccion.add(usuarioDireccion);
            }

        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
        return usuariosDireccion;
    }

    private List<ResultValidarDatos> resultValidarDatos(List<UsuarioDireccion> usuarios) {
        List<ResultValidarDatos> listaErrores = new ArrayList<>();
        int fila = 1;
        if (usuarios == null) {
            listaErrores.add(new ResultValidarDatos(0, "La lista no existe", "Lista inexistente"));
        } else if (usuarios.isEmpty()) {
            listaErrores.add(new ResultValidarDatos(0, "La lista etsa vacia", "Lista vacia"));
        } else {
            for (UsuarioDireccion usuarioDireccion : usuarios) {
                if (usuarioDireccion.usuario.getNombreUsuario() == null || usuarioDireccion.usuario.getNombreUsuario().equals("")) {
                    listaErrores.add(new ResultValidarDatos(fila, usuarioDireccion.usuario.getNombreUsuario(), "Campo Obligatorio"));
                }
                if (usuarioDireccion.usuario.getApellidoPatUsuario() == null || usuarioDireccion.usuario.getApellidoPatUsuario().equals("")) {
                    listaErrores.add(new ResultValidarDatos(fila, usuarioDireccion.usuario.getApellidoPatUsuario(), "Campo Obligatorio"));
                }
                if (usuarioDireccion.usuario.getApellidoMatUsuario() == null || usuarioDireccion.usuario.getApellidoMatUsuario().equals("")) {
                    listaErrores.add(new ResultValidarDatos(fila, usuarioDireccion.usuario.getApellidoMatUsuario(), "Campo Obligatorio"));
                }
                if (usuarioDireccion.usuario.getFechaNacimeintoUsuario() == null || new SimpleDateFormat("yyyy-MM-dd").format(usuarioDireccion.usuario.getFechaNacimeintoUsuario()).equals("")) {
                    listaErrores.add(new ResultValidarDatos(fila, new SimpleDateFormat("yyyy-MM-dd").format(usuarioDireccion.usuario.getFechaNacimeintoUsuario()), "Campo Obligatorio"));
                }
                if (usuarioDireccion.usuario.getSexoUsuario() == null || usuarioDireccion.usuario.getSexoUsuario().equals("")) {
                    listaErrores.add(new ResultValidarDatos(fila, usuarioDireccion.usuario.getSexoUsuario(), "Campo Obligatorio"));
                }
                if (usuarioDireccion.usuario.getCorreoUsuario() == null || usuarioDireccion.usuario.getCorreoUsuario().equals("")) {
                    listaErrores.add(new ResultValidarDatos(fila, usuarioDireccion.usuario.getCorreoUsuario(), "Campo Obligatorio"));
                }
                if (usuarioDireccion.usuario.getCelularUsuario() == null || usuarioDireccion.usuario.getCelularUsuario().equals("")) {
                    listaErrores.add(new ResultValidarDatos(fila, usuarioDireccion.usuario.getCelularUsuario(), "Campo Obligatorio"));
                }
                if (usuarioDireccion.usuario.getPasswordUsuario() == null || usuarioDireccion.usuario.getPasswordUsuario().equals("")) {
                    listaErrores.add(new ResultValidarDatos(fila, usuarioDireccion.usuario.getPasswordUsuario(), "Campo Obligatorio"));
                }
                if (usuarioDireccion.usuario.getTelefonoUsuario() == null || usuarioDireccion.usuario.getTelefonoUsuario().equals("")) {
                    listaErrores.add(new ResultValidarDatos(fila, usuarioDireccion.usuario.getTelefonoUsuario(), "Campo Obligatorio"));
                }
                if (usuarioDireccion.usuario.getUserNombreUsuario() == null || usuarioDireccion.usuario.getUserNombreUsuario().equals("")) {
                    listaErrores.add(new ResultValidarDatos(fila, usuarioDireccion.usuario.getUserNombreUsuario(), "Campo Obligatorio"));
                }
                if (usuarioDireccion.usuario.Roll.getIdRoll() == -1 || Integer.toString(usuarioDireccion.usuario.Roll.getIdRoll()).equals("")) {
                    listaErrores.add(new ResultValidarDatos(fila, Integer.toString(usuarioDireccion.usuario.Roll.getIdRoll()), "Campo Obligatorio"));
                }
                fila++;
            }
        }
        return listaErrores;
    }

    public static MultipartFile convertFileToMultipartFile(File file, String paramName) throws IOException {
        String contentType = Files.probeContentType(file.toPath());
        try (FileInputStream input = new FileInputStream(file)) {
            return new MockMultipartFile(
                    paramName,
                    file.getName(),
                    contentType,
                    input
            );
        }
    }

    public static MultipartFile convertFileToMultipartFile(File file) throws IOException {
        return convertFileToMultipartFile(file, "file"); // default param name
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
        ResponseEntity<Result<Integer>> response = restTemplate.exchange("http://localhost:8080/usuarioapi/estatus?IdUsuario=" + IdUsuario +"&ActivoUsuario=" + ActivoUsuario,
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
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.disgis01.ASalinasNCapas.Controller;

import com.disgis01.ASalinasNCapas.DAO.ColoniaDAOImplementation;
import com.disgis01.ASalinasNCapas.DAO.DireccionDAOImplementation;
import com.disgis01.ASalinasNCapas.DAO.EstadoDAOImplementation;
import com.disgis01.ASalinasNCapas.DAO.MunicipioDAOImplementation;
import com.disgis01.ASalinasNCapas.DAO.PaisDAOImplementation;
import com.disgis01.ASalinasNCapas.DAO.RollDAOImplementation;
import com.disgis01.ASalinasNCapas.DAO.UsuarioDAOImplementation;
import com.disgis01.ASalinasNCapas.ML.Direccion;
import com.disgis01.ASalinasNCapas.ML.Result;
import com.disgis01.ASalinasNCapas.ML.ResultValidarDatos;
import com.disgis01.ASalinasNCapas.ML.Roll;
import com.disgis01.ASalinasNCapas.ML.Usuario;
import com.disgis01.ASalinasNCapas.ML.UsuarioDireccion;
import jakarta.validation.Valid;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.SimpleFormatter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Alien 1
 */
@Controller
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired //se usa para rellenar la informacion necesaria
    private UsuarioDAOImplementation usuarioDAOImplementation;
    @Autowired //se usa para rellenar la informacion necesaria
    private PaisDAOImplementation paisDAOImplementation;
    @Autowired //se usa para rellenar la informacion necesaria
    private RollDAOImplementation rollDAOImplementation;
    @Autowired //se usa para rellenar la informacion necesaria
    private EstadoDAOImplementation estadoDAOImplementation;
    @Autowired //se usa para rellenar la informacion necesaria
    private MunicipioDAOImplementation municipioDAOImplementation;
    @Autowired //se usa para rellenar la informacion necesaria
    private ColoniaDAOImplementation coloniaDAOImplementation;
    @Autowired //se usa para rellenar la informacion necesaria
    private DireccionDAOImplementation direccionDAOImplementation;

    @GetMapping("index")
    public String Index(Model model) {
        Result result = usuarioDAOImplementation.GetAll(); //se atrapa el reusltado el sera un 1 o un 0

        if (result.correct) {
            model.addAttribute("usuariosDireccion", result.objects); //se manda la informacion a la vista a traves de una variable
            model.addAttribute("rolles", rollDAOImplementation.GetAllRoll().objects);//se manda roll para cargar el option del select
            model.addAttribute("usuarioDireccion", new UsuarioDireccion());
        }
        return "IndexUsuario";
    }

    @PostMapping("index")
    public String Index(Model model, @ModelAttribute UsuarioDireccion usuarioBusqueda) {

        model.addAttribute("usuarioBusqueda", usuarioBusqueda); //se manda la informacion a la vista a traves de una variable
        model.addAttribute("usuariosDireccion", usuarioDAOImplementation.UsuarioBusqueda(usuarioBusqueda).objects); //se manda la informacion a la vista a traves de una variable
        model.addAttribute("rolles", rollDAOImplementation.GetAllRoll().objects);//se manda roll para cargar el option del select
        model.addAttribute("usuarioDireccion", new UsuarioDireccion());

        return "IndexUsuario";
    }

    @GetMapping("addUser/{idUsuario}")// prepara la vista del formulario
    public String Formulario(Model model, @PathVariable int idUsuario) {

        if (idUsuario < 1) {
            model.addAttribute("paises", paisDAOImplementation.GetAllPais().objects);//se manda pais para cargar el option del select
            model.addAttribute("rolles", rollDAOImplementation.GetAllRoll().objects);//se manda roll para cargar el option del select
            model.addAttribute("usuarioDireccion", new UsuarioDireccion());
            return "FormUsuario";

        } else {
            model.addAttribute("usuarioDireccion", usuarioDAOImplementation.GetById(idUsuario).object); //se manda la informacion a la vista a traves de una variable

            return "UsuarioDetail";
        }

    }

    @PostMapping("addUser")// recuperar los datos del formulario
    public String Formulario(@Valid @ModelAttribute UsuarioDireccion usuarioDireccion,
            BindingResult bindingResult, @RequestParam MultipartFile Imagen,
            Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("paises", paisDAOImplementation.GetAllPais().objects);//se manda pais para cargar el option del select
            model.addAttribute("rolles", rollDAOImplementation.GetAllRoll().objects);//se manda roll para cargar el option del select
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

        Result result = usuarioDAOImplementation.Add(usuarioDireccion); // se manda la informacion al usuarioDAoOImplementation que se cargo con el form
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
            model.addAttribute("rolles", rollDAOImplementation.GetAllRoll().objects);//se manda roll para cargar el option del select
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
        Result result = usuarioDAOImplementation.Update(usuarioDireccion); // se manda la informacion al usuarioDAoOImplementation que se cargo con el form
        if (result.correct) {
            return "redirect:index";//rediccionar a la vista getAll
        }
        return "redirect:index";//rediccionar de nuevo al formulario
    }

    @PostMapping("addAddress")// recuperar los datos del formulario
    public String DireccionFormulario(@Valid @ModelAttribute UsuarioDireccion usuarioDireccion,
            BindingResult bindingResult,
            Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("paises", paisDAOImplementation.GetAllPais().objects);//se manda pais para cargar el option del select
            model.addAttribute("rolles", rollDAOImplementation.GetAllRoll().objects);//se manda roll para cargar el option del select
            model.addAttribute("usuarioDireccion", usuarioDireccion);
            return "FormUsuario";
        }
        Result result = direccionDAOImplementation.Add(usuarioDireccion); // se manda la informacion al usuarioDAoOImplementation que se cargo con el form
        if (result.correct) {
            return "redirect:index";//rediccionar a la vista getAll
        }
        return "redirect:index";//rediccionar de nuevo al formulario
    }

    @PostMapping("updateAddress")// recuperar los datos del formulario
    public String DireccionUpdate(@Valid @ModelAttribute UsuarioDireccion usuarioDireccion,
            BindingResult bindingResult,
            Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("paises", paisDAOImplementation.GetAllPais().objects);//se manda pais para cargar el option del select
            model.addAttribute("estados", estadoDAOImplementation.GetByIdEstados(usuarioDireccion.Direccion.Colonia.Municipio.Estado.Pais.getIdPais()).objects);
            model.addAttribute("municipios", municipioDAOImplementation.GetByIdMunicipios(usuarioDireccion.Direccion.Colonia.Municipio.Estado.getIdEstado()).objects);
            model.addAttribute("colonias", coloniaDAOImplementation.GetByIdColonias(usuarioDireccion.Direccion.Colonia.Municipio.getIdMunicipio()).objects);
            model.addAttribute("usuarioDireccion", usuarioDireccion);
            return "FormUsuario";
        }
        Result result = direccionDAOImplementation.Update(usuarioDireccion); // se manda la informacion al usuarioDAoOImplementation que se cargo con el form
        if (result.correct) {
            return "redirect:index";//rediccionar a la vista getAll
        }
        return "redirect:index";//rediccionar de nuevo al formulario
    }

    @GetMapping("RedireccionarFormulario")
    public String RedireccionarFormulario(@RequestParam int idUsuario, @RequestParam(required = false) Integer IdDireccion, Model model) {

        if (IdDireccion == -1) { // editarUsuario
            UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
            usuarioDireccion.usuario = new Usuario();
            usuarioDireccion.usuario.setIdUsuario(idUsuario);
            usuarioDireccion = (UsuarioDireccion) usuarioDAOImplementation.UsuarioGetSolo(idUsuario).object;
            usuarioDireccion.Direccion = new Direccion();
            usuarioDireccion.Direccion.setIdDireccion(-1);
            model.addAttribute("rolles", rollDAOImplementation.GetAllRoll().objects);//se manda roll para cargar el option del select
            model.addAttribute("usuarioDireccion", usuarioDireccion);

        } else if (IdDireccion == 0) { //  Agregar direccion
            UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
            usuarioDireccion.usuario = new Usuario();
            usuarioDireccion.usuario.setIdUsuario(idUsuario); // identifico a quien voy a darle nueva direccion
            usuarioDireccion.Direccion = new Direccion();
            model.addAttribute("usuarioDireccion", usuarioDireccion);
            model.addAttribute("paises", paisDAOImplementation.GetAllPais().objects);
            // roles

        } else { // editar direccion
            UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
            usuarioDireccion.usuario = new Usuario();
            usuarioDireccion.usuario.setIdUsuario(idUsuario);
            usuarioDireccion.Direccion = new Direccion(); // recuperar direccion usuario por id direccion
            usuarioDireccion.Direccion = (Direccion) direccionDAOImplementation.DireccionGetById(IdDireccion).object;
            model.addAttribute("paises", paisDAOImplementation.GetAllPais().objects);
            model.addAttribute("estados", estadoDAOImplementation.GetByIdEstados(usuarioDireccion.Direccion.Colonia.Municipio.Estado.Pais.getIdPais()).objects);
            model.addAttribute("municipios", municipioDAOImplementation.GetByIdMunicipios(usuarioDireccion.Direccion.Colonia.Municipio.Estado.getIdEstado()).objects);
            model.addAttribute("colonias", coloniaDAOImplementation.GetByIdColonias(usuarioDireccion.Direccion.Colonia.Municipio.getIdMunicipio()).objects);
            model.addAttribute("usuarioDireccion", usuarioDireccion);

        }
        return "FormUsuario";
    }

    @GetMapping("cargaMasiva")
    public String cargaMasiva() {
        return "CargaMasiva";
    }

    @PostMapping("cargaMasiva")
    public String cargaMasiva(@RequestParam MultipartFile archivo) {

        if (archivo != null && !archivo.isEmpty()) {
            String fileExtention = archivo.getOriginalFilename().split("\\.")[1];
            List<UsuarioDireccion> usuariosDireccion = new ArrayList<>();

            if (fileExtention.equals("txt")) {
                usuariosDireccion = LecturaArchivoTXT(archivo);
            } else { //"xlsx"
                usuariosDireccion = LecturaArchivoXLSX(archivo);
            }
            resultValidarDatos(usuariosDireccion);
        }

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
                
                usuariosDireccion.add(usuarioDireccion);
            }
        } catch (Exception e) {
            usuariosDireccion = null;
        }
        return usuariosDireccion;
    }

    public List<UsuarioDireccion> LecturaArchivoXLSX(MultipartFile archivo) {
        List<UsuarioDireccion> usuarioDireccion = new ArrayList<>();

        try (InputStream inputStream = archivo.getInputStream(); Workbook workbook = new XSSFWorkbook(inputStream);) {
            //se inicia en la primera celda
            Sheet hoja = workbook.getSheetAt(0);
            DataFormatter dataFormatter = new DataFormatter();
            Iterator<Row> filaIterator = hoja.iterator();
            while (filaIterator.hasNext()) {
                Row filaActual = filaIterator.next();
                Iterator<Cell> celdaterator = filaActual.iterator();
//                List<UsuarioDireccion> usuarioDireccion = new ArrayList<>();

            }

        } catch (Exception e) {
            usuarioDireccion = null;
        }
        return usuarioDireccion;
    }

    private List<ResultValidarDatos> resultValidarDatos(List<UsuarioDireccion> usuarios){
    List<ResultValidarDatos> listaErrores = new ArrayList<>();
    int fila = 1;
        if (usuarios == null) {
            listaErrores.add(new ResultValidarDatos(0,"La lista no existe","Lista inexistente"));
        }else if (usuarios.isEmpty()) {
            listaErrores.add(new ResultValidarDatos(0,"La lista etsa vacia","Lista vacia"));
        }else{
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
                if (usuarioDireccion.usuario.getFechaNacimeintoUsuario() == null || usuarioDireccion.usuario.getFechaNacimeintoUsuario().equals("")) {
                    listaErrores.add(new ResultValidarDatos(fila,new SimpleDateFormat("yyyy-MM-dd").format( usuarioDireccion.usuario.getFechaNacimeintoUsuario()), "Campo Obligatorio"));
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
                if (Integer.toString(usuarioDireccion.usuario.Roll.getIdRoll()) == null || Integer.toString(usuarioDireccion.usuario.Roll.getIdRoll()).equals("")) {
                    listaErrores.add(new ResultValidarDatos(fila,Integer.toString(usuarioDireccion.usuario.Roll.getIdRoll()), "Campo Obligatorio"));
                }
                fila++;
            }
        }
        return null;
    }
    
    @GetMapping("Estado/{idPais}")
    @ResponseBody // retorno de dato estructurado (objeto en JSON/XML)
    public Result Estado(@PathVariable int idPais) { // recibe el valor que esta siendo enviado desde el ajax en el html
        return estadoDAOImplementation.GetByIdEstados(idPais); // se manda la informacion a travez del metodo
    }

    @GetMapping("Municipio/{idEstado}")
    @ResponseBody
    public Result Municipio(@PathVariable int idEstado) {
        return municipioDAOImplementation.GetByIdMunicipios(idEstado);
    }

    @GetMapping("Colonia/{idMunicipio}")
    @ResponseBody
    public Result Colonia(@PathVariable int idMunicipio) {
        return coloniaDAOImplementation.GetByIdColonias(idMunicipio);
    }

    @PostMapping("Activo")
    @ResponseBody
    public Result ActivoUsuario(@RequestParam int IdUsuario, @RequestParam int ActivoUsuario) {
        return usuarioDAOImplementation.UpdateActivo(IdUsuario, ActivoUsuario);
    }
}

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
import com.disgis01.ASalinasNCapas.ML.Usuario;
import com.disgis01.ASalinasNCapas.ML.UsuarioDireccion;
import jakarta.validation.Valid;
import java.util.Base64;
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
    public Result ActivoUsuario(@RequestParam int IdUsuario, @RequestParam int ActivoUsuario){
        return usuarioDAOImplementation.UpdateActivo(IdUsuario,ActivoUsuario);
    }
}

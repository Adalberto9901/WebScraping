/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.disgis01.ASalinasNCapas;

import com.disgis01.ASalinasNCapas.DAO.ColoniaJPADAOImplementation;
import com.disgis01.ASalinasNCapas.DAO.DireccionJPADAOImplementation;
import com.disgis01.ASalinasNCapas.DAO.EstadoJPADAOImplementation;
import com.disgis01.ASalinasNCapas.DAO.IColoniaJPADAORepository;
import com.disgis01.ASalinasNCapas.DAO.IEstadoJPADAORepository;
import com.disgis01.ASalinasNCapas.DAO.IMunicipioJPADAORepository;
import com.disgis01.ASalinasNCapas.DAO.IPaisJPADAORepository;
import com.disgis01.ASalinasNCapas.DAO.IRollJPADAORepository;
import com.disgis01.ASalinasNCapas.DAO.MunicipioJPADAOImplementation;
import com.disgis01.ASalinasNCapas.DAO.PaisJPADAOImplementation;
import com.disgis01.ASalinasNCapas.DAO.RollJPADAOImplementation;
import com.disgis01.ASalinasNCapas.DAO.UsuarioJPADAOImplementation;
import com.disgis01.ASalinasNCapas.JPA.Colonia;
import com.disgis01.ASalinasNCapas.JPA.Direccion;
import com.disgis01.ASalinasNCapas.JPA.Estado;
import com.disgis01.ASalinasNCapas.JPA.Municipio;
import com.disgis01.ASalinasNCapas.JPA.Pais;
import com.disgis01.ASalinasNCapas.JPA.Result;
import com.disgis01.ASalinasNCapas.JPA.Roll;
import com.disgis01.ASalinasNCapas.JPA.Usuario;
import com.disgis01.ASalinasNCapas.JPA.UsuarioDireccion;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static javax.swing.UIManager.get;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Alien 1
 */
//@SpringBootTest
//@RestController
public class UsuarioJUnitTest {

//    @Autowired
//    private UsuarioJPADAOImplementation UsuarioJPADAOImplementation;
//    @Autowired
//    private DireccionJPADAOImplementation DireccionJPADAOImplementation;
//    @Autowired
//    private RollJPADAOImplementation RollJPADAOImplementation;
//    @Autowired
//    private PaisJPADAOImplementation PaisJPADAOImplementation;
//    @Autowired
//    private EstadoJPADAOImplementation EstadoJPADAOImplementation;
//    @Autowired
//    private MunicipioJPADAOImplementation MunicipioJPADAOImplementation;
//    @Autowired
//    private ColoniaJPADAOImplementation ColoniaJPADAOImplementation;
//
//    @Autowired
//    private TestRestTemplate restTemplate;
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Test
//    public void GetAll() throws Exception {
//        Result result = UsuarioJPADAOImplementation.GetAll();
//
//        
//        
//        Assertions.assertNotNull(result);
//        Assertions.assertTrue(result.correct);
//        Assertions.assertNull(result.object);
//        Assertions.assertNull(result.objects);
//        Assertions.assertNotNull(result.errorMasassge);
//        Assertions.assertNotNull(result.ex, "result.correct viene false y se esperaba true");
//
//    }
//
//    @Test
//    public void UsuarioGetById(int idUsuario) {
//        Result result = UsuarioJPADAOImplementation.GetById( idUsuario);
//
//        Assertions.assertNotNull(result);
//        Assertions.assertTrue(result.correct);
//        Assertions.assertNull(result.object);
//        Assertions.assertNull(result.objects);
//        Assertions.assertNotNull(result.errorMasassge);
//        Assertions.assertNotNull(result.ex, "result.correct viene false y se esperaba true");
//
//    }
//
//    @Test
//    public void UsuarioGetSolo(int idUsuario) {
//        Result result = UsuarioJPADAOImplementation.UsuarioGetSolo( idUsuario);
//
//        Assertions.assertNotNull(result);
//        Assertions.assertTrue(result.correct);
//        Assertions.assertNull(result.object);
//        Assertions.assertNull(result.objects);
//        Assertions.assertNotNull(result.errorMasassge);
//        Assertions.assertNotNull(result.ex, "result.correct viene false y se esperaba true");
//
//    }
//
//    @Test
//    public void DirecccionGetById(int idDireccion) {
//        Result result = DireccionJPADAOImplementation.DireccionGetById(idDireccion);
//
//        Assertions.assertNotNull(result);
//        Assertions.assertTrue(result.correct);
//        Assertions.assertNull(result.object);
//        Assertions.assertNull(result.objects);
//        Assertions.assertNotNull(result.errorMasassge);
//        Assertions.assertNotNull(result.ex, "result.correct viene false y se esperaba true");
//
//    }
//
//    @Test
//    public ResponseEntity AddUsuarioDireccion(@RequestBody UsuarioDireccion usuarioDireccion) {
//        try {
//            Result result = UsuarioJPADAOImplementation.Add(usuarioDireccion);
////            Result result = new Result();
//            try {
//
//                Usuario usuarioJPA = usuarioDireccion.usuario;
//                usuarioJPA.setActivoUsuario(1);
//
////                Usuario usuario = IUsuarioJPADAORepository.save(usuarioJPA);
////            Optional<Roll> rollJPA = IRollJPADAORepository.findById(usuarioDireccion.usuario.roll.getIdRoll());
//                Direccion direccionJPA = usuarioDireccion.direccion;
//                direccionJPA.setActivoDireccion(1);
////                direccionJPA.setUsuario(usuario);
//
////                Direccion direccion = IDireccionJPADAORepository.save(usuarioDireccion.direccion);
////            Usuario usuario = usuarioRepository.save(usuarioDireccion.usuario);
////            usuarioDireccion.direccion.setUsuario(usuario);
////            Direccion direccion = direccionRepository.save(usuarioDireccion.direccion);
//                result.correct = true;
//            } catch (Exception ex) {
//                result.correct = false;
//                result.errorMasassge = ex.getLocalizedMessage();
//                result.ex = ex;
//            }
//            if (result.correct) {
//                return ResponseEntity.ok().body(result);
//
//            } else {
//                return ResponseEntity.status(HttpStatus.valueOf(400)).body(result);
//            }
//
//        } catch (Exception ex) {
//            return ResponseEntity.status(HttpStatus.valueOf(500)).body(ex.getLocalizedMessage());
//        }
//    }
//
//    @Test
//    public ResponseEntity AddDireccionByUsuario(@PathVariable int idUsuario, @RequestBody UsuarioDireccion usuarioDireccion) {
//        try {
//            if (usuarioDireccion.getUsuario() == null) {
//                usuarioDireccion.setUsuario(new Usuario());
//            }
//            usuarioDireccion.getUsuario().setIdUsuario(idUsuario);
////            usuarioDireccion.getUsuario().setId(idUsuario);
//
//            Result result = DireccionJPADAOImplementation.Add(usuarioDireccion);
////            Result result = new Result();
//            try {
//
//                Direccion direccionJPA = usuarioDireccion.getDireccion();
//                direccionJPA.setActivoDireccion(1);
//
//                direccionJPA.setUsuario(usuarioDireccion.getUsuario());
////                Direccion direccion = IDireccionJPADAORepository.save(direccionJPA);
//
//                result.correct = true;
//            } catch (Exception ex) {
//                result.correct = false;
//                result.errorMasassge = ex.getLocalizedMessage();
//                result.ex = ex;
//            }
//            if (result.correct) {
//                return ResponseEntity.ok().body(result);
//
//            } else {
//                return ResponseEntity.status(HttpStatus.valueOf(400)).body(result);
//            }
//
//        } catch (Exception ex) {
//            return ResponseEntity.status(HttpStatus.valueOf(500)).body(ex.getLocalizedMessage());
//        }
//    }
//
//    @Test
//    public ResponseEntity UpdateUsuario(@PathVariable int idUsuario, @RequestBody UsuarioDireccion usuarioDireccion) {
//        try {
//            if (usuarioDireccion.getUsuario() == null) {
//                usuarioDireccion.setUsuario(new Usuario());
//            }
//            usuarioDireccion.getUsuario().setIdUsuario(idUsuario);
////            usuarioDireccion.getUsuario().setId(idUsuario);
//            Result result = UsuarioJPADAOImplementation.Update(usuarioDireccion);
////            Result result = new Result();
//            try {
//
//                Usuario usuarioJPA = usuarioDireccion.getUsuario();
//                usuarioJPA.setActivoUsuario(1);
//
////                usuarioJPA.setUsuario(usuarioDireccion.getUsuario());
////                Usuario usuario = IUsuarioJPADAORepository.save(usuarioJPA);
//                result.correct = true;
//            } catch (Exception ex) {
//                result.correct = false;
//                result.errorMasassge = ex.getLocalizedMessage();
//                result.ex = ex;
//            }
//            if (result.correct) {
//                return ResponseEntity.ok().body(result);
//
//            } else {
//                return ResponseEntity.status(HttpStatus.valueOf(400)).body(result);
//            }
//
//        } catch (Exception ex) {
//            return ResponseEntity.status(HttpStatus.valueOf(500)).body(ex.getLocalizedMessage());
//        }
//    }
//
//    @Test
//    public ResponseEntity UpdateDireccinoByUsuario(@PathVariable int idDireccion, @RequestBody UsuarioDireccion usuarioDireccion) {
//        try {
//            if (usuarioDireccion.getDireccion() == null) {
//                usuarioDireccion.setDireccion(new Direccion());
//            }
//            usuarioDireccion.getDireccion().setIdDireccion(idDireccion);
//            Result result = DireccionJPADAOImplementation.Update(usuarioDireccion);
////            Result result = new Result();
//            try {
//
//                Direccion direccionJPA = usuarioDireccion.getDireccion();
//                direccionJPA.setActivoDireccion(1);
//
//                direccionJPA.setUsuario(usuarioDireccion.getUsuario());
////                Direccion direccion = IDireccionJPADAORepository.save(direccionJPA);
//
//                result.correct = true;
//            } catch (Exception ex) {
//                result.correct = false;
//                result.errorMasassge = ex.getLocalizedMessage();
//                result.ex = ex;
//            }
//            if (result.correct) {
//                return ResponseEntity.ok().body(result);
//
//            } else {
//                return ResponseEntity.status(HttpStatus.valueOf(400)).body(result);
//            }
//
//        } catch (Exception ex) {
//            return ResponseEntity.status(HttpStatus.valueOf(500)).body(ex.getLocalizedMessage());
//        }
//    }
//
//    @Test
//    public ResponseEntity DeleteUsuarioDireccion(@PathVariable int idUsuario) {
//        try {
//            Result result = UsuarioJPADAOImplementation.Delete(idUsuario);
////            Result result = new Result();
//            try {
//
////                Optional<Usuario> usuarioJPA = IUsuarioJPADAORepository.findById(idUsuario);
////                if (usuarioJPA != null) {
////                    Usuario usuario = usuarioJPA.get();
////                    usuario.setActivoUsuario(0);
////                    Usuario usuarioEliminar = IUsuarioJPADAORepository.save(usuario);
////                    result.correct = true;
////                } else {
////                    result.correct = false;
////                    result.errorMasassge = "No se encontró la dirección con ID: " + idUsuario;
////                }
//            } catch (Exception ex) {
//                result.correct = false;
//                result.errorMasassge = ex.getLocalizedMessage();
//                result.ex = ex;
//            }
//            if (result.correct) {
//                return ResponseEntity.ok().body(result);
//
//            } else {
//                return ResponseEntity.status(HttpStatus.valueOf(400)).body(result);
//            }
//
//        } catch (Exception ex) {
//            return ResponseEntity.status(HttpStatus.valueOf(500)).body(ex.getLocalizedMessage());
//        }
//    }
//
//    @Test
//    public ResponseEntity DeleteDireccionByUsuario(@PathVariable("idDireccion") int idDireccion) {
//        try {
//            Result result = DireccionJPADAOImplementation.Delete(idDireccion);
////            Result result = new Result();
//            try {
//
////                Optional<Direccion> direccionJPA = IDireccionJPADAORepository.findById(idDireccion);
////                if (direccionJPA != null) {
////                    Direccion direccion = direccionJPA.get();
////                    direccion.setActivoDireccion(0);
////                    Direccion direccionEliminar = IDireccionJPADAORepository.save(direccion);
////                    result.correct = true;
////                } else {
////                    result.correct = false;
////                    result.errorMasassge = "No se encontró la dirección con ID: " + idDireccion;
////                }
//            } catch (Exception ex) {
//                result.correct = false;
//                result.errorMasassge = ex.getLocalizedMessage();
//                result.ex = ex;
//            }
//            if (result.correct) {
//                return ResponseEntity.ok().body(result);
//
//            } else {
//                return ResponseEntity.status(HttpStatus.valueOf(400)).body(result);
//            }
//
//        } catch (Exception ex) {
//            return ResponseEntity.status(HttpStatus.valueOf(500)).body(ex.getLocalizedMessage());
//        }
//    }
//
//    @Test
//    public ResponseEntity GetAllRoll() {
//        try {
//            Result result = RollJPADAOImplementation.GetAllRoll();
////            Result result = new Result();
//            result.objects = new ArrayList<>();
////            List<Roll> rolles = IRollJPADAORepository.findAllByOrderByIdRollAsc();
////            List<Roll> rolles = IRollJPADAORepository.findAll();
////            for (Roll roll : rolles) {
////
////                result.objects.add(roll);
////
////            }
//            result.correct = true;
//
//            if (result.correct) {
//                if (result.objects.size() == 0) {
//                    return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Sin información");
//                } else {
//                    return ResponseEntity.ok().body(result);
//                }
//
//            } else {
//                return ResponseEntity.status(HttpStatus.valueOf(400)).body(result);
//            }
//
//        } catch (Exception ex) {
//            return ResponseEntity.status(HttpStatus.valueOf(500)).body(ex.getLocalizedMessage());
//        }
//    }
//
//    @Test
//    public ResponseEntity GetAllPais() {
//        try {
//            Result result = PaisJPADAOImplementation.GetAllPais();
////            Result result = new Result();
//            result.objects = new ArrayList<>();
////            List<Pais> paises = IPaisJPADAORepository.findAllByOrderByIdPaisAsc();
//////            List<Pais> paises = IPaisJPADAORepository.findAll();
////            for (Pais pais : paises) {
////
////                result.objects.add(pais);
////
////            }
//            result.correct = true;
//            if (result.correct) {
//                if (result.objects.size() == 0) {
//                    return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Sin información");
//                } else {
//                    return ResponseEntity.ok().body(result);
//                }
//
//            } else {
//                return ResponseEntity.status(HttpStatus.valueOf(400)).body(result);
//            }
//
//        } catch (Exception ex) {
//            return ResponseEntity.status(HttpStatus.valueOf(500)).body(ex.getLocalizedMessage());
//        }
//    }
//
//    @Test
//    public ResponseEntity EstadoGetById(@PathVariable int idPais) {
//        try {
//            Result result = EstadoJPADAOImplementation.GetByIdEstados(idPais);
////            Result result = new Result();
//            result.objects = new ArrayList<>();
////            List<Estado> estados = IEstadoJPADAORepository.findByPais_IdPais(idPais);
////            for (Estado estado : estados) {
////
////                result.objects.add(estado);
////
////            }
//            result.correct = true;
//            if (result.correct) {
//                if (result.objects.size() == 0) {
//                    return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Sin información");
//                } else {
//                    return ResponseEntity.ok().body(result);
//                }
//
//            } else {
//                return ResponseEntity.status(HttpStatus.valueOf(400)).body(result);
//            }
//
//        } catch (Exception ex) {
//            return ResponseEntity.status(HttpStatus.valueOf(500)).body(ex.getLocalizedMessage());
//        }
//    }
//
//    @Test
//    public ResponseEntity MunicipioGetById(@PathVariable int idEstado) {
//        try {
//            Result result = MunicipioJPADAOImplementation.GetByIdMunicipios(idEstado);
////            Result result = new Result();
//            result.objects = new ArrayList<>();
////            List<Municipio> municipios = IMunicipioJPADAORepository.findByEstado_IdEstado(idEstado);
////            for (Municipio municipio : municipios) {
////
////                result.objects.add(municipio);
////
////            }
//            result.correct = true;
//            if (result.correct) {
//                if (result.objects.size() == 0) {
//                    return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Sin información");
//                } else {
//                    return ResponseEntity.ok().body(result);
//                }
//
//            } else {
//                return ResponseEntity.status(HttpStatus.valueOf(400)).body(result);
//            }
//
//        } catch (Exception ex) {
//            return ResponseEntity.status(HttpStatus.valueOf(500)).body(ex.getLocalizedMessage());
//        }
//    }
//
//    @Test
//    public ResponseEntity ColoniaGetById(@PathVariable int idMunicipio) {
//        try {
//            Result result = ColoniaJPADAOImplementation.GetByIdColonias(idMunicipio);
////            Result result = new Result();
//            result.objects = new ArrayList<>();
////            List<Colonia> colonias = IColoniaJPADAORepository.findByMunicipio_IdMunicipio(idMunicipio);
////            for (Colonia colonia : colonias) {
////
////                result.objects.add(colonia);
////
////            }
//            result.correct = true;
//            if (result.correct) {
//                if (result.objects.size() == 0) {
//                    return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Sin información");
//                } else {
//                    return ResponseEntity.ok().body(result);
//                }
//
//            } else {
//                return ResponseEntity.status(HttpStatus.valueOf(400)).body(result);
//            }
//
//        } catch (Exception ex) {
//            return ResponseEntity.status(HttpStatus.valueOf(500)).body(ex.getLocalizedMessage());
//        }
//    }
//
//    @Test
//    public ResponseEntity EstatusUsuario(@RequestParam int IdUsuario, @RequestParam int ActivoUsuario) {
//        try {
//            Result result = UsuarioJPADAOImplementation.UpdateActivo(IdUsuario, ActivoUsuario);
////            Result result = new Result();
//            try {
//
////                Optional<Usuario> usuarioJPA = IUsuarioJPADAORepository.findById(IdUsuario);
////                if (usuarioJPA != null) {
////                    Usuario usuario = usuarioJPA.get();
////                    usuario.setActivoUsuario(ActivoUsuario);
////                    Usuario usuarioEliminar = IUsuarioJPADAORepository.save(usuario);
////                    result.correct = true;
////                } else {
////                    result.correct = false;
////                    result.errorMasassge = "No se encontró la dirección con ID: " + IdUsuario;
////                }
//            } catch (Exception ex) {
//                result.correct = false;
//                result.errorMasassge = ex.getLocalizedMessage();
//                result.ex = ex;
//            }
//            if (result.correct) {
//                return ResponseEntity.ok().body(result);
//
//            } else {
//                return ResponseEntity.status(HttpStatus.valueOf(400)).body(result);
//            }
//
//        } catch (Exception ex) {
//            return ResponseEntity.status(HttpStatus.valueOf(500)).body(ex.getLocalizedMessage());
//        }
//    }
//
//    @Test
//    public ResponseEntity UsuarioBusqueda(@RequestBody UsuarioDireccion usuarioBusqueda) {
//        try {
//            Result result = UsuarioJPADAOImplementation.UsuarioBusqueda(usuarioBusqueda);
//            if (result.correct) {
//                if (result.objects.size() == 0) {
//                    return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Sin información");
//                } else {
//                    return ResponseEntity.ok().body(result);
//                }
//
//            } else {
//                return ResponseEntity.status(HttpStatus.valueOf(400)).body(result);
//            }
//
//        } catch (Exception ex) {
//            return ResponseEntity.status(HttpStatus.valueOf(500)).body(ex.getLocalizedMessage());
//        }
//    }

}

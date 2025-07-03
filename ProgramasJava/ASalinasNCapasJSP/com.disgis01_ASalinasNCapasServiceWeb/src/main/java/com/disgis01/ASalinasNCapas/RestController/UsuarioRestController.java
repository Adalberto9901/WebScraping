/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.disgis01.ASalinasNCapas.RestController;

import com.disgis01.ASalinasNCapas.DAO.ColoniaJPADAOImplementation;
import com.disgis01.ASalinasNCapas.DAO.DireccionJPADAOImplementation;
import com.disgis01.ASalinasNCapas.DAO.EstadoJPADAOImplementation;
import com.disgis01.ASalinasNCapas.DAO.MunicipioJPADAOImplementation;
import com.disgis01.ASalinasNCapas.DAO.PaisJPADAOImplementation;
import com.disgis01.ASalinasNCapas.DAO.RollJPADAOImplementation;
import com.disgis01.ASalinasNCapas.DAO.UsuarioJPADAOImplementation;
import com.disgis01.ASalinasNCapas.JPA.Direccion;
import com.disgis01.ASalinasNCapas.JPA.Result;
import com.disgis01.ASalinasNCapas.JPA.Usuario;
import com.disgis01.ASalinasNCapas.JPA.UsuarioDireccion;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
@RestController
@RequestMapping("/usuarioapi")
public class UsuarioRestController {

    @Autowired
    private UsuarioJPADAOImplementation UsuarioJPADAOImplementation;
    @Autowired
    private DireccionJPADAOImplementation DireccionJPADAOImplementation;
    @Autowired
    private RollJPADAOImplementation RollJPADAOImplementation;
    @Autowired
    private PaisJPADAOImplementation PaisJPADAOImplementation;
    @Autowired
    private EstadoJPADAOImplementation EstadoJPADAOImplementation;
    @Autowired
    private MunicipioJPADAOImplementation MunicipioJPADAOImplementation;
    @Autowired
    private ColoniaJPADAOImplementation ColoniaJPADAOImplementation;

    @GetMapping
    public ResponseEntity GetAll() {
        try {
            Result result = UsuarioJPADAOImplementation.GetAll();
            if (result.correct) {
                if (result.objects.size() == 0) {
                    return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Sin información");
                } else {
                    return ResponseEntity.ok().body(result);
                }

            } else {
                return ResponseEntity.status(HttpStatus.valueOf(400)).body(result);
            }

        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.valueOf(500)).body(ex.getLocalizedMessage());
        }
    }

    @GetMapping("{idUsuario}")
    public ResponseEntity GetById(@PathVariable int idUsuario) {
        try {
            Result result = UsuarioJPADAOImplementation.GetById(idUsuario);
            if (result.correct) {
                    return ResponseEntity.ok().body(result);
                
            } else {
                return ResponseEntity.status(HttpStatus.valueOf(400)).body(result);
            }

        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.valueOf(500)).body(ex.getLocalizedMessage());
        }
    }

    @GetMapping("soloUsuario/{idUsuario}")
    public ResponseEntity UsuarioGetSolo(@PathVariable int idUsuario) {
        try {
            Result result = UsuarioJPADAOImplementation.UsuarioGetSolo(idUsuario);
            if (result.correct) {
                    return ResponseEntity.ok().body(result);
                
            } else {
                return ResponseEntity.status(HttpStatus.valueOf(400)).body(result);
            }

        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.valueOf(500)).body(ex.getLocalizedMessage());
        }
    }
    
    @GetMapping("direccion/{idDireccion}")
    public ResponseEntity DireccionGetById(@PathVariable int idDireccion) {
        try {
            Result result = DireccionJPADAOImplementation.DireccionGetById(idDireccion);
            if (result.correct) {
                    return ResponseEntity.ok().body(result);

            } else {
                return ResponseEntity.status(HttpStatus.valueOf(400)).body(result);
            }

        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.valueOf(500)).body(ex.getLocalizedMessage());
        }
    }

    @PostMapping
    public ResponseEntity AddUsuarioDireccion(@RequestBody UsuarioDireccion usuarioDireccion) {
        try {
            Result result = UsuarioJPADAOImplementation.Add(usuarioDireccion);
            if (result.correct) {
                return ResponseEntity.ok().body(result);

            } else {
                return ResponseEntity.status(HttpStatus.valueOf(400)).body(result);
            }

        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.valueOf(500)).body(ex.getLocalizedMessage());
        }
    }

    @PostMapping("direccion/{idUsuario}")
    public ResponseEntity AddDireccionByUsuario(@PathVariable int idUsuario, @RequestBody UsuarioDireccion usuarioDireccion) {
        try {
            if (usuarioDireccion.getUsuario() == null) {
                usuarioDireccion.setUsuario(new Usuario());
            }
            usuarioDireccion.getUsuario().setIdUsuario(idUsuario);

            Result result = DireccionJPADAOImplementation.Add(usuarioDireccion);
            if (result.correct) {
                return ResponseEntity.ok().body(result);

            } else {
                return ResponseEntity.status(HttpStatus.valueOf(400)).body(result);
            }

        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.valueOf(500)).body(ex.getLocalizedMessage());
        }
    }

    @PutMapping("{idUsuario}")
    public ResponseEntity UpdateUsuario(@PathVariable int idUsuario, @RequestBody UsuarioDireccion usuarioDireccion) {
        try {
            if (usuarioDireccion.getUsuario() == null) {
                usuarioDireccion.setUsuario(new Usuario());
            }
            usuarioDireccion.getUsuario().setIdUsuario(idUsuario);
            Result result = UsuarioJPADAOImplementation.Update(usuarioDireccion);
            if (result.correct) {
                    return ResponseEntity.ok().body(result);

            } else {
                return ResponseEntity.status(HttpStatus.valueOf(400)).body(result);
            }

        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.valueOf(500)).body(ex.getLocalizedMessage());
        }
    }

    @PutMapping("direccion/{idDireccion}")
    public ResponseEntity UpdateDireccinoByUsuario(@PathVariable int idDireccion, @RequestBody UsuarioDireccion usuarioDireccion) {
        try {
            if (usuarioDireccion.getDireccion() == null) {
                usuarioDireccion.setDireccion(new Direccion());
            }
            usuarioDireccion.getDireccion().setIdDireccion(idDireccion);
            Result result = DireccionJPADAOImplementation.Update(usuarioDireccion);
            if (result.correct) {
                    return ResponseEntity.ok().body(result);
                
            } else {
                return ResponseEntity.status(HttpStatus.valueOf(400)).body(result);
            }

        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.valueOf(500)).body(ex.getLocalizedMessage());
        }
    }

    @DeleteMapping("{idUsuario}")
    public ResponseEntity DeleteUsuarioDireccion(@PathVariable int idUsuario) {
        try {
            Result result = UsuarioJPADAOImplementation.Delete(idUsuario);
            if (result.correct) {
                    return ResponseEntity.ok().body(result);
          
            } else {
                return ResponseEntity.status(HttpStatus.valueOf(400)).body(result);
            }

        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.valueOf(500)).body(ex.getLocalizedMessage());
        }
    }

    @DeleteMapping("direccion/{idDireccion}")
    public ResponseEntity DeleteDireccionByUsuario(@PathVariable("idDireccion") int idDireccion) {
        try {
            Result result = DireccionJPADAOImplementation.Delete(idDireccion);
            if (result.correct) {
                    return ResponseEntity.ok().body(result);

            } else {
                return ResponseEntity.status(HttpStatus.valueOf(400)).body(result);
            }

        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.valueOf(500)).body(ex.getLocalizedMessage());
        }
    }
    
    @GetMapping("roll")
    public ResponseEntity GetAllRoll() {
        try {
            Result result = RollJPADAOImplementation.GetAllRoll();
            if (result.correct) {
                if (result.objects.size() == 0) {
                    return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Sin información");
                } else {
                    return ResponseEntity.ok().body(result);
                }

            } else {
                return ResponseEntity.status(HttpStatus.valueOf(400)).body(result);
            }

        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.valueOf(500)).body(ex.getLocalizedMessage());
        }
    }
    
    @GetMapping("pais")
    public ResponseEntity GetAllPais() {
        try {
            Result result = PaisJPADAOImplementation.GetAllPais();
            if (result.correct) {
                if (result.objects.size() == 0) {
                    return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Sin información");
                } else {
                    return ResponseEntity.ok().body(result);
                }

            } else {
                return ResponseEntity.status(HttpStatus.valueOf(400)).body(result);
            }

        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.valueOf(500)).body(ex.getLocalizedMessage());
        }
    }
    
    @GetMapping("estado/{idPais}")
    public ResponseEntity EstadoGetById(@PathVariable int idPais) {
        try {
            Result result = EstadoJPADAOImplementation.GetByIdEstados(idPais);
            if (result.correct) {
                if (result.objects.size() == 0) {
                    return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Sin información");
                } else {
                    return ResponseEntity.ok().body(result);
                }

            } else {
                return ResponseEntity.status(HttpStatus.valueOf(400)).body(result);
            }

        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.valueOf(500)).body(ex.getLocalizedMessage());
        }
    }
    
    @GetMapping("municipio/{idEstado}")
    public ResponseEntity MunicipioGetById(@PathVariable int idEstado) {
        try {
            Result result = MunicipioJPADAOImplementation.GetByIdMunicipios(idEstado);
            if (result.correct) {
                if (result.objects.size() == 0) {
                    return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Sin información");
                } else {
                    return ResponseEntity.ok().body(result);
                }

            } else {
                return ResponseEntity.status(HttpStatus.valueOf(400)).body(result);
            }

        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.valueOf(500)).body(ex.getLocalizedMessage());
        }
    }
    
    @GetMapping("colonia/{idMunicipio}")
    public ResponseEntity ColoniaGetById(@PathVariable int idMunicipio) {
        try {
            Result result = ColoniaJPADAOImplementation.GetByIdColonias(idMunicipio);
            if (result.correct) {
                if (result.objects.size() == 0) {
                    return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Sin información");
                } else {
                    return ResponseEntity.ok().body(result);
                }

            } else {
                return ResponseEntity.status(HttpStatus.valueOf(400)).body(result);
            }

        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.valueOf(500)).body(ex.getLocalizedMessage());
        }
    }
    
    @GetMapping("estatus")
    public ResponseEntity EstatusUsuario(@RequestParam int IdUsuario, @RequestParam int ActivoUsuario) {
        try {
            Result result = UsuarioJPADAOImplementation.UpdateActivo(IdUsuario, ActivoUsuario);
            if (result.correct) {
                    return ResponseEntity.ok().body(result);

            } else {
                return ResponseEntity.status(HttpStatus.valueOf(400)).body(result);
            }

        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.valueOf(500)).body(ex.getLocalizedMessage());
        }
    }
    
    @PostMapping("busqueda")
    public ResponseEntity UsuarioBusqueda(@RequestBody UsuarioDireccion usuarioBusqueda) {
        try {
            Result result = UsuarioJPADAOImplementation.UsuarioBusqueda(usuarioBusqueda);
            if (result.correct) {
               if (result.objects.size() == 0) {
                    return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Sin información");
                } else {
                    return ResponseEntity.ok().body(result);
                }

            } else {
                return ResponseEntity.status(HttpStatus.valueOf(400)).body(result);
            }

        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.valueOf(500)).body(ex.getLocalizedMessage());
        }
    }
}

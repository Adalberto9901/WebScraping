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
import com.disgis01.ASalinasNCapas.DAO.ResultValidarDatos;
import com.disgis01.ASalinasNCapas.DAO.RollJPADAOImplementation;
import com.disgis01.ASalinasNCapas.DAO.UsuarioJPADAOImplementation;
import com.disgis01.ASalinasNCapas.JPA.Colonia;
import com.disgis01.ASalinasNCapas.JPA.Direccion;
import com.disgis01.ASalinasNCapas.JPA.Result;
import com.disgis01.ASalinasNCapas.JPA.Roll;
import com.disgis01.ASalinasNCapas.JPA.Usuario;
import com.disgis01.ASalinasNCapas.JPA.UsuarioDireccion;
import jakarta.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
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
import javax.crypto.CipherOutputStream;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
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
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

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

    @PostMapping("cargaMasiva")
    public ResponseEntity cargaMasiva(@RequestParam("archivo") MultipartFile archivo) throws IOException, NoSuchAlgorithmException, Exception {

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
                Result result = new Result();
                result.correct = true;
//                CrearArchivoLog(new File(absolutePath));
                SecretKey clave = generarClaveAES();
                String datosOriginales = CrearArchivoLog(new File(absolutePath));
                final String claveEncriptacion = "¡secreto!";
                String encriptado = encriptar(datosOriginales, claveEncriptacion);
                result.errorMasassge = encriptado;
                return ResponseEntity.ok().body(result);
            } else {
                return ResponseEntity.status(HttpStatus.valueOf(400)).body(listaErrores);
            }
        }
        return new ResponseEntity<>("El archivo está vacío", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("cargaMasiva/Procesar")
    public ResponseEntity AddUsuarioDireccionMasiva(@RequestParam("encriptado") String encriptado) throws IOException, NoSuchAlgorithmException, Exception {
        final String claveEncriptacion = "¡secreto!";
        String desencriptado = desencriptar(encriptado, claveEncriptacion);
        try (BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\digis\\OneDrive\\Documentos\\Adalberto Salinas Jose\\ProgramasJava\\ASalinasNCapasJSP\\com.disgis01_ASalinasNCapasServiceWeb\\" + desencriptado))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split("\\|");
                if (datos[1].equals("true") && datos[3].equals( "el documento esta listo para usar una vez") ) {
                    String archivo = datos[0];
                    if (archivo != null && !archivo.isEmpty()) {
                        String fileExtention = archivo.split("\\.")[1];

                        List<UsuarioDireccion> usuariosDireccion = new ArrayList<>();

                        if (fileExtention.equals("txt")) {
                            MultipartFile multipartFile = convertFileToMultipartFile(new File(archivo));
                            usuariosDireccion = LecturaArchivoTXT(multipartFile);
                        } else { //"xlsx"
                            usuariosDireccion = LecturaArchivoXLSX(new File(archivo));
                        }

                        List<ResultValidarDatos> listaErrores = resultValidarDatos(usuariosDireccion);
                        if (listaErrores.isEmpty()) {
                            Result result = UsuarioJPADAOImplementation.Add(usuariosDireccion);
                            if (result.correct) {
                                String comentario = "el documento subido correctamente";
                                FileWriter writer = new FileWriter(archivo, true);
                                BufferedWriter bufferedWriter = new BufferedWriter(writer);
                                bufferedWriter.write(datos[0] + "|false|" + datos[2] + "|" + comentario);
                                bufferedWriter.close();
                                writer.close();
                                return ResponseEntity.ok().body(result);

                            } else {
                                return ResponseEntity.status(HttpStatus.valueOf(400)).body(result);
                            }
                        } else {
                            return ResponseEntity.status(HttpStatus.valueOf(400)).body(listaErrores);
                        }
                    }
                } else {
                    String archivo = datos[0];
                    String comentario = "contienen un error el documento";
                    FileWriter writer = new FileWriter(archivo, true);
                    BufferedWriter bufferedWriter = new BufferedWriter(writer);
                    bufferedWriter.write(datos[0] + "|false|" + datos[2] + "|" + comentario);
                    bufferedWriter.close();
                    writer.close();

                    return (ResponseEntity) ResponseEntity.status(HttpStatus.valueOf(400));
                }
            }//termina el while
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("El archivo está vacío", HttpStatus.BAD_REQUEST);
    }

    public List<UsuarioDireccion> LecturaArchivoTXT(MultipartFile archivo) {
        List<UsuarioDireccion> usuariosDireccion = new ArrayList<>();

        try (InputStream inputStream = archivo.getInputStream(); BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));) {
            bufferedReader.readLine();
            String linea = "";
            while ((linea = bufferedReader.readLine()) != null) {
                String[] datos = linea.split("\\|");

                UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
                usuarioDireccion.Usuario = new Usuario();

                usuarioDireccion.Usuario.setNombreUsuario(datos[0]);
                usuarioDireccion.Usuario.setApellidoPatUsuario(datos[1]);
                usuarioDireccion.Usuario.setApellidoMatUsuario(datos[2]);
                usuarioDireccion.Usuario.setFechaNacimeintoUsuario(new SimpleDateFormat("yyyy-MM-dd").parse(datos[3]));
                usuarioDireccion.Usuario.setSexoUsuario(datos[4]);
                usuarioDireccion.Usuario.setCorreoUsuario(datos[5]);
                usuarioDireccion.Usuario.setCelularUsuario(datos[6]);
                usuarioDireccion.Usuario.setPasswordUsuario(datos[7]);
                usuarioDireccion.Usuario.setTelefonoUsuario(datos[8]);
                usuarioDireccion.Usuario.setUserNombreUsuario(datos[9]);

                usuarioDireccion.Usuario.Roll = new Roll();
                usuarioDireccion.Usuario.Roll.setIdRoll(Integer.parseInt(datos[10]));

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
                usuarioDireccion.Usuario = new Usuario();

                usuarioDireccion.Usuario.setNombreUsuario(row.getCell(0) != null ? row.getCell(0).toString() : "");
                usuarioDireccion.Usuario.setApellidoPatUsuario(row.getCell(1) != null ? row.getCell(1).toString() : "");
                usuarioDireccion.Usuario.setApellidoMatUsuario(row.getCell(2) != null ? row.getCell(2).toString() : "");
                usuarioDireccion.Usuario.setFechaNacimeintoUsuario(row.getCell(3).getDateCellValue());
                usuarioDireccion.Usuario.setSexoUsuario(row.getCell(4) != null ? row.getCell(4).toString() : "");
                usuarioDireccion.Usuario.setCorreoUsuario(row.getCell(5) != null ? row.getCell(5).toString() : "");
                usuarioDireccion.Usuario.setCelularUsuario(row.getCell(6) != null ? row.getCell(6).toString() : "");
                usuarioDireccion.Usuario.setPasswordUsuario(row.getCell(7) != null ? row.getCell(7).toString() : "");
                usuarioDireccion.Usuario.setTelefonoUsuario(row.getCell(8) != null ? row.getCell(8).toString() : "");
                usuarioDireccion.Usuario.setCURPUsuario(row.getCell(9) != null ? row.getCell(9).toString() : "");
                usuarioDireccion.Usuario.setUserNombreUsuario(row.getCell(10) != null ? row.getCell(10).toString() : "");

                usuarioDireccion.Usuario.Roll = new Roll();
                usuarioDireccion.Usuario.Roll.setIdRoll((int) row.getCell(11).getNumericCellValue());

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
                if (usuarioDireccion.Usuario.getNombreUsuario() == null || usuarioDireccion.Usuario.getNombreUsuario().equals("")) {
                    listaErrores.add(new ResultValidarDatos(fila, usuarioDireccion.Usuario.getNombreUsuario(), "Campo Obligatorio"));
                }
                if (usuarioDireccion.Usuario.getApellidoPatUsuario() == null || usuarioDireccion.Usuario.getApellidoPatUsuario().equals("")) {
                    listaErrores.add(new ResultValidarDatos(fila, usuarioDireccion.Usuario.getApellidoPatUsuario(), "Campo Obligatorio"));
                }
                if (usuarioDireccion.Usuario.getApellidoMatUsuario() == null || usuarioDireccion.Usuario.getApellidoMatUsuario().equals("")) {
                    listaErrores.add(new ResultValidarDatos(fila, usuarioDireccion.Usuario.getApellidoMatUsuario(), "Campo Obligatorio"));
                }
                if (usuarioDireccion.Usuario.getFechaNacimeintoUsuario() == null || new SimpleDateFormat("yyyy-MM-dd").format(usuarioDireccion.Usuario.getFechaNacimeintoUsuario()).equals("")) {
                    listaErrores.add(new ResultValidarDatos(fila, new SimpleDateFormat("yyyy-MM-dd").format(usuarioDireccion.Usuario.getFechaNacimeintoUsuario()), "Campo Obligatorio"));
                }
                if (usuarioDireccion.Usuario.getSexoUsuario() == null || usuarioDireccion.Usuario.getSexoUsuario().equals("")) {
                    listaErrores.add(new ResultValidarDatos(fila, usuarioDireccion.Usuario.getSexoUsuario(), "Campo Obligatorio"));
                }
                if (usuarioDireccion.Usuario.getCorreoUsuario() == null || usuarioDireccion.Usuario.getCorreoUsuario().equals("")) {
                    listaErrores.add(new ResultValidarDatos(fila, usuarioDireccion.Usuario.getCorreoUsuario(), "Campo Obligatorio"));
                }
                if (usuarioDireccion.Usuario.getCelularUsuario() == null || usuarioDireccion.Usuario.getCelularUsuario().equals("")) {
                    listaErrores.add(new ResultValidarDatos(fila, usuarioDireccion.Usuario.getCelularUsuario(), "Campo Obligatorio"));
                }
                if (usuarioDireccion.Usuario.getPasswordUsuario() == null || usuarioDireccion.Usuario.getPasswordUsuario().equals("")) {
                    listaErrores.add(new ResultValidarDatos(fila, usuarioDireccion.Usuario.getPasswordUsuario(), "Campo Obligatorio"));
                }
                if (usuarioDireccion.Usuario.getTelefonoUsuario() == null || usuarioDireccion.Usuario.getTelefonoUsuario().equals("")) {
                    listaErrores.add(new ResultValidarDatos(fila, usuarioDireccion.Usuario.getTelefonoUsuario(), "Campo Obligatorio"));
                }
                if (usuarioDireccion.Usuario.getUserNombreUsuario() == null || usuarioDireccion.Usuario.getUserNombreUsuario().equals("")) {
                    listaErrores.add(new ResultValidarDatos(fila, usuarioDireccion.Usuario.getUserNombreUsuario(), "Campo Obligatorio"));
                }
                if (usuarioDireccion.Usuario.Roll.getIdRoll() == -1 || Integer.toString(usuarioDireccion.Usuario.Roll.getIdRoll()).equals("")) {
                    listaErrores.add(new ResultValidarDatos(fila, Integer.toString(usuarioDireccion.Usuario.Roll.getIdRoll()), "Campo Obligatorio"));
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

    public static String CrearArchivoLog(File absolutePath) {
        String nombrefecha = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String nombreArchivo = "ruta_archivo" + nombrefecha;
        String rutaArchivoTxt = "src/main/resources/Archivos/" + nombreArchivo + ".txt";
        File rutaArchivoAEscribir = absolutePath;
        String fecha = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String comentario = "el documento esta listo para usar una vez";
        try {
            File archivo = new File(rutaArchivoTxt);
            if (archivo.createNewFile()) {
                System.out.println("Archivo creado: " + archivo.getName());
                FileWriter writer = new FileWriter(archivo);
                writer.write(rutaArchivoAEscribir.toString() + "|true|" + fecha + "|" + comentario);
                writer.close();
                System.out.println("Ruta guardada en el archivo.");
            } else {
                System.out.println("El archivo ya existe.");
            }

        } catch (IOException e) {
            System.out.println("Ocurrió un error: " + e.getMessage());
            e.printStackTrace();
        }
        return rutaArchivoTxt;
    }

    public static SecretKey generarClaveAES() throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(256); // 128, 192 o 256 bits
        return keyGenerator.generateKey();
    }

    private SecretKeySpec crearClave(String clave) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        byte[] claveEncriptacion = clave.getBytes("UTF-8");
        MessageDigest sha = MessageDigest.getInstance("SHA-1");
        claveEncriptacion = sha.digest(claveEncriptacion);
        claveEncriptacion = Arrays.copyOf(claveEncriptacion, 16);
        SecretKeySpec secretKey = new SecretKeySpec(claveEncriptacion, "AES");

        return secretKey;
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

    public String desencriptar(String datosEncriptados, String claveSecreta) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
        SecretKeySpec secretKey = this.crearClave(claveSecreta);

        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);

        byte[] bytesEncriptados = Base64.getDecoder().decode(datosEncriptados);
        byte[] datosDesencriptados = cipher.doFinal(bytesEncriptados);
        String datos = new String(datosDesencriptados);

        return datos;
    }
}

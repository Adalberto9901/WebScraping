/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.disgis01.ASalinasNCapas.JPA;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author Alien 1
 */
@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idusuario")
//    private int id;
    private int idUsuario;
    
    @Column(name = "nombreusuario")
    private String NombreUsuario;
    
    @Column(name = "apellidopatusuario")
    private String ApellidoPatUsuario;

//    @Size(min = 3, max = 35, message = "Nombre entre 3 y 35")
//    @NotEmpty(message = "Ingresa dato :@")
    @Column(name = "apellidomatusuario")
    private String ApellidoMatUsuario;

    @DateTimeFormat(pattern = "yyyy-MM-dd") //este no es el formaro el formato de sql es yyyy-MM-dd
//    @NotEmpty(message = "Ingresa dato :@")
    @Column(name = "fechanacimeintousuario")
    private Date FechaNacimeintoUsuario;

//    @NotEmpty(message = "Ingresa dato :@")
    @Column(name = "sexousuario")
    private String SexoUsuario;

//    @Pattern(regexp = "[a-zA-Z0-9_]+([.][a-zA-Z0-9_]+)*@[a-zA-Z0-9_]+([.][a-zA-Z0-9_]+)*[.][a-zA-Z]{2,5}", message = "correo invalido regex")
    @Column(name = "correousuario")
    private String CorreoUsuario;

//    @Size(min = 10, max = 20, message = "Nombre entre 10 y 20")
//    @NotEmpty(message = "Ingresa dato :@")
//    @Pattern(regexp = "[0-9]+\\-[0-9]+\\-[0-9]+", message = "celular invalido regex")
    @Column(name = "celularusuario")
    private String CelularUsuario;

//    @Size(min = 3, max = 50, message = "Nombre entre 3 y 50")
//    @NotEmpty(message = "Ingresa dato :@")
//    @Pattern(regexp = "[a-zA-Z0-9_]+[#$!%&|°¬]+", message = "contraseña invalida regex")
    @Column(name = "passwordusuario")
    private String PasswordUsuario;

//    @Size(min = 3, max = 20, message = "Nombre entre 3 y 20")
//    @NotEmpty(message = "Ingresa dato :@")
//    @Pattern(regexp = "[0-9]+\\-[0-9]+\\-[0-9]+", message = "Telefono invalido regex")
    @Column(name = "telefonousuario")
    private String TelefonoUsuario;

//    @Size(min = 10, max = 18, message = "Nombre entre 10 y 18")
//    @NotEmpty(message = "Ingresa dato :@")
    @Column(name = "curpusuario")
    private String CURPUsuario;
//
//    @Size(min = 3, max = 20, message = "Nombre entre 3 y 20")
//    @NotEmpty(message = "Ingresa dato :@")
    @Column(name = "usernameusuario")
    private String UserNombreUsuario;
    
    @Lob
    @Column(name = "imagen")
    private String Imagen;
    
    @Column(name = "activousuario")
    private int activoUsuario;

    @JoinColumn(name = "idroll")
    @ManyToOne
    public Roll roll; //=> propiedad de navegacion

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int IdUsuario) {
        this.idUsuario = IdUsuario;
    }

//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
    

    public String getNombreUsuario() {
        return NombreUsuario;
    }

    public void setNombreUsuario(String NombreUsuario) {
        this.NombreUsuario = NombreUsuario;
    }

    public String getApellidoPatUsuario() {
        return ApellidoPatUsuario;
    }

    public void setApellidoPatUsuario(String ApellidoPatUsuario) {
        this.ApellidoPatUsuario = ApellidoPatUsuario;
    }

    public String getApellidoMatUsuario() {
        return ApellidoMatUsuario;
    }

    public void setApellidoMatUsuario(String ApellidoMatUsuario) {
        this.ApellidoMatUsuario = ApellidoMatUsuario;
    }

    public Date getFechaNacimeintoUsuario() {
        return FechaNacimeintoUsuario;
    }

    public void setFechaNacimeintoUsuario(Date FechaNacimeintoUsuario) {
        this.FechaNacimeintoUsuario = FechaNacimeintoUsuario;
    }

    public String getSexoUsuario() {
        return SexoUsuario;
    }

    public void setSexoUsuario(String SexoUsuario) {
        this.SexoUsuario = SexoUsuario;
    }

    public String getCorreoUsuario() {
        return CorreoUsuario;
    }

    public void setCorreoUsuario(String CorreoUsuario) {
        this.CorreoUsuario = CorreoUsuario;
    }

    public String getCelularUsuario() {
        return CelularUsuario;
    }

    public void setCelularUsuario(String CelularUsuario) {
        this.CelularUsuario = CelularUsuario;
    }

    public String getPasswordUsuario() {
        return PasswordUsuario;
    }

    public void setPasswordUsuario(String PasswordUsuario) {
        this.PasswordUsuario = PasswordUsuario;
    }

    public String getTelefonoUsuario() {
        return TelefonoUsuario;
    }

    public void setTelefonoUsuario(String TelefonoUsuario) {
        this.TelefonoUsuario = TelefonoUsuario;
    }

    public String getCURPUsuario() {
        return CURPUsuario;
    }

    public void setCURPUsuario(String CURPUsuario) {
        this.CURPUsuario = CURPUsuario;
    }

    public String getUserNombreUsuario() {
        return UserNombreUsuario;
    }

    public void setUserNombreUsuario(String UserNombreUsuario) {
        this.UserNombreUsuario = UserNombreUsuario;
    }

    public String getImagen() {
        return Imagen;
    }

    public void setImagen(String Imagen) {
        this.Imagen = Imagen;
    }

    public int getActivoUsuario() {
        return activoUsuario;
    }

    public void setActivoUsuario(int ActivoUsuario) {
        this.activoUsuario = ActivoUsuario;
    }

}

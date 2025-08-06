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
    private int idUsuario;

    @Column(name = "nombreusuario")
    private String NombreUsuario;

    @Column(name = "apellidopatusuario")
    private String ApellidoPatUsuario;

    @Column(name = "apellidomatusuario")
    private String ApellidoMatUsuario;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "fechanacimeintousuario")
    private Date FechaNacimeintoUsuario;

    @Column(name = "sexousuario")
    private String SexoUsuario;

    @Column(name = "correousuario")
    private String CorreoUsuario;

    @Column(name = "celularusuario")
    private String CelularUsuario;

    @Column(name = "passwordusuario")
    private String passwordUsuario;

    @Column(name = "telefonousuario")
    private String TelefonoUsuario;

    @Column(name = "curpusuario")
    private String CURPUsuario;

    @Column(name = "usernameusuario")
    private String userNombreUsuario;

    @Lob
    @Column(name = "imagen")
    private String Imagen;

    @Column(name = "activousuario")
    private int activoUsuario;

    @JoinColumn(name = "idroll")
    @ManyToOne
    public Roll Roll;

    @Column(name = "token")
    private String token;

    public Usuario() {
    }

    public Usuario(String token) {
        this.token = token;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int IdUsuario) {
        this.idUsuario = IdUsuario;
    }

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
        return passwordUsuario;
    }

    public void setPasswordUsuario(String PasswordUsuario) {
        this.passwordUsuario = PasswordUsuario;
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
        return userNombreUsuario;
    }

    public void setUserNombreUsuario(String UserNombreUsuario) {
        this.userNombreUsuario = UserNombreUsuario;
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

    public Roll getRoll() {
        return Roll;
    }

    public void setRoll(Roll Roll) {
        this.Roll = Roll;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.disgis01.ASalinasNCapas.ML;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author Alien 1
 */
public class Usuario {

    private int IdUsuario;

    @Size(min = 3, max = 35, message = "Nombre entre 3 y 35")
    @NotEmpty(message = "Ingresa dato :@")
    private String NombreUsuario;

    @Size(min = 3, max = 35, message = "Nombre entre 3 y 35")
    @NotEmpty(message = "Ingresa dato :@")
    private String ApellidoPatUsuario;

    @Size(min = 3, max = 35, message = "Nombre entre 3 y 35")
    @NotEmpty(message = "Ingresa dato :@")
    private String ApellidoMatUsuario;

    @DateTimeFormat(pattern = "yyyy-MM-dd") //este no es el formaro el formato de sql es yyyy-MM-dd
//    @NotEmpty(message = "Ingresa dato :@")
    private Date FechaNacimeintoUsuario;

    @NotEmpty(message = "Ingresa dato :@")
    private String SexoUsuario;

    @Pattern(regexp = "[a-zA-Z0-9_]+([.][a-zA-Z0-9_]+)*@[a-zA-Z0-9_]+([.][a-zA-Z0-9_]+)*[.][a-zA-Z]{2,5}", message = "correo invalido regex")
    private String CorreoUsuario;

    @Size(min = 10, max = 20, message = "Nombre entre 10 y 20")
    @NotEmpty(message = "Ingresa dato :@")
    @Pattern(regexp = "[0-9]+\\-[0-9]+\\-[0-9]+", message = "celular invalido regex")
    private String CelularUsuario;

    @Size(min = 3, max = 50, message = "Nombre entre 3 y 50")
    @NotEmpty(message = "Ingresa dato :@")
    @Pattern(regexp = "[a-zA-Z0-9_]+[#$!%&|°¬]+", message = "contraseña invalida regex")
    private String PasswordUsuario;

    @Size(min = 3, max = 20, message = "Nombre entre 3 y 20")
    @NotEmpty(message = "Ingresa dato :@")
    @Pattern(regexp = "[0-9]+\\-[0-9]+\\-[0-9]+", message = "Telefono invalido regex")
    private String TelefonoUsuario;

//    @Size(min = 10, max = 18, message = "Nombre entre 10 y 18")
//    @NotEmpty(message = "Ingresa dato :@")
    private String CURPUsuario;

    @Size(min = 3, max = 20, message = "Nombre entre 3 y 20")
    @NotEmpty(message = "Ingresa dato :@")
    private String UserNombreUsuario;

    private String Imagen;
    private int ActivoUsuario;

    public Roll Roll; //=> propiedad de navegacion
    //public Direccion Direccion;

    private String token;

    public int getIdUsuario() {
        return IdUsuario;
    }

    public void setIdUsuario(int IdUsuario) {
        this.IdUsuario = IdUsuario;
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

    public String getUserNombreUsuario() {
        return UserNombreUsuario;
    }

    public void setUserNombreUsuario(String UserNombreUsuario) {
        this.UserNombreUsuario = UserNombreUsuario;
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

    public Roll getRoll() {
        return Roll;
    }

    public void setRoll(Roll Roll) {
        this.Roll = Roll;
    }

    public String getImagen() {
        return Imagen;
    }

    public void setImagen(String Imagen) {
        this.Imagen = Imagen;
    }

    public int getActivoUsuario() {
        return ActivoUsuario;
    }

    public void setActivoUsuario(int ActivoUsuario) {
        this.ActivoUsuario = ActivoUsuario;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}

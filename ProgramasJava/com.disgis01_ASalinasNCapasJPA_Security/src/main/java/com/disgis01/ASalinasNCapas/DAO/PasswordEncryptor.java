/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.disgis01.ASalinasNCapas.DAO;

import com.disgis01.ASalinasNCapas.JPA.Usuario;
import jakarta.annotation.PostConstruct;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 *
 * @author Alien 1
 */
@Component
public class PasswordEncryptor {
    
    private final IUsuarioJPADAORepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public PasswordEncryptor(IUsuarioJPADAORepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void encryptPasswordsIfNeeded() {
        List<Usuario> usuarios = usuarioRepository.findAll();

        for (Usuario usuario : usuarios) {
            String plainPassword = usuario.getPasswordUsuario();

            // Solo encripta si no es BCrypt aún
            if (!plainPassword.startsWith("$2a$")) {
                String hashed = passwordEncoder.encode(plainPassword);
                usuario.setPasswordUsuario(hashed);
                usuarioRepository.save(usuario);
            }
        }
        System.out.println("✅ Todas las contraseñas han sido cifradas con BCrypt");
    }
    
}

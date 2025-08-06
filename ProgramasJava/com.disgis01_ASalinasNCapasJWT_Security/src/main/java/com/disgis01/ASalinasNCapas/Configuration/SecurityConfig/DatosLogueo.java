package com.disgis01.ASalinasNCapas.Configuration.SecurityConfig;

import com.disgis01.ASalinasNCapas.DAO.IUsuarioJPADAORepository;
import com.disgis01.ASalinasNCapas.JPA.Usuario;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class DatosLogueo implements UserDetailsService{
    
    @Autowired
    private IUsuarioJPADAORepository usuarioRepository;
    
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByUserNombreUsuario(userName)
            .orElseThrow(() -> new UsernameNotFoundException("El usuario no existe"));

        return new org.springframework.security.core.userdetails.User(
            usuario.getUserNombreUsuario(), 
            usuario.getPasswordUsuario(), 
//            List.of(new SimpleGrantedAuthority("ROLE_" + usuario.getRoll().getNombreRoll()))
            List.of(new SimpleGrantedAuthority(usuario.getRoll().getNombreRoll()))
        );
    }
}

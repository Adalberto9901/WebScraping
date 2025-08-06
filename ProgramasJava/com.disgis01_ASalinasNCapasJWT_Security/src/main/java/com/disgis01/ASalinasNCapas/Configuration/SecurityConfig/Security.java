/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.disgis01.ASalinasNCapas.Configuration.SecurityConfig;

import com.disgis01.ASalinasNCapas.DAO.IUsuarioJPADAORepository;
import com.disgis01.ASalinasNCapas.JPA.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.persistence.EntityManager;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import static org.springframework.security.config.Customizer.withDefaults;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.HeaderWriterLogoutHandler;
import org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter;
import static org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter.Directive.CACHE;
import static org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter.Directive.COOKIES;
import static org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter.Directive.STORAGE;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 *
 * @author Alien 1
 */
@EnableWebSecurity
@Configuration
public class Security {

    private final IUsuarioJPADAORepository iUsuarioJPADAORepository;
    
    @Autowired
    public Security(IUsuarioJPADAORepository iUsuarioJPADAORepository) {
        this.iUsuarioJPADAORepository = iUsuarioJPADAORepository;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            Usuario usuario = iUsuarioJPADAORepository.findAllByUserNombreUsuario(username);

            if (usuario == null) {
                throw new UsernameNotFoundException("Usuario no encontrado: " + username);
            }

            String rol = usuario.getRoll().getNombreRoll();

            return User.builder()
                    .username(usuario.getUserNombreUsuario())
                    .password(usuario.getPasswordUsuario())
                    //                    .roles(rol)
                    .authorities(rol)
                    .build();
        };
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http, PasswordEncoder passwordEncoder) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService()) // Usa el UserDetailsService que definimos
                .passwordEncoder(passwordEncoder) // BCrypt encoder
                .and()
                .build();
    }

    @Bean
    public SecurityFilterChain SecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf().disable()
                .authorizeHttpRequests(
                        configure -> configure
                                //hasAnyRole -> ROLE_Programador 
                                //HasAnyAuthority -> Programador
                                .requestMatchers(HttpMethod.POST, "/rest/login").permitAll()
                                .requestMatchers(HttpMethod.POST, "/usuario/Activo").hasAnyAuthority("Programador")
                                .requestMatchers(HttpMethod.GET, "/usuario/cargaMasiva/**").hasAnyAuthority("Administrador", "Analista", "Programador")
                                .requestMatchers(HttpMethod.POST, "/usuario/cargaMasiva").hasAnyAuthority("Administrador", "Programador")
                                .requestMatchers(HttpMethod.GET, "/usuario/RedireccionarFormulario/**").hasAnyAuthority("Programador", "Analista")
                                .requestMatchers(HttpMethod.POST, "/usuario/updateAddress").hasRole("Programador")
                                .requestMatchers(HttpMethod.POST, "/usuario/updateUser").hasAuthority("Programador")
                                .requestMatchers(HttpMethod.POST, "/usuario/addAddress").hasAuthority("Programador")
                                .requestMatchers(HttpMethod.POST, "/usuario/addUser").hasAuthority("Programador")
                                .requestMatchers(HttpMethod.GET, "/usuario/addUser/**").hasAnyAuthority("Analista", "Programador")
                                .requestMatchers(HttpMethod.POST, "/usuario/index").hasAuthority("Programador")
                                .requestMatchers(HttpMethod.GET, "/usuario/index").hasAnyAuthority("Administrador", "Programador", "Analista", "Cliente", "Gerente")
                                .anyRequest().authenticated()
                )
                .formLogin(
                        form -> form
                                .loginPage("/usuario/login")
                                .defaultSuccessUrl("/usuario/index", true)
                                .failureUrl("/login?error=true")
                                .permitAll()
                )
                .logout(
                        logout -> logout
                                .logoutUrl("/logout")
                                .logoutSuccessUrl("/usuario/login?logout")
                                .invalidateHttpSession(true)
                                .clearAuthentication(true)
                                .deleteCookies("auth_code", "JSESSIONID")
                                .addLogoutHandler(new HeaderWriterLogoutHandler(new ClearSiteDataHeaderWriter(CACHE, COOKIES, STORAGE)))
                                .permitAll()
                )
                .headers(
                        headers -> headers
                                .cacheControl(cache -> cache.disable())
                )
                .exceptionHandling(
                        ex -> ex
                                .accessDeniedPage("/usuario/403")
                )
                .httpBasic(withDefaults()) 
                ;

        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}

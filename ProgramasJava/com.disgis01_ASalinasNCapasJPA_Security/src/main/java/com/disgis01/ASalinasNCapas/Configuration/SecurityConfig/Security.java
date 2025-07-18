/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.disgis01.ASalinasNCapas.Configuration.SecurityConfig;

//import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import static org.springframework.security.config.Customizer.withDefaults;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.CookieClearingLogoutHandler;
import org.springframework.security.web.authentication.logout.HeaderWriterLogoutHandler;
import org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter;
import static org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter.Directive.CACHE;
import static org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter.Directive.COOKIES;
import static org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter.Directive.STORAGE;

/**
 *
 * @author Alien 1
 */
@EnableWebSecurity
@Configuration
public class Security {

    @Bean
    public SecurityFilterChain SecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                //                .headers(headers -> headers.cacheControl(withDefaults()))
                .authorizeHttpRequests(
                        configure -> configure
                                //hasAnyRole -> ROLE_Programador 
                                //HasAnyAuthority -> Programador
                                //                                .requestMatchers(HttpMethod.GET, "/usuario/cargaMasiva/**").hasAnyAuthority("Administrador", "Analista", "Programador")
                                .requestMatchers(HttpMethod.POST, "/usuario/Activo").hasAuthority("Programador")
                                .requestMatchers(HttpMethod.GET, "/usuario/cargaMasiva/**").hasAnyAuthority("Administrador", "Analista", "Programador")
                                .requestMatchers(HttpMethod.POST, "/usuario/cargaMasiva").hasAnyAuthority("Administrador", "Programador")
                                .requestMatchers(HttpMethod.GET, "/usuario/RedireccionarFormulario/**").hasAuthority("Programador")
                                .requestMatchers(HttpMethod.POST, "/usuario/updateAddress").hasAuthority("Programador")
                                .requestMatchers(HttpMethod.POST, "/usuario/updateUser").hasAuthority("Programador")
                                .requestMatchers(HttpMethod.POST, "/usuario/addAddress").hasAuthority("Programador")
                                .requestMatchers(HttpMethod.POST, "/usuario/addUser").hasAuthority("Programador")
                                .requestMatchers(HttpMethod.GET, "/usuario/addUser/**").hasAnyAuthority("Analista", "Programador")
                                .requestMatchers(HttpMethod.POST, "/usuario/index").hasAuthority("Programador")
                                .requestMatchers(HttpMethod.GET, "/usuario/index").hasAnyAuthority("Administrador", "Programador", "Analista")
//                                .requestMatchers("/usuario/**").hasAuthority("Programador")
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
                .httpBasic(withDefaults()) //        .httpBasic(httpBasic -> httpBasic.init(httpSecurity)
                ;

        return httpSecurity.build();
    }

    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager(PasswordEncoder passwordEncoder) {
        UserDetails programador = User.builder() //Programador admin, puede hacer todo
                .password(passwordEncoder.encode("password1"))
                .username("Pepito")
                .roles("Programador")
                .authorities("Programador")
                .build();
        UserDetails administrador = User.builder() //Administtador -> Solo puede hacer la carga masiva
                .password(passwordEncoder.encode("password2"))
                .username("Juanito")
                .roles("Administrador")
                .authorities("Administrador")
                .build();
        UserDetails analista = User.builder() //Analista -> Solo puede hacer peticiones GET
                .password(passwordEncoder.encode("password3"))
                .username("Adal")
                .roles("Analista")
                .authorities("Analista")
                .build();
        return new InMemoryUserDetailsManager(programador, administrador, analista);

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

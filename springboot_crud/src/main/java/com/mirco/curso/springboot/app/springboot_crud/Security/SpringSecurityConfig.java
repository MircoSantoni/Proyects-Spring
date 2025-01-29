package com.mirco.curso.springboot.app.springboot_crud.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.mirco.curso.springboot.app.springboot_crud.Security.filter.JwtAuthenticationFilter;

@Configuration
public class SpringSecurityConfig {

    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;

    @Bean
    AuthenticationManager authenticationManager() throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }
    // bean para hashear contraseñas
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    // filtro de seguridad http
    @Bean
    SecurityFilterChain filterChain(org.springframework.security.config.annotation.web.builders.HttpSecurity http) throws Exception {
        return http
            // permitir acceso publico a users y autenticacion al resto
            .authorizeHttpRequests(authz -> authz
                .requestMatchers(HttpMethod.GET ,"/api/users", "/error").permitAll()  // Ruta pública                .requestMatchers(HttpMethod.GET ,"/api/users", "/error").permitAll()  // Ruta pública
                .requestMatchers(HttpMethod.POST, "/api/users/register", "/error").permitAll()
                .anyRequest().authenticated()  // Otras rutas requieren autenticación                .requestMatchers(HttpMethod.POST ,"/api/users/register", "/error").permitAll()  // Ruta pública

            )
            .addFilter(new JwtAuthenticationFilter(authenticationConfiguration.getAuthenticationManager()))
            .csrf(config -> config.disable())
            
            // configuracion de gestión de sesiones como STATELESS, servidor no almacena sesión, cada solicitud debe ser autenticada 
            .sessionManagement(management -> management
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .build();
    }
}

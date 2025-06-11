package com.codespace.tutorias.JWT.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Configuration
public class SecurityConfig {

    private final com.codespace.tutorias.JWT.JWTFilter jwtFilter;

    public SecurityConfig(com.codespace.tutorias.JWT.JWTFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers("/tutorado/registro", "/tutor/registro").permitAll()
                        .requestMatchers(HttpMethod.GET, "/tutorias/all", "/tutorias/{id}", "/tutorias/buscar").hasAnyRole("TUTOR", "TUTORADO", "ADMIN")
                        .requestMatchers("/tutorias/**").hasAnyRole("TUTOR", "ADMIN")
                        .requestMatchers("/horarios/**").hasAnyRole("TUTOR", "ADMIN")
                        .requestMatchers("/tutorado/**").hasAnyRole("TUTORADO", "ADMIN")
                        .requestMatchers("/tutor/**").hasAnyRole("TUTOR", "ADMIN")
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
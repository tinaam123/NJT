package com.penjackasala.climbing_sala.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    /**
     * Dev config: dozvoljava sve zahteve prema /api/** bez autentikacije.
     * Upotrebljavati SAMO za lokalni razvoj i testiranje.
     */
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // Dozvoli sve zahteve prema API-ju bez autentikacije
                        .requestMatchers("/api/**").permitAll()
                        // H2 konzola
                        .requestMatchers("/h2-console/**").permitAll()
                        // Sve ostalo zahteva autentikaciju (ako postoji)
                        .anyRequest().authenticated()
                )
                // Omogućava iframes za h2 konzolu
                .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.sameOrigin()))
                .build();
    }
}
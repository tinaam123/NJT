package com.penjackasala.climbing_sala.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.penjackasala.climbing_sala.repository.ClanRepository;

@Configuration
public class AdminUserConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(
            PasswordEncoder encoder,
            ClanRepository clanRepository
    ) {
        // Admin korisnik (hardkodovan)
        var admin = User.builder()
                .username("admin")
                .password(encoder.encode("admin123"))
                .roles("ADMIN")
                .build();

        return username -> {
            // Prvo proveri admin
            if (username.equals("admin")) return admin;

            // Zatim proveri clan u bazi
            return clanRepository.findByUsername(username)
                    .map(clan -> User.builder()
                            .username(clan.getUsername())
                            .password(clan.getPassword())
                            .roles("CLAN")
                            .build())
                    .orElseThrow(() -> new UsernameNotFoundException("Korisnik nije pronađen: " + username));
        };
    }
}
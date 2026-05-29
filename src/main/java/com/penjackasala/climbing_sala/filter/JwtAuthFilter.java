package com.penjackasala.climbing_sala.filter;

import com.penjackasala.climbing_sala.repository.ClanRepository;
import com.penjackasala.climbing_sala.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final ClanRepository clanRepository;

    public JwtAuthFilter(JwtUtil jwtUtil, ClanRepository clanRepository) {
        this.jwtUtil = jwtUtil;
        this.clanRepository = clanRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            if (jwtUtil.isValid(token)) {
                String username = jwtUtil.extractUsername(token);

                // Odredi role
                List<SimpleGrantedAuthority> authorities;
                if (username.equals("admin")) {
                    authorities = List.of(new SimpleGrantedAuthority("ROLE_ADMIN"));
                } else {
                    authorities = clanRepository.findByUsername(username)
                            .map(c -> List.of(new SimpleGrantedAuthority("ROLE_CLAN")))
                            .orElse(List.of());
                }

                var auth = new UsernamePasswordAuthenticationToken(
                        username, null, authorities
                );
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }

        filterChain.doFilter(request, response);
    }
}
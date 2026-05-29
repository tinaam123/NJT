package com.penjackasala.climbing_sala.controller;

import com.penjackasala.climbing_sala.dto.ClanResponse;
import com.penjackasala.climbing_sala.dto.LoginRequest;
import com.penjackasala.climbing_sala.dto.LoginResponse;
import com.penjackasala.climbing_sala.dto.RegistracijaKorisnikaRequest;
import com.penjackasala.climbing_sala.repository.ClanRepository;
import com.penjackasala.climbing_sala.util.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;
    private final ClanRepository clanRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(AuthenticationManager authManager, JwtUtil jwtUtil,
                          ClanRepository clanRepository, PasswordEncoder passwordEncoder) {
        this.authManager = authManager;
        this.jwtUtil = jwtUtil;
        this.clanRepository = clanRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        try {
            var auth = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.username(), request.password()
                    )
            );
            String token = jwtUtil.generateToken(request.username());
            String role = auth.getAuthorities().contains(
                    new SimpleGrantedAuthority("ROLE_ADMIN")) ? "ADMIN" : "CLAN";
            return new LoginResponse(token, request.username(), role);
        } catch (AuthenticationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                    "Pogrešno korisničko ime ili lozinka.");
        }
    }

    @PostMapping("/registruj-korisnika/{clanId}")
    public void registrujKorisnika(@PathVariable Long clanId,
                                   @Valid @RequestBody RegistracijaKorisnikaRequest request) {
        var clan = clanRepository.findById(clanId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Član nije pronađen"));

        if (clanRepository.existsByUsername(request.username())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Korisničko ime već postoji.");
        }

        clan.setUsername(request.username());
        clan.setPassword(passwordEncoder.encode(request.password()));
        clan.setRole(com.penjackasala.climbing_sala.enums.RoleKorisnika.CLAN);
        clanRepository.save(clan);
    }

    @GetMapping("/me/clan")
    public ClanResponse getMeClan(org.springframework.security.core.Authentication auth) {
        String username = auth.getName();
        return clanRepository.findByUsername(username)
                .map(ClanResponse::fromEntity)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Član nije pronađen"));
    }
}
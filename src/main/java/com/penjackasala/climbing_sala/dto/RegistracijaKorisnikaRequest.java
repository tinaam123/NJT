package com.penjackasala.climbing_sala.dto;

import jakarta.validation.constraints.NotBlank;

public record RegistracijaKorisnikaRequest(
        @NotBlank(message = "Korisničko ime je obavezno")
        String username,
        @NotBlank(message = "Lozinka je obavezna")
        String password
) {}
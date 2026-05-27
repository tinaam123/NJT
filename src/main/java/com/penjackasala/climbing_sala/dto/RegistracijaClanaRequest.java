package com.penjackasala.climbing_sala.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

import java.time.LocalDate;

public record RegistracijaClanaRequest(

        @NotBlank(message = "Ime je obavezno")
        String ime,

        @NotBlank(message = "Prezime je obavezno")
        String prezime,

        @NotNull(message = "Datum rođenja je obavezan")
        @Past(message = "Datum rođenja mora biti u prošlosti")
        LocalDate datumRodjenja,

        @NotBlank(message = "Adresa je obavezna")
        String adresa,

        @NotBlank(message = "Telefon je obavezan")
        String telefon,

        @NotBlank(message = "Email je obavezan")
        @Email(message = "Email nije u ispravnom formatu")
        String email,

        @NotBlank(message = "Kontakt osoba za hitne slučajeve je obavezna")
        String kontaktOsoba,

        @NotBlank(message = "Telefon kontakta za hitne slučajeve je obavezan")
        String kontaktTelefon,

        @NotBlank(message = "Krvna grupa je obavezna")
        String krvnaGrupa
) {
}

package com.penjackasala.climbing_sala.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Past;

import java.time.LocalDate;

public record IzmenaClanaRequest(
        String ime,
        String prezime,

        @Past(message = "Datum rođenja mora biti u prošlosti")
        LocalDate datumRodjenja,

        String adresa,
        String telefon,

        @Email(message = "Email nije u ispravnom formatu")
        String email,

        String kontaktOsoba,
        String kontaktTelefon,
        String krvnaGrupa,
        Boolean aktivan
) {
}
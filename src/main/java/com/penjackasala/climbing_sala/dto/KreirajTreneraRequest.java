package com.penjackasala.climbing_sala.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Email;
import java.time.LocalDate;

public record KreirajTreneraRequest(
        @NotBlank(message = "Ime je obavezno")
        String ime,

        @NotBlank(message = "Prezime je obavezno")
        String prezime,

        String specijalizacija,

        Double ocena,

        @Email(message = "Email mora biti validan")
        String email,

        String telefon,

        LocalDate datumZaposlenja,

        @NotNull(message = "Status aktivan je obavezan")
        Boolean aktivan
) {
}
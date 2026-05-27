package com.penjackasala.climbing_sala.dto;

import com.penjackasala.climbing_sala.enums.KategorijaRute;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record KreirajRutuRequest(
        @NotBlank(message = "Naziv rute je obavezan")
        String naziv,

        String tezina,

        Integer visina,

        String lokacija,

        String boja,

        @NotNull(message = "Kategorija je obavezna")
        KategorijaRute kategorija,

        LocalDate datumPostavljanja,

        String postavljac,

        @NotNull(message = "Status aktivna je obavezan")
        Boolean aktivna
) {
}
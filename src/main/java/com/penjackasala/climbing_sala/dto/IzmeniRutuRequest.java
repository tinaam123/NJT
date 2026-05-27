package com.penjackasala.climbing_sala.dto;

import com.penjackasala.climbing_sala.enums.KategorijaRute;

import java.time.LocalDate;

public record IzmeniRutuRequest(
        String naziv,

        String tezina,

        Integer visina,

        String lokacija,

        String boja,

        KategorijaRute kategorija,

        LocalDate datumPostavljanja,

        LocalDate datumUklanjanja,

        String postavljac,

        Boolean aktivna
) {
}
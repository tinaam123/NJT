package com.penjackasala.climbing_sala.dto;

import java.time.LocalDate;

/**
 * DTO za izmenu podataka o treneru - sva polja su opciona (partial update).
 */
public record IzmeniTreneraRequest(
        String ime,
        String prezime,
        String specijalizacija,
        Double ocena,
        String email,
        String telefon,
        LocalDate datumZaposlenja,
        Boolean aktivan
) {
}
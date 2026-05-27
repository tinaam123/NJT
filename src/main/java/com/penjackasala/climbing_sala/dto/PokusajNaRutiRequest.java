package com.penjackasala.climbing_sala.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record PokusajNaRutiRequest(

        @NotNull(message = "Ruta je obavezna")
        Long rutaId,

        @NotNull(message = "Broj pokušaja je obavezan")
        @Min(value = 1, message = "Broj pokušaja mora biti najmanje 1")
        Integer brPokusaja,

        @NotNull(message = "Polje 'savladana' je obavezno")
        Boolean savladana,

        String napomena
) {
}
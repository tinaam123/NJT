package com.penjackasala.climbing_sala.dto;

import com.penjackasala.climbing_sala.enums.TipClanarine;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public record DodavanjeClanarineRequest(

        @NotNull(message = "Tip članarine je obavezan")
        TipClanarine tip,

        @NotNull(message = "Datum početka je obavezan")
        LocalDate datumPocetka,

        @NotNull(message = "Datum isteka je obavezan")
        LocalDate datumIsteka,

        @NotNull(message = "Iznos je obavezan")
        @DecimalMin(value = "0.01", message = "Iznos mora biti veći od 0")
        BigDecimal iznos,

        String napomena
) {
}
package com.penjackasala.climbing_sala.dto;

import jakarta.validation.constraints.DecimalMin;

import java.math.BigDecimal;

public record EvidentiranjeUlaskaRequest(
        Boolean opremaNajam,

        @DecimalMin(value = "0.00", message = "Iznos najma ne može biti negativan")
        BigDecimal iznosNajma,

        Boolean pratilac,
        Boolean upisnina,
        String napomena
) {
}
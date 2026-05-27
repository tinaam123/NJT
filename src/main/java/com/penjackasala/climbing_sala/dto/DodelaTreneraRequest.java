package com.penjackasala.climbing_sala.dto;

import jakarta.validation.constraints.NotNull;

public record DodelaTreneraRequest(
        @NotNull(message = "ID trenera je obavezan")
        Long idTrenera
) {
}
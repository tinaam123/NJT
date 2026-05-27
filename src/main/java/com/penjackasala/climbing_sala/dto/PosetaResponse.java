package com.penjackasala.climbing_sala.dto;

import com.penjackasala.climbing_sala.entity.Poseta;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PosetaResponse(
        Long id,
        Long clanId,
        String ime,
        String prezime,
        LocalDateTime vremeUlaska,
        LocalDateTime vremeIzlaska,
        Integer trajanje,
        Boolean upisnina,
        Boolean opremaNajam,
        BigDecimal iznosNajma,
        Boolean pratilac,
        String napomena
) {

    public static PosetaResponse fromEntity(Poseta poseta) {
        return new PosetaResponse(
                poseta.getId(),
                poseta.getClan().getId(),
                poseta.getClan().getIme(),
                poseta.getClan().getPrezime(),
                poseta.getVremeUlaska(),
                poseta.getVremeIzlaska(),
                poseta.getTrajanje(),
                poseta.getUpisnina(),
                poseta.getOpremaNajam(),
                poseta.getIznosNajma(),
                poseta.getPratilac(),
                poseta.getNapomena()
        );
    }
}
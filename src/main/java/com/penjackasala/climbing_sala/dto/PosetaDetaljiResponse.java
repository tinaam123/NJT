package com.penjackasala.climbing_sala.dto;

import com.penjackasala.climbing_sala.entity.Poseta;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record PosetaDetaljiResponse(
        Long id,
        Long clanId,
        String ime,
        String prezime,
        LocalDateTime vremeUlaska,
        LocalDateTime vremeIzlaska,
        Integer trajanje,
        Boolean otvorena,
        Boolean opremaNajam,
        BigDecimal iznosNajma,
        Boolean pratilac,
        Boolean upisnina,
        String napomena,
        List<PokusajNaRutiResponse> pokusaji
) {

    public static PosetaDetaljiResponse fromEntity(Poseta poseta, List<PokusajNaRutiResponse> pokusaji) {
        return new PosetaDetaljiResponse(
                poseta.getId(),
                poseta.getClan().getId(),
                poseta.getClan().getIme(),
                poseta.getClan().getPrezime(),
                poseta.getVremeUlaska(),
                poseta.getVremeIzlaska(),
                poseta.getTrajanje(),
                poseta.getVremeIzlaska() == null,
                poseta.getOpremaNajam(),
                poseta.getIznosNajma(),
                poseta.getPratilac(),
                poseta.getUpisnina(),
                poseta.getNapomena(),
                pokusaji
        );
    }
}
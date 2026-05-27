package com.penjackasala.climbing_sala.dto;

import com.penjackasala.climbing_sala.entity.Poseta;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PosetaIstorijaItemResponse(
        Long id,
        LocalDateTime vremeUlaska,
        LocalDateTime vremeIzlaska,
        Integer trajanje,
        Boolean otvorena,
        Boolean opremaNajam,
        BigDecimal iznosNajma,
        Boolean pratilac,
        Boolean upisnina,
        String napomena,
        Integer brojPokusaja
) {

    public static PosetaIstorijaItemResponse fromEntity(Poseta poseta, int brojPokusaja) {
        return new PosetaIstorijaItemResponse(
                poseta.getId(),
                poseta.getVremeUlaska(),
                poseta.getVremeIzlaska(),
                poseta.getTrajanje(),
                poseta.getVremeIzlaska() == null,
                poseta.getOpremaNajam(),
                poseta.getIznosNajma(),
                poseta.getPratilac(),
                poseta.getUpisnina(),
                poseta.getNapomena(),
                brojPokusaja
        );
    }
}

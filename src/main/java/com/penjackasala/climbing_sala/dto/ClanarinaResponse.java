package com.penjackasala.climbing_sala.dto;

import com.penjackasala.climbing_sala.entity.Clanarina;
import com.penjackasala.climbing_sala.enums.TipClanarine;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ClanarinaResponse(
        Long id,
        TipClanarine tip,
        LocalDate datumPocetka,
        LocalDate datumIsteka,
        BigDecimal iznos,
        Boolean aktivna,
        String napomena,
        Long clanId
) {

    public static ClanarinaResponse fromEntity(Clanarina clanarina) {
        return new ClanarinaResponse(
                clanarina.getId(),
                clanarina.getTip(),
                clanarina.getDatumPocetka(),
                clanarina.getDatumIsteka(),
                clanarina.getIznos(),
                clanarina.getAktivna(),
                clanarina.getNapomena(),
                clanarina.getClan().getId()
        );
    }
}
package com.penjackasala.climbing_sala.dto;

import com.penjackasala.climbing_sala.entity.Clanarina;
import com.penjackasala.climbing_sala.enums.StatusClanarine;
import com.penjackasala.climbing_sala.enums.TipClanarine;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ClanarinaStatusResponse(
        Long id,
        TipClanarine tip,
        LocalDate datumPocetka,
        LocalDate datumIsteka,
        BigDecimal iznos,
        StatusClanarine status,
        Boolean trenutnoAktivna,
        String napomena
) {

    public static ClanarinaStatusResponse fromEntity(Clanarina clanarina, LocalDate danas) {
        boolean trenutnoAktivna = !danas.isBefore(clanarina.getDatumPocetka())
                && !danas.isAfter(clanarina.getDatumIsteka());

        return new ClanarinaStatusResponse(
                clanarina.getId(),
                clanarina.getTip(),
                clanarina.getDatumPocetka(),
                clanarina.getDatumIsteka(),
                clanarina.getIznos(),
                trenutnoAktivna ? StatusClanarine.AKTIVNA : StatusClanarine.ISTEKLA,
                trenutnoAktivna,
                clanarina.getNapomena()
        );
    }
}
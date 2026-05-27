package com.penjackasala.climbing_sala.dto;

import com.penjackasala.climbing_sala.entity.Clan;
import com.penjackasala.climbing_sala.entity.Clanarina;
import com.penjackasala.climbing_sala.enums.TipClanarine;

import java.math.BigDecimal;
import java.time.LocalDate;

public record IsteklaClanarinaResponse(
        Long clanId,
        String ime,
        String prezime,
        String email,
        String telefon,
        TipClanarine tipClanarine,
        LocalDate datumIsteka,
        BigDecimal iznos,
        Long danaDo,
        String statusPrikaz
) {

    public static IsteklaClanarinaResponse fromEntity(Clan clan, Clanarina clanarina, LocalDate sada) {
        long danaDo = java.time.temporal.ChronoUnit.DAYS.between(sada, clanarina.getDatumIsteka());

        String statusPrikaz;
        if (danaDo < 0) {
            statusPrikaz = "ISTEKLA - " + Math.abs(danaDo) + " dana unazad";
        } else {
            statusPrikaz = "ISTIČE ZA " + danaDo + " dana";
        }

        return new IsteklaClanarinaResponse(
                clan.getId(),
                clan.getIme(),
                clan.getPrezime(),
                clan.getEmail(),
                clan.getTelefon(),
                clanarina.getTip(),
                clanarina.getDatumIsteka(),
                clanarina.getIznos(),
                danaDo,
                statusPrikaz
        );
    }
}
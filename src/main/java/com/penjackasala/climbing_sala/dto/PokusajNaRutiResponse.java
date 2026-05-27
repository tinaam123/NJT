package com.penjackasala.climbing_sala.dto;

import com.penjackasala.climbing_sala.entity.PokusajNaRuti;
import com.penjackasala.climbing_sala.enums.KategorijaRute;

import java.time.LocalDate;

public record PokusajNaRutiResponse(
        Long id,
        LocalDate datum,
        Integer brPokusaja,
        Boolean savladana,
        String napomena,
        Long clanId,
        Long rutaId,
        String nazivRute,
        KategorijaRute kategorijaRute,
        String tezinaRute,
        Long posetaId
) {

    public static PokusajNaRutiResponse fromEntity(PokusajNaRuti pokusaj) {
        return new PokusajNaRutiResponse(
                pokusaj.getId(),
                pokusaj.getDatum(),
                pokusaj.getBrPokusaja(),
                pokusaj.getSavladana(),
                pokusaj.getNapomena(),
                pokusaj.getClan().getId(),
                pokusaj.getRuta().getId(),
                pokusaj.getRuta().getNaziv(),
                pokusaj.getRuta().getKategorija(),
                pokusaj.getRuta().getTezina(),
                pokusaj.getPoseta() != null ? pokusaj.getPoseta().getId() : null
        );
    }
}
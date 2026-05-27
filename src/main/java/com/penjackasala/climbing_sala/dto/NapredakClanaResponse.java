package com.penjackasala.climbing_sala.dto;

import java.util.List;

public record NapredakClanaResponse(
        Long clanId,
        String ime,
        String prezime,
        Integer ukupnoPokusaja,
        Integer ukupnoSavladano,
        String poruka,
        List<PokusajNaRutiResponse> pokusaji
) {
}
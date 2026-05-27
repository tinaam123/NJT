package com.penjackasala.climbing_sala.dto;

import java.util.List;

public record StatusClanarineClanaResponse(
        Long clanId,
        String ime,
        String prezime,
        Boolean imaAktivnuClanarinu,
        String upozorenje,
        ClanarinaStatusResponse aktivnaClanarina,
        List<ClanarinaStatusResponse> sveClanarine
) {
}
package com.penjackasala.climbing_sala.dto;

import java.util.List;

public record IstorijaPosetaResponse(
        Long clanId,
        String ime,
        String prezime,
        Integer ukupnoPoseta,
        String poruka,
        List<PosetaIstorijaItemResponse> posete
) {
}
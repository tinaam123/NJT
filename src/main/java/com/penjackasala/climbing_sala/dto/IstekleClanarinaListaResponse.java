package com.penjackasala.climbing_sala.dto;

import com.penjackasala.climbing_sala.enums.FilterTip;

import java.util.List;

public record IstekleClanarinaListaResponse(
        List<IsteklaClanarinaResponse> clanarine,
        Integer ukupniBroj,
        FilterTip filterKoristenjeFilter,
        Integer brojiDana
) {
}

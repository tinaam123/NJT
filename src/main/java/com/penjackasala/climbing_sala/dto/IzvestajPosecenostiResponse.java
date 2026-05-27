package com.penjackasala.climbing_sala.dto;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Response for visit report.
 */
public record IzvestajPosecenostiResponse(
        LocalDateTime periodStart,
        LocalDateTime periodEnd,
        int brojPoseta,
        Double prosecnoTrajanjeMin,
        List<ClanAktivnost> najaktivnijiClanovi,
        List<PosetePoDanu> posetePoDanu
) {
}
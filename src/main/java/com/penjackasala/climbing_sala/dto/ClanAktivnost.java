package com.penjackasala.climbing_sala.dto;

/**
 * Aggregated activity for a member (clan).
 */
public record ClanAktivnost(
        Long clanId,
        String imePrezime,
        int brojPoseta,
        long ukupnoTrajanjeMin
) {
}
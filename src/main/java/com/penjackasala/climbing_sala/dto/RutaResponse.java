package com.penjackasala.climbing_sala.dto;

import com.penjackasala.climbing_sala.entity.Ruta;
import com.penjackasala.climbing_sala.enums.KategorijaRute;
import java.time.LocalDate;

public record RutaResponse(
        Long id,
        String naziv,
        String tezina,
        Integer visina,
        String lokacija,
        String boja,
        KategorijaRute kategorija,
        LocalDate datumPostavljanja,
        LocalDate datumUklanjanja,
        String postavljac,
        Boolean aktivna,
        Integer brojPokusaja,
        Integer brojUspelihPokusaja
) {
    public static RutaResponse fromEntity(Ruta ruta) {
        int brojPokusaja = ruta.getPokusaji() != null ? ruta.getPokusaji().size() : 0;
        int brojUspelih = ruta.getPokusaji() != null
                ? (int) ruta.getPokusaji().stream().filter(p -> Boolean.TRUE.equals(p.getSavladana())).count()
                : 0;
        return new RutaResponse(
                ruta.getId(),
                ruta.getNaziv(),
                ruta.getTezina(),
                ruta.getVisina(),
                ruta.getLokacija(),
                ruta.getBoja(),
                ruta.getKategorija(),
                ruta.getDatumPostavljanja(),
                ruta.getDatumUklanjanja(),
                ruta.getPostavljac(),
                ruta.getAktivna(),
                brojPokusaja,
                brojUspelih
        );
    }
}
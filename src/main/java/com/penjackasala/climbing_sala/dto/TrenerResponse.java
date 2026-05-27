package com.penjackasala.climbing_sala.dto;

import com.penjackasala.climbing_sala.entity.Trener;

import java.time.LocalDate;

public record TrenerResponse(
        Long id,
        String ime,
        String prezime,
        String specijalizacija,
        Double ocena,
        String email,
        String telefon,
        LocalDate datumZaposlenja,
        Boolean aktivan,
        Integer brojClanova
) {

    public static TrenerResponse fromEntity(Trener trener) {
        int brojClanova = trener.getClanovi() != null ? trener.getClanovi().size() : 0;

        return new TrenerResponse(
                trener.getId(),
                trener.getIme(),
                trener.getPrezime(),
                trener.getSpecijalizacija(),
                trener.getOcena(),
                trener.getEmail(),
                trener.getTelefon(),
                trener.getDatumZaposlenja(),
                trener.getAktivan(),
                brojClanova
        );
    }
}
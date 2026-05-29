package com.penjackasala.climbing_sala.dto;

import com.penjackasala.climbing_sala.entity.Clan;
import java.time.LocalDate;

public record ClanResponse(
        Long id,
        String ime,
        String prezime,
        LocalDate datumRodjenja,
        LocalDate datumUclanjanja,
        String adresa,
        String telefon,
        String email,
        String kontaktOsoba,
        String kontaktTelefon,
        String krvnaGrupa,
        Boolean aktivan,
        Long trenerId,
        String trenerImePrezime,
        String username
) {
    public static ClanResponse fromEntity(Clan clan) {
        Long trenerId = clan.getTrener() != null ? clan.getTrener().getId() : null;
        String trenerImePrezime = clan.getTrener() != null
                ? clan.getTrener().getIme() + " " + clan.getTrener().getPrezime()
                : null;
        return new ClanResponse(
                clan.getId(),
                clan.getIme(),
                clan.getPrezime(),
                clan.getDatumRodjenja(),
                clan.getDatumUclanjanja(),
                clan.getAdresa(),
                clan.getTelefon(),
                clan.getEmail(),
                clan.getKontaktOsoba(),
                clan.getKontaktTelefon(),
                clan.getKrvnaGrupa(),
                clan.getAktivan(),
                trenerId,
                trenerImePrezime,
                clan.getUsername()
        );
    }
}
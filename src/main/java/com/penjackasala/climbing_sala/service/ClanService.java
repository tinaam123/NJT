package com.penjackasala.climbing_sala.service;

import com.penjackasala.climbing_sala.dto.ClanResponse;
import com.penjackasala.climbing_sala.dto.IzmenaClanaRequest;
import com.penjackasala.climbing_sala.dto.RegistracijaClanaRequest;
import com.penjackasala.climbing_sala.entity.Clan;
import com.penjackasala.climbing_sala.entity.Trener;
import com.penjackasala.climbing_sala.repository.ClanRepository;
import com.penjackasala.climbing_sala.repository.TrenerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class ClanService {

    private final ClanRepository clanRepository;
    private final TrenerRepository trenerRepository;

    public ClanService(ClanRepository clanRepository, TrenerRepository trenerRepository) {
        this.clanRepository = clanRepository;
        this.trenerRepository = trenerRepository;
    }

    @Transactional
    public ClanResponse registrujClana(RegistracijaClanaRequest request) {
        if (clanRepository.existsByEmail(request.email())) {
            throw new IllegalArgumentException("Član sa unetim emailom već postoji");
        }

        Clan clan = new Clan();
        clan.setIme(request.ime());
        clan.setPrezime(request.prezime());
        clan.setDatumRodjenja(request.datumRodjenja());
        clan.setDatumUclanjanja(LocalDate.now());
        clan.setAdresa(request.adresa());
        clan.setTelefon(request.telefon());
        clan.setEmail(request.email());
        clan.setKontaktOsoba(request.kontaktOsoba());
        clan.setKontaktTelefon(request.kontaktTelefon());
        clan.setKrvnaGrupa(request.krvnaGrupa());
        clan.setAktivan(true);

        Clan sacuvanClan = clanRepository.save(clan);

        return ClanResponse.fromEntity(sacuvanClan);
    }

    @Transactional(readOnly = true)
    public ClanResponse pronadjiClana(Long id) {
        Clan clan = clanRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Član nije pronađen"));

        return ClanResponse.fromEntity(clan);
    }

    @Transactional(readOnly = true)
    public List<ClanResponse> pretraziClanove(String kriterijum) {
        if (kriterijum == null || kriterijum.isBlank()) {
            throw new IllegalArgumentException("Kriterijum pretrage je obavezan");
        }

        String sredjenKriterijum = kriterijum.trim();

        if (sredjenKriterijum.matches("\\d+")) {
            return clanRepository.findById(Long.parseLong(sredjenKriterijum))
                    .map(clan -> List.of(ClanResponse.fromEntity(clan)))
                    .orElseThrow(() -> new IllegalArgumentException("Član nije pronađen"));
        }

        List<ClanResponse> rezultati = clanRepository
                .findByImeContainingIgnoreCaseOrPrezimeContainingIgnoreCase(sredjenKriterijum, sredjenKriterijum)
                .stream()
                .map(ClanResponse::fromEntity)
                .toList();

        if (rezultati.isEmpty()) {
            throw new IllegalArgumentException("Član nije pronađen");
        }

        return rezultati;
    }

    @Transactional
    public ClanResponse izmeniClana(Long id, IzmenaClanaRequest request) {
        Clan clan = clanRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Član nije pronađen"));

        if (request.email() != null && !request.email().equals(clan.getEmail()) && clanRepository.existsByEmail(request.email())) {
            throw new IllegalArgumentException("Član sa unetim emailom već postoji");
        }

        if (request.ime() != null) {
            clan.setIme(request.ime());
        }
        if (request.prezime() != null) {
            clan.setPrezime(request.prezime());
        }
        if (request.datumRodjenja() != null) {
            clan.setDatumRodjenja(request.datumRodjenja());
        }
        if (request.adresa() != null) {
            clan.setAdresa(request.adresa());
        }
        if (request.telefon() != null) {
            clan.setTelefon(request.telefon());
        }
        if (request.email() != null) {
            clan.setEmail(request.email());
        }
        if (request.kontaktOsoba() != null) {
            clan.setKontaktOsoba(request.kontaktOsoba());
        }
        if (request.kontaktTelefon() != null) {
            clan.setKontaktTelefon(request.kontaktTelefon());
        }
        if (request.krvnaGrupa() != null) {
            clan.setKrvnaGrupa(request.krvnaGrupa());
        }
        if (request.aktivan() != null) {
            clan.setAktivan(request.aktivan());
        }

        Clan izmenjenClan = clanRepository.save(clan);

        return ClanResponse.fromEntity(izmenjenClan);
    }

    // UC-10 - Dodela trenera članu
    @Transactional
    public ClanResponse dodeliTrenera(Long idClana, Long idTrenera) {
        Clan clan = clanRepository.findById(idClana)
                .orElseThrow(() -> new IllegalArgumentException("Član nije pronađen"));

        Trener trener = trenerRepository.findById(idTrenera)
                .orElseThrow(() -> new IllegalArgumentException("Trener nije pronađen"));

        if (!trener.getAktivan()) {
            throw new IllegalArgumentException("Trener mora biti aktivan");
        }

        clan.setTrener(trener);
        Clan sacuvanClan = clanRepository.save(clan);

        return ClanResponse.fromEntity(sacuvanClan);
    }

    // UC-10 - Uklanjanje trenera sa člana
    @Transactional
    public ClanResponse ukloniTrenera(Long idClana) {
        Clan clan = clanRepository.findById(idClana)
                .orElseThrow(() -> new IllegalArgumentException("Član nije pronađen"));

        if (clan.getTrener() == null) {
            throw new IllegalArgumentException("Član nema dodeljenog trenera");
        }

        clan.setTrener(null);
        Clan sacuvanClan = clanRepository.save(clan);

        return ClanResponse.fromEntity(sacuvanClan);
    }
    @Transactional(readOnly = true)
    public List<ClanResponse> sviClanovi() {
        return clanRepository.findAll()
                .stream()
                .map(ClanResponse::fromEntity)
                .toList();
    }
}
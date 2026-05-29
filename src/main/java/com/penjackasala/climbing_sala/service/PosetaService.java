package com.penjackasala.climbing_sala.service;

import com.penjackasala.climbing_sala.dto.*;
import com.penjackasala.climbing_sala.entity.Clan;
import com.penjackasala.climbing_sala.entity.Poseta;
import com.penjackasala.climbing_sala.repository.ClanRepository;
import com.penjackasala.climbing_sala.repository.ClanarinaRepository;
import com.penjackasala.climbing_sala.repository.PokusajNaRutiRepository;
import com.penjackasala.climbing_sala.repository.PosetaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PosetaService {

    private final PosetaRepository posetaRepository;
    private final ClanRepository clanRepository;
    private final ClanarinaRepository clanarinaRepository;
    private final PokusajNaRutiRepository pokusajRepository;

    public PosetaService(
            PosetaRepository posetaRepository,
            ClanRepository clanRepository,
            ClanarinaRepository clanarinaRepository,
            PokusajNaRutiRepository pokusajRepository
    ) {
        this.posetaRepository = posetaRepository;
        this.clanRepository = clanRepository;
        this.clanarinaRepository = clanarinaRepository;
        this.pokusajRepository = pokusajRepository;
    }

    @Transactional
    public PosetaResponse evidentirajUlazak(Long clanId, EvidentiranjeUlaskaRequest request) {
        Clan clan = clanRepository.findById(clanId)
                .orElseThrow(() -> new IllegalArgumentException("Član nije pronađen"));

        LocalDate danas = LocalDate.now();

        boolean imaAktivnuClanarinu = clanarinaRepository
                .existsByClanIdAndDatumPocetkaLessThanEqualAndDatumIstekaGreaterThanEqual(
                        clanId,
                        danas,
                        danas
                );

        if (!imaAktivnuClanarinu) {
            throw new IllegalArgumentException("Član nema aktivnu članarinu. Ulazak nije dozvoljen.");
        }

        boolean imaOtvorenaPoseta = posetaRepository
                .findFirstByClanIdAndVremeIzlaskaIsNullOrderByVremeUlaskaDesc(clanId)
                .isPresent();

        if (imaOtvorenaPoseta) {
            throw new IllegalArgumentException("Član već ima evidentiran ulazak bez izlaska. Najpre evidentirajte izlazak.");
        }

        Poseta poseta = new Poseta();
        poseta.setClan(clan);
        poseta.setVremeUlaska(LocalDateTime.now());
        poseta.setVremeIzlaska(null);
        poseta.setTrajanje(null);
        poseta.setOpremaNajam(Boolean.TRUE.equals(request.opremaNajam()));
        poseta.setIznosNajma(request.iznosNajma());
        poseta.setPratilac(Boolean.TRUE.equals(request.pratilac()));
        poseta.setUpisnina(Boolean.TRUE.equals(request.upisnina()));
        poseta.setNapomena(request.napomena());

        Poseta sacuvanaPoseta = posetaRepository.save(poseta);

        return PosetaResponse.fromEntity(sacuvanaPoseta);
    }

    @Transactional
    public PosetaResponse evidentirajIzlazak(Long clanId) {
        if (!clanRepository.existsById(clanId)) {
            throw new IllegalArgumentException("Član nije pronađen");
        }

        Poseta otvorenaPoseta = posetaRepository
                .findFirstByClanIdAndVremeIzlaskaIsNullOrderByVremeUlaskaDesc(clanId)
                .orElseThrow(() -> new IllegalArgumentException("Nije pronađena otvorena poseta za člana"));

        LocalDateTime vremeIzlaska = LocalDateTime.now();
        otvorenaPoseta.setVremeIzlaska(vremeIzlaska);

        long trajanjeUMinutima = Duration.between(otvorenaPoseta.getVremeUlaska(), vremeIzlaska).toMinutes();
        otvorenaPoseta.setTrajanje((int) trajanjeUMinutima);

        Poseta zatvorenaPoseta = posetaRepository.save(otvorenaPoseta);

        return PosetaResponse.fromEntity(zatvorenaPoseta);
    }

    @Transactional(readOnly = true)
    public IstorijaPosetaResponse pregledIstorijePoseta(Long clanId) {
        Clan clan = clanRepository.findById(clanId)
                .orElseThrow(() -> new IllegalArgumentException("Član nije pronađen"));

        List<Poseta> posete = posetaRepository.findByClanIdOrderByVremeUlaskaDesc(clanId);

        List<PosetaIstorijaItemResponse> stavke = posete.stream()
                .map(poseta -> {
                    int brojPokusaja = pokusajRepository.findByPosetaIdOrderByDatumDesc(poseta.getId()).size();
                    return PosetaIstorijaItemResponse.fromEntity(poseta, brojPokusaja);
                })
                .toList();

        String poruka = stavke.isEmpty()
                ? "Nema evidentiranih poseta za ovog člana."
                : null;

        return new IstorijaPosetaResponse(
                clan.getId(),
                clan.getIme(),
                clan.getPrezime(),
                stavke.size(),
                poruka,
                stavke
        );
    }

    @Transactional(readOnly = true)
    public PosetaDetaljiResponse pregledDetaljaPosete(Long clanId, Long posetaId) {
        if (!clanRepository.existsById(clanId)) {
            throw new IllegalArgumentException("Član nije pronađen");
        }

        Poseta poseta = posetaRepository.findById(posetaId)
                .orElseThrow(() -> new IllegalArgumentException("Poseta nije pronađena"));

        if (!poseta.getClan().getId().equals(clanId)) {
            throw new IllegalArgumentException("Poseta ne pripada izabranom članu");
        }

        List<PokusajNaRutiResponse> pokusaji = pokusajRepository
                .findByPosetaIdOrderByDatumDesc(posetaId)
                .stream()
                .map(PokusajNaRutiResponse::fromEntity)
                .toList();

        return PosetaDetaljiResponse.fromEntity(poseta, pokusaji);
    }
}
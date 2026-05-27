package com.penjackasala.climbing_sala.service;

import com.penjackasala.climbing_sala.dto.ClanarinaResponse;
import com.penjackasala.climbing_sala.dto.ClanarinaStatusResponse;
import com.penjackasala.climbing_sala.dto.DodavanjeClanarineRequest;
import com.penjackasala.climbing_sala.dto.StatusClanarineClanaResponse;
import com.penjackasala.climbing_sala.entity.Clan;
import com.penjackasala.climbing_sala.entity.Clanarina;
import com.penjackasala.climbing_sala.repository.ClanRepository;
import com.penjackasala.climbing_sala.repository.ClanarinaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

@Service
public class ClanarinaService {

    private final ClanarinaRepository clanarinaRepository;
    private final ClanRepository clanRepository;

    public ClanarinaService(ClanarinaRepository clanarinaRepository, ClanRepository clanRepository) {
        this.clanarinaRepository = clanarinaRepository;
        this.clanRepository = clanRepository;
    }

    @Transactional
    public ClanarinaResponse dodajClanarinu(Long clanId, DodavanjeClanarineRequest request) {
        Clan clan = clanRepository.findById(clanId)
                .orElseThrow(() -> new IllegalArgumentException("Član nije pronađen"));

        if (request.datumIsteka().isBefore(request.datumPocetka())) {
            throw new IllegalArgumentException("Datum isteka ne može biti pre datuma početka");
        }

        Clanarina clanarina = new Clanarina();
        clanarina.setClan(clan);
        clanarina.setTip(request.tip());
        clanarina.setDatumPocetka(request.datumPocetka());
        clanarina.setDatumIsteka(request.datumIsteka());
        clanarina.setIznos(request.iznos());
        clanarina.setNapomena(request.napomena());
        clanarina.setAktivna(true);

        Clanarina sacuvanaClanarina = clanarinaRepository.save(clanarina);

        return ClanarinaResponse.fromEntity(sacuvanaClanarina);
    }

    @Transactional(readOnly = true)
    public StatusClanarineClanaResponse pregledStatusaClanarine(Long clanId) {
        Clan clan = clanRepository.findById(clanId)
                .orElseThrow(() -> new IllegalArgumentException("Član nije pronađen"));

        LocalDate danas = LocalDate.now();

        List<ClanarinaStatusResponse> sveClanarine = clanarinaRepository
                .findByClanIdOrderByDatumPocetkaDesc(clanId)
                .stream()
                .map(clanarina -> ClanarinaStatusResponse.fromEntity(clanarina, danas))
                .toList();

        ClanarinaStatusResponse aktivnaClanarina = sveClanarine.stream()
                .filter(ClanarinaStatusResponse::trenutnoAktivna)
                .max(Comparator.comparing(ClanarinaStatusResponse::datumIsteka))
                .orElse(null);

        boolean imaAktivnuClanarinu = aktivnaClanarina != null;

        String upozorenje = imaAktivnuClanarinu
                ? null
                : "Član nema aktivnu članarinu";

        return new StatusClanarineClanaResponse(
                clan.getId(),
                clan.getIme(),
                clan.getPrezime(),
                imaAktivnuClanarinu,
                upozorenje,
                aktivnaClanarina,
                sveClanarine
        );
    }
}
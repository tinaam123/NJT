package com.penjackasala.climbing_sala.service;

import com.penjackasala.climbing_sala.dto.NapredakClanaResponse;
import com.penjackasala.climbing_sala.dto.PokusajNaRutiRequest;
import com.penjackasala.climbing_sala.dto.PokusajNaRutiResponse;
import com.penjackasala.climbing_sala.entity.Clan;
import com.penjackasala.climbing_sala.entity.PokusajNaRuti;
import com.penjackasala.climbing_sala.entity.Poseta;
import com.penjackasala.climbing_sala.entity.Ruta;
import com.penjackasala.climbing_sala.enums.KategorijaRute;
import com.penjackasala.climbing_sala.repository.ClanRepository;
import com.penjackasala.climbing_sala.repository.PokusajNaRutiRepository;
import com.penjackasala.climbing_sala.repository.PosetaRepository;
import com.penjackasala.climbing_sala.repository.RutaRepository;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class PokusajNaRutiService {

    private final PokusajNaRutiRepository pokusajRepository;
    private final ClanRepository clanRepository;
    private final RutaRepository rutaRepository;
    private final PosetaRepository posetaRepository;

    public PokusajNaRutiService(
            PokusajNaRutiRepository pokusajRepository,
            ClanRepository clanRepository,
            RutaRepository rutaRepository,
            PosetaRepository posetaRepository
    ) {
        this.pokusajRepository = pokusajRepository;
        this.clanRepository = clanRepository;
        this.rutaRepository = rutaRepository;
        this.posetaRepository = posetaRepository;
    }

    @Transactional
    public PokusajNaRutiResponse dodajPokusajNaAktivnojPoseti(Long clanId, PokusajNaRutiRequest request) {
        Clan clan = clanRepository.findById(clanId)
                .orElseThrow(() -> new IllegalArgumentException("Član nije pronađen"));

        Poseta aktivnaPoseta = posetaRepository
                .findFirstByClanIdAndVremeIzlaskaIsNullOrderByVremeUlaskaDesc(clanId)
                .orElseThrow(() -> new IllegalArgumentException("Nije pronađena otvorena poseta za člana"));

        Ruta ruta = rutaRepository.findById(request.rutaId())
                .orElseThrow(() -> new IllegalArgumentException("Ruta ne postoji. Kreirajte rutu pre evidentiranja pokušaja."));

        PokusajNaRuti pokusaj = new PokusajNaRuti();
        pokusaj.setClan(clan);
        pokusaj.setRuta(ruta);
        pokusaj.setPoseta(aktivnaPoseta);
        pokusaj.setDatum(LocalDate.now());
        pokusaj.setBrPokusaja(request.brPokusaja());
        pokusaj.setSavladana(request.savladana());
        pokusaj.setNapomena(request.napomena());

        PokusajNaRuti sacuvanPokusaj = pokusajRepository.save(pokusaj);

        return PokusajNaRutiResponse.fromEntity(sacuvanPokusaj);
    }

    @Transactional(readOnly = true)
    public NapredakClanaResponse pregledNapretka(
            Long clanId,
            KategorijaRute kategorija,
            LocalDate datumOd,
            LocalDate datumDo,
            Boolean savladana
    ) {
        Clan clan = clanRepository.findById(clanId)
                .orElseThrow(() -> new IllegalArgumentException("Član nije pronađen"));

        Specification<PokusajNaRuti> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(cb.equal(root.get("clan").get("id"), clanId));

            if (kategorija != null) {
                predicates.add(cb.equal(root.get("ruta").get("kategorija"), kategorija));
            }
            if (datumOd != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("datum"), datumOd));
            }
            if (datumDo != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("datum"), datumDo));
            }
            if (savladana != null) {
                predicates.add(cb.equal(root.get("savladana"), savladana));
            }

            query.orderBy(cb.desc(root.get("datum")));
            return cb.and(predicates.toArray(new Predicate[0]));
        };

        List<PokusajNaRutiResponse> pokusaji = pokusajRepository.findAll(spec)
                .stream()
                .map(PokusajNaRutiResponse::fromEntity)
                .toList();

        int ukupnoSavladano = (int) pokusaji.stream().filter(PokusajNaRutiResponse::savladana).count();

        String poruka = pokusaji.isEmpty()
                ? "Nema evidentiranih pokušaja za izabrane kriterijume."
                : null;

        return new NapredakClanaResponse(
                clan.getId(),
                clan.getIme(),
                clan.getPrezime(),
                pokusaji.size(),
                ukupnoSavladano,
                poruka,
                pokusaji
        );
    }
}
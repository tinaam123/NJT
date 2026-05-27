package com.penjackasala.climbing_sala.service;

import com.penjackasala.climbing_sala.dto.ClanAktivnost;
import com.penjackasala.climbing_sala.dto.IzvestajPosecenostiResponse;
import com.penjackasala.climbing_sala.dto.PosetePoDanu;
import com.penjackasala.climbing_sala.entity.Poseta;
import com.penjackasala.climbing_sala.repository.PosetaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.StringWriter;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service for generating visit reports.
 */
@Service
public class IzvestajService {

    private final PosetaRepository posetaRepository;

    public IzvestajService(PosetaRepository posetaRepository) {
        this.posetaRepository = posetaRepository;
    }

    @Transactional(readOnly = true)
    public IzvestajPosecenostiResponse generisiIzvestaj(LocalDateTime start, LocalDateTime end) {
        if (start == null || end == null) {
            throw new IllegalArgumentException("period start i end moraju biti zadati");
        }
        if (end.isBefore(start)) {
            throw new IllegalArgumentException("period end mora biti posle period start");
        }

        List<Poseta> posete = posetaRepository.findPoseteUPeriodu(start, end);

        int brojPoseta = posete.size();

        // compute durations in minutes for each poseta
        List<Long> trajanja = new ArrayList<>();
        for (Poseta p : posete) {
            Long trajanjeMin = izracunajTrajanjeMin(p);
            if (trajanjeMin != null) {
                trajanja.add(trajanjeMin);
            }
        }

        Double prosecnoTrajanje = null;
        if (!trajanja.isEmpty()) {
            double avg = trajanja.stream().mapToLong(Long::longValue).average().orElse(0.0);
            prosecnoTrajanje = Math.round(avg * 100.0) / 100.0; // round 2 dec
        }

        // Najaktivniji clanovi: group by clan id
        Map<Long, ClanAgg> mapa = new HashMap<>();
        for (Poseta p : posete) {
            Long clanId = p.getClan().getId();
            String imePrezime = p.getClan().getIme() + " " + p.getClan().getPrezime();
            long tr = Optional.ofNullable(izracunajTrajanjeMin(p)).orElse(0L);
            mapa.compute(clanId, (k, agg) -> {
                if (agg == null) return new ClanAgg(clanId, imePrezime, 1, tr);
                agg.broj++;
                agg.ukupnoTrajanje += tr;
                return agg;
            });
        }

        List<ClanAktivnost> najaktivniji = mapa.values().stream()
                .sorted(Comparator.comparingInt(ClanAgg::getBroj).reversed()
                        .thenComparingLong(ClanAgg::getUkupnoTrajanje).reversed())
                .limit(5)
                .map(a -> new ClanAktivnost(a.clanId, a.imePrezime, a.broj, a.ukupnoTrajanje))
                .collect(Collectors.toList());

        // posete po danu: use vremeUlaska date
        Map<LocalDate, Integer> poDanu = new TreeMap<>();
        for (Poseta p : posete) {
            LocalDate dan = p.getVremeUlaska().toLocalDate();
            poDanu.merge(dan, 1, Integer::sum);
        }
        List<PosetePoDanu> posetePoDanu = poDanu.entrySet().stream()
                .map(e -> new PosetePoDanu(e.getKey(), e.getValue()))
                .collect(Collectors.toList());

        return new IzvestajPosecenostiResponse(start, end, brojPoseta, prosecnoTrajanje, najaktivniji, posetePoDanu);
    }

    /**
     * Export the same report as CSV (simple, comma-separated).
     */
    @Transactional(readOnly = true)
    public String generisiIzvestajCsv(LocalDateTime start, LocalDateTime end) {
        IzvestajPosecenostiResponse rpt = generisiIzvestaj(start, end);
        StringWriter sw = new StringWriter();

        sw.append("Period start,").append(rpt.periodStart().toString()).append("\n");
        sw.append("Period end,").append(rpt.periodEnd().toString()).append("\n");
        sw.append("Broj poseta,").append(String.valueOf(rpt.brojPoseta())).append("\n");
        sw.append("Prosecno trajanje (min),").append(String.valueOf(rpt.prosecnoTrajanjeMin() != null ? rpt.prosecnoTrajanjeMin() : "")).append("\n\n");

        sw.append("Najaktivniji clanovi\n");
        sw.append("clanId,imePrezime,brojPoseta,ukupnoTrajanjeMin\n");
        for (ClanAktivnost a : rpt.najaktivnijiClanovi()) {
            sw.append(String.valueOf(a.clanId())).append(",");
            sw.append("\"").append(a.imePrezime()).append("\",");
            sw.append(String.valueOf(a.brojPoseta())).append(",");
            sw.append(String.valueOf(a.ukupnoTrajanjeMin())).append("\n");
        }

        sw.append("\nPosete po danu\n");
        sw.append("dan,brojPoseta\n");
        for (PosetePoDanu p : rpt.posetePoDanu()) {
            sw.append(p.dan().toString()).append(",").append(String.valueOf(p.brojPoseta())).append("\n");
        }

        return sw.toString();
    }

    private Long izracunajTrajanjeMin(Poseta p) {
        if (p.getTrajanje() != null) {
            return p.getTrajanje().longValue();
        }
        if (p.getVremeUlaska() != null && p.getVremeIzlaska() != null) {
            Duration d = Duration.between(p.getVremeUlaska(), p.getVremeIzlaska());
            return d.toMinutes();
        }
        return null; // unknown duration
    }

    // simple mutable aggregator used internally
    private static class ClanAgg {
        final Long clanId;
        final String imePrezime;
        int broj;
        long ukupnoTrajanje;

        ClanAgg(Long clanId, String imePrezime, int broj, long ukupnoTrajanje) {
            this.clanId = clanId;
            this.imePrezime = imePrezime;
            this.broj = broj;
            this.ukupnoTrajanje = ukupnoTrajanje;
        }

        int getBroj() { return broj; }
        long getUkupnoTrajanje() { return ukupnoTrajanje; }
    }
}
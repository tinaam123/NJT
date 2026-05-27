package com.penjackasala.climbing_sala.service;

import com.penjackasala.climbing_sala.dto.IzmeniTreneraRequest;
import com.penjackasala.climbing_sala.dto.KreirajTreneraRequest;
import com.penjackasala.climbing_sala.dto.TrenerResponse;
import com.penjackasala.climbing_sala.entity.Trener;
import com.penjackasala.climbing_sala.repository.TrenerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TrenerService {

    private final TrenerRepository trenerRepository;

    public TrenerService(TrenerRepository trenerRepository) {
        this.trenerRepository = trenerRepository;
    }

    @Transactional
    public TrenerResponse kreirajTrenera(KreirajTreneraRequest request) {
        Trener trener = new Trener();
        trener.setIme(request.ime());
        trener.setPrezime(request.prezime());
        trener.setSpecijalizacija(request.specijalizacija());
        trener.setOcena(request.ocena());
        trener.setEmail(request.email());
        trener.setTelefon(request.telefon());
        trener.setDatumZaposlenja(request.datumZaposlenja());
        trener.setAktivan(request.aktivan());

        Trener sacuvanTrener = trenerRepository.save(trener);

        return TrenerResponse.fromEntity(sacuvanTrener);
    }

    @Transactional(readOnly = true)
    public TrenerResponse pronadjiTrenera(Long id) {
        Trener trener = trenerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Trener nije pronađen"));

        return TrenerResponse.fromEntity(trener);
    }

    @Transactional(readOnly = true)
    public List<TrenerResponse> listaDostupnihTrenera() {
        return trenerRepository.findByAktivanTrueOrderByOcenaDesc()
                .stream()
                .map(TrenerResponse::fromEntity)
                .toList();
    }

    // UC-12 - Uređivanje trenera (partial update)
    @Transactional
    public TrenerResponse izmeniTrenera(Long id, IzmeniTreneraRequest request) {
        Trener trener = trenerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Trener nije pronađen"));

        if (request.ime() != null) {
            trener.setIme(request.ime());
        }
        if (request.prezime() != null) {
            trener.setPrezime(request.prezime());
        }
        if (request.specijalizacija() != null) {
            trener.setSpecijalizacija(request.specijalizacija());
        }
        if (request.ocena() != null) {
            trener.setOcena(request.ocena());
        }
        if (request.email() != null) {
            trener.setEmail(request.email());
        }
        if (request.telefon() != null) {
            trener.setTelefon(request.telefon());
        }
        if (request.datumZaposlenja() != null) {
            trener.setDatumZaposlenja(request.datumZaposlenja());
        }
        if (request.aktivan() != null) {
            trener.setAktivan(request.aktivan());
        }

        Trener sacuvan = trenerRepository.save(trener);
        return TrenerResponse.fromEntity(sacuvan);
    }

    // UC-12 - Brisanje trenera (alternativni tok: sprečiti brisanje ako ima članove)
    @Transactional
    public void obrisiTrenera(Long id) {
        Trener trener = trenerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Trener nije pronađen"));

        if (trener.getClanovi() != null && !trener.getClanovi().isEmpty()) {
            throw new IllegalArgumentException("Trener ima dodeljene članove. Uklonite dodelu ili prebacite članove pre brisanja.");
        }

        trenerRepository.deleteById(id);
    }
}
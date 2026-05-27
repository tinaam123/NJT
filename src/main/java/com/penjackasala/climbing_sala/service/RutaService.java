package com.penjackasala.climbing_sala.service;

import com.penjackasala.climbing_sala.dto.IzmeniRutuRequest;
import com.penjackasala.climbing_sala.dto.KreirajRutuRequest;
import com.penjackasala.climbing_sala.dto.RutaResponse;
import com.penjackasala.climbing_sala.entity.Ruta;
import com.penjackasala.climbing_sala.repository.RutaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RutaService {

    private final RutaRepository rutaRepository;

    public RutaService(RutaRepository rutaRepository) {
        this.rutaRepository = rutaRepository;
    }

    // UC-11 - Kreiranje nove rute
    @Transactional
    public RutaResponse kreirajRutu(KreirajRutuRequest request) {
        Ruta ruta = new Ruta();
        ruta.setNaziv(request.naziv());
        ruta.setTezina(request.tezina());
        ruta.setVisina(request.visina());
        ruta.setLokacija(request.lokacija());
        ruta.setBoja(request.boja());
        ruta.setKategorija(request.kategorija());
        ruta.setDatumPostavljanja(request.datumPostavljanja());
        ruta.setPostavljac(request.postavljac());
        ruta.setAktivna(request.aktivna());

        Ruta sacuvanaRuta = rutaRepository.save(ruta);

        return RutaResponse.fromEntity(sacuvanaRuta);
    }

    // UC-11 - Pregled rute po ID-u
    @Transactional(readOnly = true)
    public RutaResponse pronađiRutu(Long id) {
        Ruta ruta = rutaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Ruta nije pronađena"));

        return RutaResponse.fromEntity(ruta);
    }

    // UC-11 - Lista svih ruta
    @Transactional(readOnly = true)
    public List<RutaResponse> listaRuta() {
        return rutaRepository.findAll()
                .stream()
                .map(RutaResponse::fromEntity)
                .toList();
    }

    // UC-11 - Lista aktivnih ruta
    @Transactional(readOnly = true)
    public List<RutaResponse> listaAktivnihRuta() {
        return rutaRepository.findByAktivnaTrue()
                .stream()
                .map(RutaResponse::fromEntity)
                .toList();
    }

    // UC-11 - Uređivanje rute
    @Transactional
    public RutaResponse izmeniRutu(Long id, IzmeniRutuRequest request) {
        Ruta ruta = rutaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Ruta nije pronađena"));

        if (request.naziv() != null) {
            ruta.setNaziv(request.naziv());
        }
        if (request.tezina() != null) {
            ruta.setTezina(request.tezina());
        }
        if (request.visina() != null) {
            ruta.setVisina(request.visina());
        }
        if (request.lokacija() != null) {
            ruta.setLokacija(request.lokacija());
        }
        if (request.boja() != null) {
            ruta.setBoja(request.boja());
        }
        if (request.kategorija() != null) {
            ruta.setKategorija(request.kategorija());
        }
        if (request.datumPostavljanja() != null) {
            ruta.setDatumPostavljanja(request.datumPostavljanja());
        }
        if (request.datumUklanjanja() != null) {
            ruta.setDatumUklanjanja(request.datumUklanjanja());
        }
        if (request.postavljac() != null) {
            ruta.setPostavljac(request.postavljac());
        }
        if (request.aktivna() != null) {
            ruta.setAktivna(request.aktivna());
        }

        Ruta azuriranaRuta = rutaRepository.save(ruta);

        return RutaResponse.fromEntity(azuriranaRuta);
    }

    // UC-11 - Brisanje rute (sa validacijom pokušaja)
    @Transactional
    public void obrisiRutu(Long id) {
        Ruta ruta = rutaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Ruta nije pronađena"));

        // Alternativni tok 5a - provera pokušaja na ruti
        if (ruta.getPokusaji() != null && !ruta.getPokusaji().isEmpty()) {
            throw new IllegalArgumentException(
                    "Ruta ima " + ruta.getPokusaji().size() +
                            " pokušaja. Prvo uklonite povezane pokušaje ili deaktivirajte rutu."
            );
        }

        rutaRepository.deleteById(id);
    }

    // UC-11 - Deaktiviranje rute (alternativa brisanju)
    @Transactional
    public RutaResponse deaktivirajRutu(Long id) {
        Ruta ruta = rutaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Ruta nije pronađena"));

        ruta.setAktivna(false);
        ruta.setDatumUklanjanja(java.time.LocalDate.now());

        Ruta azuriranaRuta = rutaRepository.save(ruta);

        return RutaResponse.fromEntity(azuriranaRuta);
    }
}
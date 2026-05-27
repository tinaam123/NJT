package com.penjackasala.climbing_sala.controller;

import com.penjackasala.climbing_sala.dto.IzmeniRutuRequest;
import com.penjackasala.climbing_sala.dto.KreirajRutuRequest;
import com.penjackasala.climbing_sala.dto.RutaResponse;
import com.penjackasala.climbing_sala.service.RutaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rute")
public class RutaController {

    private final RutaService rutaService;

    public RutaController(RutaService rutaService) {
        this.rutaService = rutaService;
    }

    // UC-11 - Kreiranje nove rute
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RutaResponse kreirajRutu(@Valid @RequestBody KreirajRutuRequest request) {
        return rutaService.kreirajRutu(request);
    }

    // UC-11 - Pregled rute po ID-u
    @GetMapping("/{id}")
    public RutaResponse prikaziRutu(@PathVariable Long id) {
        return rutaService.pronađiRutu(id);
    }

    // UC-11 - Lista svih ruta
    @GetMapping
    public List<RutaResponse> listaRuta() {
        return rutaService.listaRuta();
    }

    // UC-11 - Lista aktivnih ruta
    @GetMapping("/aktivne/sve")
    public List<RutaResponse> listaAktivnihRuta() {
        return rutaService.listaAktivnihRuta();
    }

    // UC-11 - Uređivanje rute
    @PutMapping("/{id}")
    public RutaResponse izmeniRutu(@PathVariable Long id,
                                   @Valid @RequestBody IzmeniRutuRequest request) {
        return rutaService.izmeniRutu(id, request);
    }

    // UC-11 - Deaktiviranje rute (bez brisanja)
    @PatchMapping("/{id}/deaktiviraj")
    public RutaResponse deaktivirajRutu(@PathVariable Long id) {
        return rutaService.deaktivirajRutu(id);
    }

    // UC-11 - Brisanje rute (sa validacijom)
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void obrisiRutu(@PathVariable Long id) {
        rutaService.obrisiRutu(id);
    }
}
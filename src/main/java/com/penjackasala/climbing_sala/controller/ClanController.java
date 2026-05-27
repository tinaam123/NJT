package com.penjackasala.climbing_sala.controller;

import com.penjackasala.climbing_sala.dto.ClanResponse;
import com.penjackasala.climbing_sala.dto.DodelaTreneraRequest;
import com.penjackasala.climbing_sala.dto.IzmenaClanaRequest;
import com.penjackasala.climbing_sala.dto.RegistracijaClanaRequest;
import com.penjackasala.climbing_sala.dto.TrenerResponse;
import com.penjackasala.climbing_sala.service.ClanService;
import com.penjackasala.climbing_sala.service.TrenerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clanovi")
public class ClanController {

    private final ClanService clanService;
    private final TrenerService trenerService;

    public ClanController(ClanService clanService, TrenerService trenerService) {
        this.clanService = clanService;
        this.trenerService = trenerService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClanResponse registrujClana(@Valid @RequestBody RegistracijaClanaRequest request) {
        return clanService.registrujClana(request);
    }

    @GetMapping("/pretraga")
    public List<ClanResponse> pretraziClanove(@RequestParam String kriterijum) {
        return clanService.pretraziClanove(kriterijum);
    }

    @GetMapping("/{id}")
    public ClanResponse prikaziProfilClana(@PathVariable Long id) {
        return clanService.pronadjiClana(id);
    }

    @PutMapping("/{id}")
    public ClanResponse izmeniClana(@PathVariable Long id, @Valid @RequestBody IzmenaClanaRequest request) {
        return clanService.izmeniClana(id, request);
    }

    // UC-10 - Prikaz dostupnih trenera
    @GetMapping("/{id}/dostupniTreneri")
    public List<TrenerResponse> prikaziDostupneTrenere() {
        return trenerService.listaDostupnihTrenera();
    }

    // UC-10 - Dodela trenera članu
    @PostMapping("/{id}/dodelaTrenera")
    public ClanResponse dodeliTrenera(@PathVariable Long id,
                                      @Valid @RequestBody DodelaTreneraRequest request) {
        return clanService.dodeliTrenera(id, request.idTrenera());
    }

    // UC-10 - Uklanjanje trenera sa člana
    @DeleteMapping("/{id}/trenera")
    public ClanResponse ukloniTrenera(@PathVariable Long id) {
        return clanService.ukloniTrenera(id);
    }
}
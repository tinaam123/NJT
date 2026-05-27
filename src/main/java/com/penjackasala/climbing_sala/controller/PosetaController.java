package com.penjackasala.climbing_sala.controller;

import com.penjackasala.climbing_sala.dto.EvidentiranjeUlaskaRequest;
import com.penjackasala.climbing_sala.dto.IstorijaPosetaResponse;
import com.penjackasala.climbing_sala.dto.PosetaDetaljiResponse;
import com.penjackasala.climbing_sala.dto.PosetaResponse;
import com.penjackasala.climbing_sala.service.PosetaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clanovi/{clanId}/posete")
public class PosetaController {

    private final PosetaService posetaService;

    public PosetaController(PosetaService posetaService) {
        this.posetaService = posetaService;
    }

    @PostMapping("/ulazak")
    @ResponseStatus(HttpStatus.CREATED)
    public PosetaResponse evidentirajUlazak(
            @PathVariable Long clanId,
            @Valid @RequestBody EvidentiranjeUlaskaRequest request
    ) {
        return posetaService.evidentirajUlazak(clanId, request);
    }

    @PostMapping("/izlazak")
    public PosetaResponse evidentirajIzlazak(@PathVariable Long clanId) {
        return posetaService.evidentirajIzlazak(clanId);
    }

    @GetMapping
    public IstorijaPosetaResponse pregledIstorijePoseta(@PathVariable Long clanId) {
        return posetaService.pregledIstorijePoseta(clanId);
    }

    @GetMapping("/{posetaId:\\d+}")
    public PosetaDetaljiResponse pregledDetaljaPosete(
            @PathVariable Long clanId,
            @PathVariable Long posetaId
    ) {
        return posetaService.pregledDetaljaPosete(clanId, posetaId);
    }
}
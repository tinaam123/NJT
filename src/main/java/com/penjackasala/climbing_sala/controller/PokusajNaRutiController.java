package com.penjackasala.climbing_sala.controller;

import com.penjackasala.climbing_sala.dto.NapredakClanaResponse;
import com.penjackasala.climbing_sala.dto.PokusajNaRutiRequest;
import com.penjackasala.climbing_sala.dto.PokusajNaRutiResponse;
import com.penjackasala.climbing_sala.enums.KategorijaRute;
import com.penjackasala.climbing_sala.service.PokusajNaRutiService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/clanovi/{clanId}")
public class PokusajNaRutiController {

    private final PokusajNaRutiService pokusajService;

    public PokusajNaRutiController(PokusajNaRutiService pokusajService) {
        this.pokusajService = pokusajService;
    }

    @PostMapping("/posete/aktivna/pokusaji")
    @ResponseStatus(HttpStatus.CREATED)
    public PokusajNaRutiResponse dodajPokusaj(
            @PathVariable Long clanId,
            @Valid @RequestBody PokusajNaRutiRequest request
    ) {
        return pokusajService.dodajPokusajNaAktivnojPoseti(clanId, request);
    }

    @GetMapping("/pokusaji")
    public NapredakClanaResponse pregledNapretka(
            @PathVariable Long clanId,
            @RequestParam(required = false) KategorijaRute kategorija,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate datumOd,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate datumDo,
            @RequestParam(required = false) Boolean savladana
    ) {
        return pokusajService.pregledNapretka(clanId, kategorija, datumOd, datumDo, savladana);
    }
}
package com.penjackasala.climbing_sala.controller;

import com.penjackasala.climbing_sala.dto.ClanarinaResponse;
import com.penjackasala.climbing_sala.dto.DodavanjeClanarineRequest;
import com.penjackasala.climbing_sala.dto.StatusClanarineClanaResponse;
import com.penjackasala.climbing_sala.service.ClanarinaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clanovi/{clanId}/clanarine")
public class ClanarinaController {

    private final ClanarinaService clanarinaService;

    public ClanarinaController(ClanarinaService clanarinaService) {
        this.clanarinaService = clanarinaService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClanarinaResponse dodajClanarinu(
            @PathVariable Long clanId,
            @Valid @RequestBody DodavanjeClanarineRequest request
    ) {
        return clanarinaService.dodajClanarinu(clanId, request);
    }

    @GetMapping("/status")
    public StatusClanarineClanaResponse pregledStatusaClanarine(@PathVariable Long clanId) {
        return clanarinaService.pregledStatusaClanarine(clanId);
    }
}
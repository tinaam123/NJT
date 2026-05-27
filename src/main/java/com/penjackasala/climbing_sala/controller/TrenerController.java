package com.penjackasala.climbing_sala.controller;

import com.penjackasala.climbing_sala.dto.IzmeniTreneraRequest;
import com.penjackasala.climbing_sala.dto.KreirajTreneraRequest;
import com.penjackasala.climbing_sala.dto.TrenerResponse;
import com.penjackasala.climbing_sala.service.TrenerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/treneri")
public class TrenerController {

    private final TrenerService trenerService;

    public TrenerController(TrenerService trenerService) {
        this.trenerService = trenerService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TrenerResponse kreirajTrenera(@Valid @RequestBody KreirajTreneraRequest request) {
        return trenerService.kreirajTrenera(request);
    }

    @GetMapping
    public List<TrenerResponse> listaDostupnihTrenera() {
        return trenerService.listaDostupnihTrenera();
    }

    @GetMapping("/{id}")
    public TrenerResponse prikaziTrenera(@PathVariable Long id) {
        return trenerService.pronadjiTrenera(id);
    }

    // UC-12 - Uređivanje trenera (partial update)
    @PutMapping("/{id}")
    public TrenerResponse izmeniTrenera(@PathVariable Long id,
                                        @RequestBody IzmeniTreneraRequest request) {
        return trenerService.izmeniTrenera(id, request);
    }

    // UC-12 - Brisanje trenera
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void obrisiTrenera(@PathVariable Long id) {
        trenerService.obrisiTrenera(id);
    }
}
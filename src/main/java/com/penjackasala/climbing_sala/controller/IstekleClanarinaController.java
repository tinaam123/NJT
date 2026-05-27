package com.penjackasala.climbing_sala.controller;

import com.penjackasala.climbing_sala.enums.FilterTip;
import com.penjackasala.climbing_sala.dto.IsteklihClanarinaRequest;
import com.penjackasala.climbing_sala.dto.IstekleClanarinaListaResponse;
import com.penjackasala.climbing_sala.service.IstekleClanarinaService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clanarine/istekle")
public class IstekleClanarinaController {

    private final IstekleClanarinaService istekleClanarinaService;

    public IstekleClanarinaController(IstekleClanarinaService istekleClanarinaService) {
        this.istekleClanarinaService = istekleClanarinaService;
    }

    @GetMapping
    public IstekleClanarinaListaResponse pregledIsteklihIClanarina(
            @RequestParam FilterTip filterTip,
            @RequestParam(required = false, defaultValue = "30") Integer brojiDana
    ) {
        IsteklihClanarinaRequest request = new IsteklihClanarinaRequest(filterTip, brojiDana);
        return istekleClanarinaService.pregledIsteklihIClanarina(request);
    }
}
package com.penjackasala.climbing_sala.service;

import com.penjackasala.climbing_sala.enums.FilterTip;
import com.penjackasala.climbing_sala.dto.IsteklaClanarinaResponse;
import com.penjackasala.climbing_sala.dto.IsteklihClanarinaRequest;
import com.penjackasala.climbing_sala.dto.IstekleClanarinaListaResponse;
import com.penjackasala.climbing_sala.entity.Clanarina;
import com.penjackasala.climbing_sala.repository.ClanarinaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class IstekleClanarinaService {

    private final ClanarinaRepository clanarinaRepository;

    public IstekleClanarinaService(ClanarinaRepository clanarinaRepository) {
        this.clanarinaRepository = clanarinaRepository;
    }

    @Transactional(readOnly = true)
    public IstekleClanarinaListaResponse pregledIsteklihIClanarina(IsteklihClanarinaRequest request) {
        LocalDate sada = LocalDate.now();
        List<Clanarina> clanarine = new java.util.ArrayList<>();

        if (request.filterTip() == FilterTip.ISTEKLE) {
            // Preuzmi sve istekle članarine
            clanarine = clanarinaRepository.findByDatumIstekaBeforeAndAktivnaTrue(sada);
        } else if (request.filterTip() == FilterTip.ISTICA) {
            // Preuzmi članarine koje će isteci u narednih N dana
            LocalDate datumDo = sada.plusDays(request.brojiDana() != null ? request.brojiDana() : 30);
            clanarine = clanarinaRepository.findByDatumIstekaBetweenAndAktivnaTrue(sada, datumDo);
        }

        List<IsteklaClanarinaResponse> odgovori = clanarine.stream()
                .map(clanarina -> IsteklaClanarinaResponse.fromEntity(
                        clanarina.getClan(),
                        clanarina,
                        sada
                ))
                .sorted((c1, c2) -> Long.compare(Math.abs(c1.danaDo()), Math.abs(c2.danaDo())))
                .collect(Collectors.toList());

        return new IstekleClanarinaListaResponse(
                odgovori,
                odgovori.size(),
                request.filterTip(),
                request.brojiDana() != null ? request.brojiDana() : 30
        );
    }
}
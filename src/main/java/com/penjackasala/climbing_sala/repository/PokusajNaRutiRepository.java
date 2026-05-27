package com.penjackasala.climbing_sala.repository;

import com.penjackasala.climbing_sala.entity.PokusajNaRuti;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface PokusajNaRutiRepository
        extends JpaRepository<PokusajNaRuti, Long>, JpaSpecificationExecutor<PokusajNaRuti> {

    List<PokusajNaRuti> findByPosetaIdOrderByDatumDesc(Long posetaId);
}
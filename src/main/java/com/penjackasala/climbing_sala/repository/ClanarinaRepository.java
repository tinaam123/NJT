package com.penjackasala.climbing_sala.repository;

import com.penjackasala.climbing_sala.entity.Clanarina;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ClanarinaRepository extends JpaRepository<Clanarina, Long> {

    List<Clanarina> findByClanIdOrderByDatumPocetkaDesc(Long clanId);
    List<Clanarina> findByDatumIstekaBeforeAndAktivnaTrue(LocalDate datum);

    List<Clanarina> findByDatumIstekaBetweenAndAktivnaTrue(LocalDate datumOd, LocalDate datumDo);

    boolean existsByClanIdAndDatumPocetkaLessThanEqualAndDatumIstekaGreaterThanEqual(
            Long clanId,
            LocalDate datumPocetka,
            LocalDate datumIsteka
    );
}
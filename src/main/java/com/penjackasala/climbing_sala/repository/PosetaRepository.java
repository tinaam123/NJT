package com.penjackasala.climbing_sala.repository;

import com.penjackasala.climbing_sala.entity.Poseta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PosetaRepository extends JpaRepository<Poseta, Long> {

    Optional<Poseta> findFirstByClanIdAndVremeIzlaskaIsNullOrderByVremeUlaskaDesc(Long clanId);

    List<Poseta> findByClanIdOrderByVremeUlaskaDesc(Long clanId);

    /**
     * Find visits that overlap with the given period [start, end].
     * We consider a visit overlapping if its entry time <= end and its exit time is null or >= start.
     */
    @Query("select p from Poseta p " +
            "where p.vremeUlaska <= :end " +
            "and (p.vremeIzlaska is null or p.vremeIzlaska >= :start)")
    List<Poseta> findPoseteUPeriodu(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
}
package com.penjackasala.climbing_sala.repository;

import com.penjackasala.climbing_sala.entity.Clan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClanRepository extends JpaRepository<Clan, Long> {
    boolean existsByEmail(String email);

    Optional<Clan> findByEmail(String email);

    List<Clan> findByImeContainingIgnoreCaseOrPrezimeContainingIgnoreCase(String ime, String prezime);
}
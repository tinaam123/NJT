package com.penjackasala.climbing_sala.repository;

import com.penjackasala.climbing_sala.entity.Trener;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrenerRepository extends JpaRepository<Trener, Long> {

    List<Trener> findByAktivanTrueOrderByOcenaDesc();
}
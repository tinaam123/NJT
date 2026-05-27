package com.penjackasala.climbing_sala.repository;

import com.penjackasala.climbing_sala.entity.Ruta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
public interface RutaRepository extends JpaRepository<Ruta, Long> {
    List<Ruta> findByAktivnaTrue();
}

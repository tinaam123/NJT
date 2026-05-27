package com.penjackasala.climbing_sala.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "pokusaj_na_ruti")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PokusajNaRuti {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate datum;

    @Column(nullable = false)
    private Boolean savladana;

    @Column(nullable = false)
    private Integer brPokusaja;

    private String napomena;

    @ManyToOne(optional = false)
    @JoinColumn(name = "clan_id", nullable = false)
    private Clan clan;

    @ManyToOne(optional = false)
    @JoinColumn(name = "ruta_id", nullable = false)
    private Ruta ruta;

    @ManyToOne
    @JoinColumn(name = "poseta_id")
    private Poseta poseta;
}
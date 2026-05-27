package com.penjackasala.climbing_sala.entity;

import com.penjackasala.climbing_sala.enums.TipClanarine;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "clanarina")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Clanarina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipClanarine tip;

    @Column(nullable = false)
    private LocalDate datumPocetka;

    @Column(nullable = false)
    private LocalDate datumIsteka;

    @Column(nullable = false)
    private BigDecimal iznos;

    @Column(nullable = false)
    private Boolean aktivna;

    private String napomena;

    @ManyToOne(optional = false)
    @JoinColumn(name = "clan_id", nullable = false)
    private Clan clan;
}
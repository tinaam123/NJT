package com.penjackasala.climbing_sala.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
@Entity
@Table(name = "poseta")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Poseta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime vremeUlaska;

    private LocalDateTime vremeIzlaska;

    private Integer trajanje;

    private Boolean upisnina;

    private Boolean opremaNajam;

    private BigDecimal iznosNajma;

    private Boolean pratilac;

    private String napomena;

    @ManyToOne(optional = false)
    @JoinColumn(name = "clan_id", nullable = false)
    private Clan clan;

    @OneToMany(mappedBy = "poseta", cascade = CascadeType.ALL)
    private List<PokusajNaRuti> pokusaji;
}

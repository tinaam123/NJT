package com.penjackasala.climbing_sala.entity;



import com.penjackasala.climbing_sala.enums.KategorijaRute;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "ruta")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Ruta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String naziv;

    private String tezina;

    private Integer visina;

    private String lokacija;

    private String boja;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private KategorijaRute kategorija;

    private LocalDate datumPostavljanja;

    private LocalDate datumUklanjanja;

    private String postavljac;

    @Column(nullable = false)
    private Boolean aktivna;

    @OneToMany(mappedBy = "ruta", cascade = CascadeType.ALL)
    private List<PokusajNaRuti> pokusaji;
}
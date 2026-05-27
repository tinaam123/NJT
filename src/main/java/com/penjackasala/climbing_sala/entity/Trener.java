package com.penjackasala.climbing_sala.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "trener")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Trener {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String ime;

    @Column(nullable = false)
    private String prezime;

    private String specijalizacija;

    @Column(nullable = false)
    private Boolean aktivan;

    private Double ocena;

    private String email;

    private String telefon;

    private LocalDate datumZaposlenja;

    @OneToMany(mappedBy = "trener")
    private List<Clan> clanovi;
}
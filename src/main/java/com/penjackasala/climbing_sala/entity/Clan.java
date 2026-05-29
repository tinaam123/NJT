package com.penjackasala.climbing_sala.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "clan")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Clan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String ime;

    @Column(nullable = false)
    private String prezime;

    @Column(unique = true)
    private String email;

    private LocalDate datumRodjenja;

    private LocalDate datumUclanjanja;

    private String adresa;

    private String telefon;

    private String kontaktOsoba;

    private String kontaktTelefon;

    private String krvnaGrupa;


    @Column(nullable = false)
    private Boolean aktivan;

    @OneToMany(mappedBy = "clan", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Clanarina> clanarine;

    @OneToMany(mappedBy = "clan", cascade = CascadeType.ALL)
    private List<Poseta> posete;

    @Column(unique = true)
    private String username;

    private String password;

    @Enumerated(EnumType.STRING)
    private com.penjackasala.climbing_sala.enums.RoleKorisnika role;

    @ManyToOne
    @JoinColumn(name = "trener_id")
    private Trener trener;
}
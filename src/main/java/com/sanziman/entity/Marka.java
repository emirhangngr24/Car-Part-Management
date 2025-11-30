package com.sanziman.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "markalar")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Marka {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String ad;

    @Column(length = 1000)
    private String aciklama;

    private String logoUrl;

    @OneToMany(mappedBy = "marka", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Parca> parcalar = new ArrayList<>();

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime olusturmaTarihi;

    @UpdateTimestamp
    private LocalDateTime guncellemeTarihi;

    private Boolean aktif = true;
}
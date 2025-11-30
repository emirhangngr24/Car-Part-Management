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
@Table(name = "parcalar")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Parca {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String baslik;

    @Column(length = 5000)
    private String aciklama;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "marka_id", nullable = false)
    private Marka marka;

    @ElementCollection
    @CollectionTable(name = "parca_fotograflar", joinColumns = @JoinColumn(name = "parca_id"))
    @Column(name = "foto_url")
    private List<String> fotografUrls = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "parca_videolar", joinColumns = @JoinColumn(name = "parca_id"))
    @Column(name = "video_url")
    private List<String> videoUrls = new ArrayList<>();

    private String stokKodu;

    private Double fiyat;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime olusturmaTarihi;

    @UpdateTimestamp
    private LocalDateTime guncellemeTarihi;

    private Boolean aktif = true;

    private Integer siralamaIndex = 0;
}
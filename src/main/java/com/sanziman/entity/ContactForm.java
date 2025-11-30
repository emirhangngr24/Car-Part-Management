package com.sanziman.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "contact_forms")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContactForm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String adSoyad;

    @Column(nullable = false)
    private String email;

    private String telefon;

    @Column(nullable = false)
    private String konu;

    @Column(nullable = false, length = 2000)
    private String mesaj;

    @Column(nullable = false)
    private LocalDateTime gonderimTarihi;

    @Column(nullable = false)
    private Boolean okundu = false;

    @PrePersist
    protected void onCreate() {
        gonderimTarihi = LocalDateTime.now();
    }
}
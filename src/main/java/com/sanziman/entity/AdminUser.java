package com.sanziman.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "admin_users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String kullaniciAdi;

    @Column(nullable = false)
    private String sifre;

    @Column(nullable = false)
    private String email;

    private String adSoyad;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime olusturmaTarihi;

    private Boolean aktif = true;

    @Column(nullable = false)
    private String rol = "ADMIN"; // Şimdilik hep ADMIN
}
package com.sanziman.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "site_content")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SiteContent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String contentKey;

    @Column(columnDefinition = "TEXT")
    private String contentValue;

    @Column(length = 50)
    private String contentType;

    @Column(length = 100)
    private String category;

    @Column(length = 200)
    private String description;

    private LocalDateTime guncellemeTarihi;

    @PreUpdate
    protected void onUpdate() {
        guncellemeTarihi = LocalDateTime.now();
    }
}
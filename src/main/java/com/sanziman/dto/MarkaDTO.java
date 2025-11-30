package com.sanziman.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MarkaDTO {
    private Long id;
    private String ad;
    private String aciklama;
    private String logoUrl;
    private Boolean aktif;
    private LocalDateTime olusturmaTarihi;
    private Integer parcaSayisi; // Kaç parça var bu markada
}
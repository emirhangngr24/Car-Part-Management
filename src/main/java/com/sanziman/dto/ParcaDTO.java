package com.sanziman.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParcaDTO {
    private Long id;
    private String baslik;
    private String aciklama;
    private Long markaId;
    private String markaAd;
    private List<String> fotografUrls;
    private List<String> videoUrls;
    private String stokKodu;
    private Double fiyat;
    private Boolean aktif;
    private Integer siralamaIndex;
    private LocalDateTime olusturmaTarihi;
}
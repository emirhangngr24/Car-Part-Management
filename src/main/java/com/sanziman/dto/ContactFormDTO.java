package com.sanziman.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContactFormDTO {
    private Long id;
    private String adSoyad;
    private String email;
    private String telefon;
    private String konu;
    private String mesaj;
    private LocalDateTime gonderimTarihi;
    private Boolean okundu;
}
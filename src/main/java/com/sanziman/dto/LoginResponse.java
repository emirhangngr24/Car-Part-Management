package com.sanziman.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    private String token;
    private String tokenType = "Bearer";
    private String kullaniciAdi;
    private String email;
    private String adSoyad;

    public LoginResponse(String token, String kullaniciAdi, String email, String adSoyad) {
        this.token = token;
        this.kullaniciAdi = kullaniciAdi;
        this.email = email;
        this.adSoyad = adSoyad;
    }
}
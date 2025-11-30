package com.sanziman.service;

import com.sanziman.dto.LoginRequest;
import com.sanziman.dto.LoginResponse;
import com.sanziman.entity.AdminUser;
import com.sanziman.repository.AdminUserRepository;
import com.sanziman.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private AdminUserRepository adminUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    public LoginResponse login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getKullaniciAdi(),
                        loginRequest.getSifre()
                )
        );

        String token = jwtUtil.generateToken(loginRequest.getKullaniciAdi());

        AdminUser adminUser = adminUserRepository.findByKullaniciAdi(loginRequest.getKullaniciAdi())
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı"));

        return new LoginResponse(token, adminUser.getKullaniciAdi(), adminUser.getEmail(), adminUser.getAdSoyad());
    }

    public AdminUser register(AdminUser adminUser) {
        if (adminUserRepository.existsByKullaniciAdi(adminUser.getKullaniciAdi())) {
            throw new RuntimeException("Bu kullanıcı adı zaten kullanılıyor");
        }

        if (adminUserRepository.existsByEmail(adminUser.getEmail())) {
            throw new RuntimeException("Bu email zaten kullanılıyor");
        }

        adminUser.setSifre(passwordEncoder.encode(adminUser.getSifre()));

        return adminUserRepository.save(adminUser);
    }
}
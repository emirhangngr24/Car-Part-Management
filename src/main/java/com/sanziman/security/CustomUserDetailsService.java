package com.sanziman.security;

import com.sanziman.entity.AdminUser;
import com.sanziman.repository.AdminUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private AdminUserRepository adminUserRepository;

    @Override
    public UserDetails loadUserByUsername(String kullaniciAdi) throws UsernameNotFoundException {
        AdminUser adminUser = adminUserRepository.findByKullaniciAdi(kullaniciAdi)
                .orElseThrow(() -> new UsernameNotFoundException("Kullanıcı bulunamadı: " + kullaniciAdi));

        if (!adminUser.getAktif()) {
            throw new UsernameNotFoundException("Kullanıcı aktif değil: " + kullaniciAdi);
        }

        return User.builder()
                .username(adminUser.getKullaniciAdi())
                .password(adminUser.getSifre())
                .authorities(Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + adminUser.getRol())))
                .build();
    }
}
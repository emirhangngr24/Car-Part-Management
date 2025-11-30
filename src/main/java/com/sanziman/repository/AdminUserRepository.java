package com.sanziman.repository;

import com.sanziman.entity.AdminUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminUserRepository extends JpaRepository<AdminUser, Long> {

    Optional<AdminUser> findByKullaniciAdi(String kullaniciAdi);

    Optional<AdminUser> findByEmail(String email);

    boolean existsByKullaniciAdi(String kullaniciAdi);

    boolean existsByEmail(String email);
}
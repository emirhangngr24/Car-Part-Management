package com.sanziman.repository;

import com.sanziman.entity.ContactForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactFormRepository extends JpaRepository<ContactForm, Long> {

    // En yeni mesajları önce getir
    List<ContactForm> findAllByOrderByGonderimTarihiDesc();

    // Okunmamış mesajları getir
    List<ContactForm> findByOkunduFalseOrderByGonderimTarihiDesc();

    // Okunmamış mesaj sayısı
    long countByOkunduFalse();
}
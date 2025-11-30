package com.sanziman.repository;

import com.sanziman.entity.Marka;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MarkaRepository extends JpaRepository<Marka, Long> {

    Optional<Marka> findByAd(String ad);

    List<Marka> findByAktifTrue();

    boolean existsByAd(String ad);
}
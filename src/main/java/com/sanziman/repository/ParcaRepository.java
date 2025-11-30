package com.sanziman.repository;

import com.sanziman.entity.Parca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParcaRepository extends JpaRepository<Parca, Long> {

    List<Parca> findByMarkaId(Long markaId);

    List<Parca> findByMarkaIdAndAktifTrue(Long markaId);

    List<Parca> findByAktifTrueOrderBySiralamaIndexAsc();

    List<Parca> findByMarkaIdOrderBySiralamaIndexAsc(Long markaId);
}
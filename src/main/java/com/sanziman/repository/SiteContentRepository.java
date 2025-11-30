package com.sanziman.repository;

import com.sanziman.entity.SiteContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SiteContentRepository extends JpaRepository<SiteContent, Long> {

    Optional<SiteContent> findByContentKey(String contentKey);

    List<SiteContent> findByCategory(String category);

    List<SiteContent> findAllByOrderByCategoryAscContentKeyAsc();
}
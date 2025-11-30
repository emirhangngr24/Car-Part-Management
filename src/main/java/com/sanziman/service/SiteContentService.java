package com.sanziman.service;

import com.sanziman.dto.SiteContentDTO;
import com.sanziman.entity.SiteContent;
import com.sanziman.repository.SiteContentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SiteContentService {

    private final SiteContentRepository repository;

    public List<SiteContentDTO> getAllContent() {
        return repository.findAllByOrderByCategoryAscContentKeyAsc()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<SiteContentDTO> getContentByCategory(String category) {
        return repository.findByCategory(category)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public String getContentValue(String key) {
        return repository.findByContentKey(key)
                .map(SiteContent::getContentValue)
                .orElse("");
    }

    public Map<String, String> getAllContentMap() {
        return repository.findAll()
                .stream()
                .collect(Collectors.toMap(
                        SiteContent::getContentKey,
                        SiteContent::getContentValue
                ));
    }

    @Transactional
    public SiteContentDTO updateContent(Long id, SiteContentDTO dto) {
        SiteContent content = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("İçerik bulunamadı"));

        content.setContentValue(dto.getContentValue());
        content.setGuncellemeTarihi(LocalDateTime.now());

        SiteContent updated = repository.save(content);
        return convertToDTO(updated);
    }

    @Transactional
    public void updateMultiple(List<SiteContentDTO> contents) {
        contents.forEach(dto -> {
            repository.findById(dto.getId()).ifPresent(content -> {
                content.setContentValue(dto.getContentValue());
                content.setGuncellemeTarihi(LocalDateTime.now());
                repository.save(content);
            });
        });
    }
    @Transactional
    public SiteContentDTO updateOrCreateByKey(String key, String value) {
        Optional<SiteContent> existing = repository.findByContentKey(key);

        if (existing.isPresent()) {
            SiteContent content = existing.get();
            content.setContentValue(value);
            content.setGuncellemeTarihi(LocalDateTime.now());
            return convertToDTO(repository.save(content));
        } else {
            SiteContent newContent = new SiteContent();
            newContent.setContentKey(key);
            newContent.setContentValue(value);
            newContent.setContentType("text");

            String category = key.split("_")[0];
            newContent.setCategory(category);
            newContent.setDescription("Auto-created");
            newContent.setGuncellemeTarihi(LocalDateTime.now());

            return convertToDTO(repository.save(newContent));
        }
    }

    private SiteContentDTO convertToDTO(SiteContent content) {
        SiteContentDTO dto = new SiteContentDTO();
        dto.setId(content.getId());
        dto.setContentKey(content.getContentKey());
        dto.setContentValue(content.getContentValue());
        dto.setContentType(content.getContentType());
        dto.setCategory(content.getCategory());
        dto.setDescription(content.getDescription());
        dto.setGuncellemeTarihi(content.getGuncellemeTarihi());
        return dto;
    }
}
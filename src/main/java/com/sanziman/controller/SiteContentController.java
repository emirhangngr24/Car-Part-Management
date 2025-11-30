package com.sanziman.controller;

import com.sanziman.dto.SiteContentDTO;
import com.sanziman.service.SiteContentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class SiteContentController {

    private final SiteContentService service;

    @GetMapping("/public/site-content")
    public ResponseEntity<Map<String, String>> getAllContentPublic() {
        return ResponseEntity.ok(service.getAllContentMap());
    }

    @GetMapping("/public/site-content/category/{category}")
    public ResponseEntity<List<SiteContentDTO>> getByCategory(@PathVariable String category) {
        return ResponseEntity.ok(service.getContentByCategory(category));
    }

    @GetMapping("/admin/site-content")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<SiteContentDTO>> getAllContent() {
        return ResponseEntity.ok(service.getAllContent());
    }

    @PutMapping("/admin/site-content/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SiteContentDTO> updateContent(
            @PathVariable Long id,
            @RequestBody SiteContentDTO dto
    ) {
        return ResponseEntity.ok(service.updateContent(id, dto));
    }

    @PutMapping("/admin/site-content/bulk")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> updateMultiple(@RequestBody List<SiteContentDTO> contents) {
        service.updateMultiple(contents);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/public/site-content/{key}")
    public ResponseEntity<String> getContentByKey(@PathVariable String key) {
        String value = service.getContentValue(key);
        return ResponseEntity.ok(value);
    }

    @PutMapping("/admin/site-content/key/{key}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SiteContentDTO> updateOrCreate(
            @PathVariable String key,
            @RequestBody Map<String, String> payload
    ) {
        return ResponseEntity.ok(service.updateOrCreateByKey(key, payload.get("value")));
    }
}
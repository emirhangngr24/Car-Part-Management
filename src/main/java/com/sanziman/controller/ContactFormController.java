package com.sanziman.controller;

import com.sanziman.dto.ContactFormDTO;
import com.sanziman.service.ContactFormService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/contact")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class ContactFormController {

    private final ContactFormService contactFormService;

    //kullanıcılar form gönderebilir
    @PostMapping("/send")
    public ResponseEntity<?> sendContactForm(@RequestBody ContactFormDTO dto) {
        Map<String, Object> response = new HashMap<>();

        try {
            // Validasyon
            if (dto.getAdSoyad() == null || dto.getAdSoyad().isEmpty() ||
                    dto.getEmail() == null || dto.getEmail().isEmpty() ||
                    dto.getKonu() == null || dto.getKonu().isEmpty() ||
                    dto.getMesaj() == null || dto.getMesaj().isEmpty()) {

                response.put("success", false);
                response.put("message", "Lütfen tüm zorunlu alanları doldurun!");
                return ResponseEntity.badRequest().body(response);
            }

            ContactFormDTO saved = contactFormService.saveContactForm(dto);

            response.put("success", true);
            response.put("message", "Mesajınız başarıyla gönderildi!");
            response.put("data", saved);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            e.printStackTrace();
            response.put("success", false);
            response.put("message", "Mesaj gönderilemedi. Lütfen tekrar deneyin.");
            return ResponseEntity.status(500).body(response);
        }
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ContactFormDTO>> getAllContactForms() {
        return ResponseEntity.ok(contactFormService.getAllContactForms());
    }

    @GetMapping("/unread")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ContactFormDTO>> getUnreadContactForms() {
        return ResponseEntity.ok(contactFormService.getUnreadContactForms());
    }

    @GetMapping("/unread-count")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Long>> getUnreadCount() {
        Map<String, Long> response = new HashMap<>();
        response.put("count", contactFormService.getUnreadCount());
        return ResponseEntity.ok(response);
    }

    // okundu olarak işaretleme
    @PutMapping("/{id}/mark-read")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> markAsRead(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            contactFormService.markAsRead(id);
            response.put("success", true);
            response.put("message", "Mesaj okundu olarak işaretlendi");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(404).body(response);
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteContactForm(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            contactFormService.deleteContactForm(id);
            response.put("success", true);
            response.put("message", "Mesaj silindi");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Mesaj silinemedi");
            return ResponseEntity.status(500).body(response);
        }
    }
}
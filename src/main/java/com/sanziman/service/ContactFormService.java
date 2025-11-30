package com.sanziman.service;

import com.sanziman.dto.ContactFormDTO;
import com.sanziman.entity.ContactForm;
import com.sanziman.repository.ContactFormRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContactFormService {

    private final ContactFormRepository contactFormRepository;

    public ContactFormDTO saveContactForm(ContactFormDTO dto) {
        ContactForm contactForm = new ContactForm();
        contactForm.setAdSoyad(dto.getAdSoyad());
        contactForm.setEmail(dto.getEmail());
        contactForm.setTelefon(dto.getTelefon());
        contactForm.setKonu(dto.getKonu());
        contactForm.setMesaj(dto.getMesaj());

        ContactForm saved = contactFormRepository.save(contactForm);
        return convertToDTO(saved);
    }

    public List<ContactFormDTO> getAllContactForms() {
        return contactFormRepository.findAllByOrderByGonderimTarihiDesc()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<ContactFormDTO> getUnreadContactForms() {
        return contactFormRepository.findByOkunduFalseOrderByGonderimTarihiDesc()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public long getUnreadCount() {
        return contactFormRepository.countByOkunduFalse();
    }

    public void markAsRead(Long id) {
        ContactForm contactForm = contactFormRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mesaj bulunamadı"));
        contactForm.setOkundu(true);
        contactFormRepository.save(contactForm);
    }

    public void deleteContactForm(Long id) {
        contactFormRepository.deleteById(id);
    }

    private ContactFormDTO convertToDTO(ContactForm entity) {
        ContactFormDTO dto = new ContactFormDTO();
        dto.setId(entity.getId());
        dto.setAdSoyad(entity.getAdSoyad());
        dto.setEmail(entity.getEmail());
        dto.setTelefon(entity.getTelefon());
        dto.setKonu(entity.getKonu());
        dto.setMesaj(entity.getMesaj());
        dto.setGonderimTarihi(entity.getGonderimTarihi());
        dto.setOkundu(entity.getOkundu());
        return dto;
    }
}
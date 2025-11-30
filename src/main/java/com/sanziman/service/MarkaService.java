package com.sanziman.service;

import com.sanziman.dto.MarkaDTO;
import com.sanziman.entity.Marka;
import com.sanziman.repository.MarkaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MarkaService {

    @Autowired
    private MarkaRepository markaRepository;

    @Autowired
    private CloudinaryService cloudinaryService;

    public List<MarkaDTO> getAllMarkalar() {
        return markaRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<MarkaDTO> getAktifMarkalar() {
        return markaRepository.findByAktifTrue().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public MarkaDTO getMarkaById(Long id) {
        Marka marka = markaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Marka bulunamadı: " + id));
        return convertToDTO(marka);
    }

    public MarkaDTO createMarka(Marka marka, MultipartFile logo) throws IOException {
        if (markaRepository.existsByAd(marka.getAd())) {
            throw new RuntimeException("Bu marka adı zaten mevcut");
        }

        if (logo != null && !logo.isEmpty()) {
            String logoUrl = cloudinaryService.uploadFile(logo, "markalar");
            marka.setLogoUrl(logoUrl);
        }

        Marka savedMarka = markaRepository.save(marka);
        return convertToDTO(savedMarka);
    }

    public MarkaDTO updateMarka(Long id, Marka markaDetails, MultipartFile logo) throws IOException {
        Marka marka = markaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Marka bulunamadı: " + id));

        marka.setAd(markaDetails.getAd());
        marka.setAciklama(markaDetails.getAciklama());
        marka.setAktif(markaDetails.getAktif());

        if (logo != null && !logo.isEmpty()) {
            if (marka.getLogoUrl() != null) {
                try {
                    String publicId = cloudinaryService.extractPublicId(marka.getLogoUrl());
                    cloudinaryService.deleteFile(publicId);
                } catch (Exception e) {
                    System.out.println("Eski logo silinemedi: " + e.getMessage());
                }
            }
            String logoUrl = cloudinaryService.uploadFile(logo, "markalar");
            marka.setLogoUrl(logoUrl);
        }

        Marka updatedMarka = markaRepository.save(marka);
        return convertToDTO(updatedMarka);
    }

    public void deleteMarka(Long id) {
        Marka marka = markaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Marka bulunamadı: " + id));

        if (marka.getLogoUrl() != null) {
            try {
                String publicId = cloudinaryService.extractPublicId(marka.getLogoUrl());
                cloudinaryService.deleteFile(publicId);
            } catch (Exception e) {
                System.out.println("Logo silinemedi: " + e.getMessage());
            }
        }

        markaRepository.delete(marka);
    }

    private MarkaDTO convertToDTO(Marka marka) {
        MarkaDTO dto = new MarkaDTO();
        dto.setId(marka.getId());
        dto.setAd(marka.getAd());
        dto.setAciklama(marka.getAciklama());
        dto.setLogoUrl(marka.getLogoUrl());
        dto.setAktif(marka.getAktif());
        dto.setOlusturmaTarihi(marka.getOlusturmaTarihi());
        dto.setParcaSayisi(marka.getParcalar() != null ? marka.getParcalar().size() : 0);
        return dto;
    }
}
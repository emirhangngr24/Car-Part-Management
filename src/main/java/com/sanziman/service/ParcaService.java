package com.sanziman.service;

import com.sanziman.dto.ParcaDTO;
import com.sanziman.entity.Marka;
import com.sanziman.entity.Parca;
import com.sanziman.repository.MarkaRepository;
import com.sanziman.repository.ParcaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ParcaService {

    @Autowired
    private ParcaRepository parcaRepository;

    @Autowired
    private MarkaRepository markaRepository;

    @Autowired
    private CloudinaryService cloudinaryService;

    public List<ParcaDTO> getAllParcalar() {
        return parcaRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<ParcaDTO> getParcalarByMarkaId(Long markaId) {
        return parcaRepository.findByMarkaIdOrderBySiralamaIndexAsc(markaId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<ParcaDTO> getAktifParcalar() {
        return parcaRepository.findByAktifTrueOrderBySiralamaIndexAsc().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public ParcaDTO getParcaById(Long id) {
        Parca parca = parcaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Parça bulunamadı: " + id));
        return convertToDTO(parca);
    }

    public ParcaDTO createParca(Parca parca, Long markaId, List<MultipartFile> fotograflar, List<MultipartFile> videolar) throws IOException {
        Marka marka = markaRepository.findById(markaId)
                .orElseThrow(() -> new RuntimeException("Marka bulunamadı: " + markaId));

        parca.setMarka(marka);

        if (fotograflar != null && !fotograflar.isEmpty()) {
            List<String> fotoUrls = new ArrayList<>();
            for (MultipartFile foto : fotograflar) {
                if (!foto.isEmpty()) {
                    String url = cloudinaryService.uploadFile(foto, "parcalar/fotograflar");
                    fotoUrls.add(url);
                }
            }
            parca.setFotografUrls(fotoUrls);
        }

        if (videolar != null && !videolar.isEmpty()) {
            List<String> videoUrls = new ArrayList<>();
            for (MultipartFile video : videolar) {
                if (!video.isEmpty()) {
                    String url = cloudinaryService.uploadFile(video, "parcalar/videolar");
                    videoUrls.add(url);
                }
            }
            parca.setVideoUrls(videoUrls);
        }

        Parca savedParca = parcaRepository.save(parca);
        return convertToDTO(savedParca);
    }

    public ParcaDTO updateParca(Long id, Parca parcaDetails, List<MultipartFile> yeniFotograflar, List<MultipartFile> yeniVideolar) throws IOException {
        Parca parca = parcaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Parça bulunamadı: " + id));

        parca.setBaslik(parcaDetails.getBaslik());
        parca.setAciklama(parcaDetails.getAciklama());
        parca.setStokKodu(parcaDetails.getStokKodu());
        parca.setFiyat(parcaDetails.getFiyat());
        parca.setAktif(parcaDetails.getAktif());
        parca.setSiralamaIndex(parcaDetails.getSiralamaIndex());

        if (yeniFotograflar != null && !yeniFotograflar.isEmpty()) {
            List<String> yeniFotoUrls = new ArrayList<>(parca.getFotografUrls());
            for (MultipartFile foto : yeniFotograflar) {
                if (!foto.isEmpty()) {
                    String url = cloudinaryService.uploadFile(foto, "parcalar/fotograflar");
                    yeniFotoUrls.add(url);
                }
            }
            parca.setFotografUrls(yeniFotoUrls);
        }

        if (yeniVideolar != null && !yeniVideolar.isEmpty()) {
            List<String> yeniVideoUrls = new ArrayList<>(parca.getVideoUrls());
            for (MultipartFile video : yeniVideolar) {
                if (!video.isEmpty()) {
                    String url = cloudinaryService.uploadFile(video, "parcalar/videolar");
                    yeniVideoUrls.add(url);
                }
            }
            parca.setVideoUrls(yeniVideoUrls);
        }

        Parca updatedParca = parcaRepository.save(parca);
        return convertToDTO(updatedParca);
    }

    public void deleteParca(Long id) {
        Parca parca = parcaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Parça bulunamadı: " + id));

        if (parca.getFotografUrls() != null) {
            for (String url : parca.getFotografUrls()) {
                try {
                    String publicId = cloudinaryService.extractPublicId(url);
                    cloudinaryService.deleteFile(publicId);
                } catch (Exception e) {
                    System.out.println("Fotoğraf silinemedi: " + e.getMessage());
                }
            }
        }

        if (parca.getVideoUrls() != null) {
            for (String url : parca.getVideoUrls()) {
                try {
                    String publicId = cloudinaryService.extractPublicId(url);
                    cloudinaryService.deleteFile(publicId);
                } catch (Exception e) {
                    System.out.println("Video silinemedi: " + e.getMessage());
                }
            }
        }

        parcaRepository.delete(parca);
    }

    public ParcaDTO deleteFotograf(Long parcaId, String fotoUrl) throws IOException {
        Parca parca = parcaRepository.findById(parcaId)
                .orElseThrow(() -> new RuntimeException("Parça bulunamadı: " + parcaId));

        parca.getFotografUrls().remove(fotoUrl);

        String publicId = cloudinaryService.extractPublicId(fotoUrl);
        cloudinaryService.deleteFile(publicId);

        Parca updatedParca = parcaRepository.save(parca);
        return convertToDTO(updatedParca);
    }

    public ParcaDTO deleteVideo(Long parcaId, String videoUrl) throws IOException {
        Parca parca = parcaRepository.findById(parcaId)
                .orElseThrow(() -> new RuntimeException("Parça bulunamadı: " + parcaId));

        parca.getVideoUrls().remove(videoUrl);

        String publicId = cloudinaryService.extractPublicId(videoUrl);
        cloudinaryService.deleteFile(publicId);

        Parca updatedParca = parcaRepository.save(parca);
        return convertToDTO(updatedParca);
    }

    private ParcaDTO convertToDTO(Parca parca) {
        ParcaDTO dto = new ParcaDTO();
        dto.setId(parca.getId());
        dto.setBaslik(parca.getBaslik());
        dto.setAciklama(parca.getAciklama());
        dto.setMarkaId(parca.getMarka().getId());
        dto.setMarkaAd(parca.getMarka().getAd());
        dto.setFotografUrls(parca.getFotografUrls());
        dto.setVideoUrls(parca.getVideoUrls());
        dto.setStokKodu(parca.getStokKodu());
        dto.setFiyat(parca.getFiyat());
        dto.setAktif(parca.getAktif());
        dto.setSiralamaIndex(parca.getSiralamaIndex());
        dto.setOlusturmaTarihi(parca.getOlusturmaTarihi());
        return dto;
    }
}
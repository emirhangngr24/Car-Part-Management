package com.sanziman.controller;

import com.sanziman.dto.ParcaDTO;
import com.sanziman.entity.Parca;
import com.sanziman.service.ParcaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ParcaController {

    @Autowired
    private ParcaService parcaService;
    @GetMapping("/public/parcalar")
    public ResponseEntity<List<ParcaDTO>> getPublicParcalar() {
        return ResponseEntity.ok(parcaService.getAktifParcalar());
    }

    @GetMapping("/public/parcalar/marka/{markaId}")
    public ResponseEntity<List<ParcaDTO>> getPublicParcalarByMarka(@PathVariable Long markaId) {
        return ResponseEntity.ok(parcaService.getParcalarByMarkaId(markaId));
    }

    @GetMapping("/public/parcalar/{id}")
    public ResponseEntity<ParcaDTO> getPublicParcaById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(parcaService.getParcaById(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/admin/parcalar")
    public ResponseEntity<List<ParcaDTO>> getAllParcalar() {
        return ResponseEntity.ok(parcaService.getAllParcalar());
    }

    @GetMapping("/admin/parcalar/{id}")
    public ResponseEntity<ParcaDTO> getParcaById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(parcaService.getParcaById(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/admin/parcalar/marka/{markaId}")
    public ResponseEntity<List<ParcaDTO>> getParcalarByMarkaId(@PathVariable Long markaId) {
        return ResponseEntity.ok(parcaService.getParcalarByMarkaId(markaId));
    }

    @PostMapping("/admin/parcalar")
    public ResponseEntity<ParcaDTO> createParca(
            @RequestParam("baslik") String baslik,
            @RequestParam(value = "aciklama", required = false) String aciklama,
            @RequestParam("markaId") Long markaId,
            @RequestParam(value = "stokKodu", required = false) String stokKodu,
            @RequestParam(value = "fiyat", required = false) Double fiyat,
            @RequestParam(value = "siralamaIndex", defaultValue = "0") Integer siralamaIndex,
            @RequestParam(value = "fotograflar", required = false) List<MultipartFile> fotograflar,
            @RequestParam(value = "videolar", required = false) List<MultipartFile> videolar) {
        try {
            Parca parca = new Parca();
            parca.setBaslik(baslik);
            parca.setAciklama(aciklama);
            parca.setStokKodu(stokKodu);
            parca.setFiyat(fiyat);
            parca.setSiralamaIndex(siralamaIndex);

            ParcaDTO createdParca = parcaService.createParca(parca, markaId, fotograflar, videolar);
            return ResponseEntity.ok(createdParca);
        } catch (IOException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/admin/parcalar/{id}")
    public ResponseEntity<ParcaDTO> updateParca(
            @PathVariable Long id,
            @RequestParam("baslik") String baslik,
            @RequestParam(value = "aciklama", required = false) String aciklama,
            @RequestParam(value = "stokKodu", required = false) String stokKodu,
            @RequestParam(value = "fiyat", required = false) Double fiyat,
            @RequestParam(value = "aktif", defaultValue = "true") Boolean aktif,
            @RequestParam(value = "siralamaIndex", defaultValue = "0") Integer siralamaIndex,
            @RequestParam(value = "fotograflar", required = false) List<MultipartFile> fotograflar,
            @RequestParam(value = "videolar", required = false) List<MultipartFile> videolar) {
        try {
            Parca parca = new Parca();
            parca.setBaslik(baslik);
            parca.setAciklama(aciklama);
            parca.setStokKodu(stokKodu);
            parca.setFiyat(fiyat);
            parca.setAktif(aktif);
            parca.setSiralamaIndex(siralamaIndex);

            ParcaDTO updatedParca = parcaService.updateParca(id, parca, fotograflar, videolar);
            return ResponseEntity.ok(updatedParca);
        } catch (IOException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/admin/parcalar/{id}")
    public ResponseEntity<Void> deleteParca(@PathVariable Long id) {
        try {
            parcaService.deleteParca(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/admin/parcalar/{id}/fotograf")
    public ResponseEntity<ParcaDTO> deleteFotograf(
            @PathVariable Long id,
            @RequestParam("url") String fotoUrl) {
        try {
            ParcaDTO updatedParca = parcaService.deleteFotograf(id, fotoUrl);
            return ResponseEntity.ok(updatedParca);
        } catch (IOException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/admin/parcalar/{id}/video")
    public ResponseEntity<ParcaDTO> deleteVideo(
            @PathVariable Long id,
            @RequestParam("url") String videoUrl) {
        try {
            ParcaDTO updatedParca = parcaService.deleteVideo(id, videoUrl);
            return ResponseEntity.ok(updatedParca);
        } catch (IOException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}

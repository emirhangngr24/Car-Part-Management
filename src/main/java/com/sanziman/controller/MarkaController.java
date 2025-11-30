package com.sanziman.controller;

import com.sanziman.dto.MarkaDTO;
import com.sanziman.entity.Marka;
import com.sanziman.service.MarkaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MarkaController {

    @Autowired
    private MarkaService markaService;

    @GetMapping("/public/markalar")
    public ResponseEntity<List<MarkaDTO>> getPublicMarkalar() {
        return ResponseEntity.ok(markaService.getAktifMarkalar());
    }

    @GetMapping("/admin/markalar")
    public ResponseEntity<List<MarkaDTO>> getAllMarkalar() {
        return ResponseEntity.ok(markaService.getAllMarkalar());
    }

    @GetMapping("/admin/markalar/{id}")
    public ResponseEntity<MarkaDTO> getMarkaById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(markaService.getMarkaById(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/admin/markalar")
    public ResponseEntity<MarkaDTO> createMarka(
            @RequestParam("ad") String ad,
            @RequestParam(value = "aciklama", required = false) String aciklama,
            @RequestParam(value = "logo", required = false) MultipartFile logo) {
        try {
            Marka marka = new Marka();
            marka.setAd(ad);
            marka.setAciklama(aciklama);

            MarkaDTO createdMarka = markaService.createMarka(marka, logo);
            return ResponseEntity.ok(createdMarka);
        } catch (IOException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/admin/markalar/{id}")
    public ResponseEntity<MarkaDTO> updateMarka(
            @PathVariable Long id,
            @RequestParam("ad") String ad,
            @RequestParam(value = "aciklama", required = false) String aciklama,
            @RequestParam(value = "aktif", defaultValue = "true") Boolean aktif,
            @RequestParam(value = "logo", required = false) MultipartFile logo) {
        try {
            Marka marka = new Marka();
            marka.setAd(ad);
            marka.setAciklama(aciklama);
            marka.setAktif(aktif);

            MarkaDTO updatedMarka = markaService.updateMarka(id, marka, logo);
            return ResponseEntity.ok(updatedMarka);
        } catch (IOException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/admin/markalar/{id}")
    public ResponseEntity<Void> deleteMarka(@PathVariable Long id) {
        try {
            markaService.deleteMarka(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}

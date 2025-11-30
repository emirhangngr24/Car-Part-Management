package com.sanziman.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SiteContentDTO {
    private Long id;
    private String contentKey;
    private String contentValue;
    private String contentType;
    private String category;
    private String description;
    private LocalDateTime guncellemeTarihi;
}
package com.company.uniclip.backend.dto;

import lombok.Data;

@Data
public class FileDto {
    private String kind;
    private String id;
    private String name;
    private String mimeType;
    private String link;
}

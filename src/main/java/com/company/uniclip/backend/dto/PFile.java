package com.company.uniclip.backend.dto;

import lombok.Data;

@Data
public class PFile {
    private String kind;
    private String id;
    private String mimeType;
    private String filename;
    private String productUrl;
}

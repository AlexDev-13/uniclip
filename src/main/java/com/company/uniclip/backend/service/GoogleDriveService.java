package com.company.uniclip.backend.service;

import com.company.uniclip.backend.dto.DriveFiles;
import com.company.uniclip.backend.dto.File;
import com.company.uniclip.backend.dto.PhotoFiles;
import com.google.gson.Gson;
import org.springframework.http.*;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class GoogleDriveService {

    private final RestTemplate restTemplate;

    public GoogleDriveService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public DriveFiles getDriveFiles(String accessToken) {
        String requestUri = "https://www.googleapis.com/drive/v3/files";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Bearer " + accessToken);

        HttpEntity request = new HttpEntity(headers);
        MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter =
                new MappingJackson2HttpMessageConverter();
        restTemplate.getMessageConverters().add(mappingJackson2HttpMessageConverter);
        ResponseEntity<String> response = restTemplate.exchange(requestUri, HttpMethod.GET, request, String.class);

        Gson gson = new Gson();
        DriveFiles driveFiles = gson.fromJson(response.getBody(), DriveFiles.class);
        System.out.println(driveFiles);
        List<File> files = new ArrayList<>();
        DriveFiles imageFiles = new DriveFiles();

        for (File file : driveFiles.getFiles()) {
            if (file.getMimeType().equals("image/jpeg") || file.getMimeType().equals("video/mp4") || file.getMimeType().equals("image/png")) {
                files.add(file);
            }
        }
        imageFiles.setFiles(files);

        return imageFiles;
    }

    public PhotoFiles getPhotoFiles(String accessToken) {
        String requestUri = "https://photoslibrary.googleapis.com/v1/mediaItems";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Bearer " + accessToken);

        HttpEntity request = new HttpEntity(headers);
        MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter =
                new MappingJackson2HttpMessageConverter();
        restTemplate.getMessageConverters().add(mappingJackson2HttpMessageConverter);
        ResponseEntity<String> response = restTemplate.exchange(requestUri, HttpMethod.GET, request, String.class);

        Gson gson = new Gson();
        PhotoFiles photoFiles = gson.fromJson(response.getBody(), PhotoFiles.class);
        return photoFiles;
    }
}

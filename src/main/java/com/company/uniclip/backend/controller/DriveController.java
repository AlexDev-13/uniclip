package com.company.uniclip.backend.controller;

import com.company.uniclip.backend.dto.*;
import com.company.uniclip.backend.enums.SessionKey;
import com.company.uniclip.backend.exception.AccessDeniedException;
import com.company.uniclip.backend.service.GoogleDriveService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collection;

@RestController
public class DriveController {

    private final GoogleDriveService googleDriveService;

    public DriveController(GoogleDriveService googleDriveService) {
        this.googleDriveService = googleDriveService;
    }

    @GetMapping("/drive")
    public Collection<FileDto> getDrive(HttpSession session) {
        String accessToken = session.getAttribute(SessionKey.GOOGLE_OAUTH_TOKEN.toString()) == null
                ? "" : session.getAttribute(SessionKey.GOOGLE_OAUTH_TOKEN.toString()).toString();

        if (accessToken == null || accessToken.isBlank()) {
            throw new AccessDeniedException("Invalid token");
        }
        DriveFiles driveFiles = googleDriveService.getDriveFiles(accessToken);

        System.out.println(driveFiles.toString());

        return toDriveMappers(driveFiles.getFiles());
    }

    @GetMapping("/photo")
    public Collection<FileDto> getPhoto(HttpSession session) {
        String accessToken = session.getAttribute(SessionKey.GOOGLE_OAUTH_TOKEN.toString()) == null
                ? "" : session.getAttribute(SessionKey.GOOGLE_OAUTH_TOKEN.toString()).toString();

        if (accessToken == null || accessToken.isBlank()) {
            throw new AccessDeniedException("Invalid token");
        }
        PhotoFiles photoFiles = googleDriveService.getPhotoFiles(accessToken);

        System.out.println(photoFiles.toString());

        return toPhotoMappers(photoFiles.getMediaItems());
    }

    private Collection<FileDto> toDriveMappers(Collection<File> files) {
        Collection<FileDto> fileDtos = new ArrayList<>();

        for (File file : files) {
            fileDtos.add(toDriveMapper(file));
        }
        return fileDtos;
    }

    private FileDto toDriveMapper(File file) {
        FileDto fileDto = new FileDto();
        fileDto.setName(file.getName());
        fileDto.setLink("http://drive.google.com/file/d/"+file.getId()+"/view?usp");
        fileDto.setKind(file.getKind());
        fileDto.setMimeType(file.getMimeType());
        fileDto.setId(file.getId());
        return fileDto;
    }

    private Collection<FileDto> toPhotoMappers(Collection<PFile> files) {
        Collection<FileDto> fileDtos = new ArrayList<>();

        for (PFile file : files) {
            fileDtos.add(toPhotoMapper(file));
        }
        return fileDtos;
    }

    private FileDto toPhotoMapper(PFile file) {
        FileDto fileDto = new FileDto();
        fileDto.setName(file.getFilename());
        fileDto.setKind(file.getKind());
        fileDto.setLink(file.getProductUrl());
        fileDto.setMimeType(file.getMimeType());
        fileDto.setId(file.getId());
        return fileDto;
    }

}

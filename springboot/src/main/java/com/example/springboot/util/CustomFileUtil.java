package com.example.springboot.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import jakarta.annotation.PostConstruct;
import lombok.extern.log4j.Log4j2;
import com.example.springboot.config.UploadProperties;

import net.coobird.thumbnailator.Thumbnails;

@Component
@Log4j2
public class CustomFileUtil {
    private final UploadProperties uploadProperties;

    public CustomFileUtil(UploadProperties uploadProperties) {
        this.uploadProperties = uploadProperties;
    }

    private String uploadPath;

    @PostConstruct
    public void init() {
        uploadPath = uploadProperties.getPath();

        File tempFolder = new File(uploadPath);

        if(tempFolder.exists() == false) {
            tempFolder.mkdir();
        }
        
        log.info("------uploadPath------");
        log.info(uploadPath);
    }

    public List<String> saveFiles(List<MultipartFile> files) throws RuntimeException {
        if(files == null || files.size() == 0) {
            return List.of();
        }

        List<String> uploadNames = new ArrayList<>();

        for (MultipartFile multipartFile : files) {
            String savedName = UUID.randomUUID().toString() + "_" + multipartFile.getOriginalFilename();
            Path savePath = Paths.get(uploadPath, savedName);
            try {
                Files.copy(multipartFile.getInputStream(), savePath);

                String contnetType = multipartFile.getContentType();

                if(contnetType != null && contnetType.startsWith("image")){
                    Path thumbnailPath = Paths.get(uploadPath, "S_" + savedName);

                    Thumbnails.of(savePath.toFile())
                    .size(200,200)
                    .toFile(thumbnailPath.toFile());
                }

                uploadNames.add(savedName);
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage());
            }
        }

        return uploadNames;
    }

    public ResponseEntity<Resource> getFile(String fileName) {
        Resource resource = new FileSystemResource(uploadPath + File.separator + fileName);

        if( ! resource. isReadable() ) {
            resource = new FileSystemResource(uploadPath + File.separator + "base.jpg");
        }

        HttpHeaders headers = new HttpHeaders();
        try{
            headers.add("Content-Type", Files.probeContentType(resource.getFile().toPath()));
        } catch(Exception e) {
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok().headers(headers).body(resource);
    }

    public void deleteFiles(List<String> fileNames) {
        if(fileNames == null || fileNames.size() == 0) {
            return;
        }

        fileNames.forEach(fileName -> {
            String thumbnailFieName = "s_"  + fileName;
            Path thumbnailPath = Paths.get(uploadPath, thumbnailFieName);
            Path filePath = Paths.get(uploadPath, fileName);

            try {
                Files.deleteIfExists(filePath);
                Files.deleteIfExists(thumbnailPath);
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage());
            }

        });
    }
}
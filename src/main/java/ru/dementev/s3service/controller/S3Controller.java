package ru.dementev.s3service.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.dementev.s3service.service.S3Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
@RestController
@Slf4j
public class S3Controller {

    private final S3Service s3Service;

    @GetMapping("/v1/download")
    public ResponseEntity<ByteArrayResource> download(@RequestParam String bucketName, @RequestParam String key) throws IOException {
        ByteBuffer buffer = s3Service.getObject(bucketName, key);
        ByteArrayResource resource = new ByteArrayResource(buffer.array());
        HttpHeaders headers = new HttpHeaders();
        String[] split = key.split("/");
        int length = split.length;
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + split[length - 1]);
        return ResponseEntity.ok()
                .headers(headers)
                .contentType(new MediaType("application", "force-download"))
                .body(resource);
    }

    @PostMapping("/v1/upload")
    public void upload(@RequestParam String bucketName, @RequestParam String key, @RequestParam MultipartFile file) throws IOException {
        try (InputStream inputStream = file.getInputStream()) {
             s3Service.putObject(bucketName, key, inputStream, file.getSize());
        }
    }

    @GetMapping("/v1/url")
    public URL getPreSignedUrl(@RequestParam String bucketName, @RequestParam String key){
        return s3Service.getPreSignedUrl(bucketName, key);
    }
}

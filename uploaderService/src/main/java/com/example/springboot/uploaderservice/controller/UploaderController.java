package com.example.springboot.uploaderservice.controller;

import com.example.springboot.uploaderservice.service.UploaderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping(path = "/core")
@RestController
@RequiredArgsConstructor
public class UploaderController {
    private final UploaderService uploaderService;
    @PostMapping(path = "/fileUpload")
    public ResponseEntity<String> uploadFile(@RequestPart("file") MultipartFile file) {
        String url = uploaderService.upload(file);
        return ResponseEntity.ok(url);
    }
}


package com.example.springboot.uploaderservice.service;


import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

//@Service
public interface UploaderService {

    String upload(MultipartFile file);
}

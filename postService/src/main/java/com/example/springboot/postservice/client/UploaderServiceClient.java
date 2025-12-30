package com.example.springboot.postservice.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringEncoder;


@FeignClient(
        name = "uploader-service",
        path = "/api/v1/upload/core",
        url = "${UPLOADER_SERVICE_URI:}",
        configuration = UploaderServiceClient.MultipartSupportConfig.class
)
public interface UploaderServiceClient {
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<String> uploadFile(@RequestPart("file") MultipartFile file);

    @Configuration
    static class MultipartSupportConfig {

        @Bean
        public Encoder feignFormEncoder(ObjectFactory<HttpMessageConverters> converters) {
            return new SpringFormEncoder(new SpringEncoder(converters));
        }
    }
}

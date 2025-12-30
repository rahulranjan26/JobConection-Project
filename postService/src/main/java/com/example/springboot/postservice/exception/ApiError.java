package com.example.springboot.postservice.exception;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@Setter
@Getter
public class ApiError {

    private String errorMessage;
    private HttpStatus status;
    private LocalDateTime timestamp;

    public ApiError() {
        this.timestamp = LocalDateTime.now();
    }

    public ApiError(String errorMessage, HttpStatus status) {
        this();
        this.errorMessage = errorMessage;
        this.status = status;

    }
}

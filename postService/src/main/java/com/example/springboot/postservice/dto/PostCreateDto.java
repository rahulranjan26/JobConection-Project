package com.example.springboot.postservice.dto;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class PostCreateDto {
    private String content;
}

package com.example.springboot.postservice.dto;


import lombok.Data;

@Data
public class PersonDto {
    private Long personId;

    private Long userId;

    private String name;
}

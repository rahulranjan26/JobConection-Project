package com.example.springboot.userservice.dto;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Data
@Getter
@Setter
public class UserDto {
    private Long userId;
    private String name;
    private String email;
}

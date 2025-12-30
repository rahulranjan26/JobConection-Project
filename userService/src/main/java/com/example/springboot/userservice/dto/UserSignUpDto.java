package com.example.springboot.userservice.dto;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class UserSignUpDto {
    private String email;
    private String password;
    private String name;
}

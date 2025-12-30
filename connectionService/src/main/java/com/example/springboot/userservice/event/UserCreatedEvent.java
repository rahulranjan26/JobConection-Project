package com.example.springboot.userservice.event;


import lombok.*;

@Data
public class UserCreatedEvent {

    private Long userId;
    private String name;
}

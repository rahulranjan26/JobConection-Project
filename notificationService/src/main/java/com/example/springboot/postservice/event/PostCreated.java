package com.example.springboot.postservice.event;


import lombok.*;

@Data
public class PostCreated {
    private Long postId;
    private String content;
    private Long userId;
    private Long ownerId;
}

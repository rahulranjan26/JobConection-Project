package com.example.springboot.postservice.event;


import lombok.*;

@Data
public class PostLiked {
    private Long postId;
    private Long userId;
    private Long ownerId;
}

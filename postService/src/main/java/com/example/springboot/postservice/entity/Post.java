package com.example.springboot.postservice.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Long userId;


    private String imageUrl;

    @CreationTimestamp
    private LocalDateTime createdAt;


    @UpdateTimestamp
    private LocalDateTime updatedAt;

}

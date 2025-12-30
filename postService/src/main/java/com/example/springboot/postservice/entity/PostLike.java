package com.example.springboot.postservice.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class PostLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postLikeId;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long postId;

    @CreationTimestamp
    private LocalDateTime createdDate;
}

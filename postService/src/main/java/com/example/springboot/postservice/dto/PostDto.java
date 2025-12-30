package com.example.springboot.postservice.dto;


import jakarta.persistence.Column;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
public class PostDto {
    private Long postId;
    private String content;
    private Long userId;
    private LocalDateTime createdAt;

}

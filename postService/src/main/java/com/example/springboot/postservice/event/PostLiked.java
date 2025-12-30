package com.example.springboot.postservice.event;


import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostLiked {
    private Long postId;
    private Long userId;
    private Long ownerId;
}

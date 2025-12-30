package com.example.springboot.postservice.repository;

import com.example.springboot.postservice.entity.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    boolean existsByUserIdAndPostId(Long userId, Long postId);

    void deletePostByUserIdAndPostId(Long userId, Long postId);
}
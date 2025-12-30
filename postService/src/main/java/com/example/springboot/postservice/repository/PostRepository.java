package com.example.springboot.postservice.repository;

import com.example.springboot.postservice.dto.PostDto;
import com.example.springboot.postservice.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findPostByUserId(Long userId);
}

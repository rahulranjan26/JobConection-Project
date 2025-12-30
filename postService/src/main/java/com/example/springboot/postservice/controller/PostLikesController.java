package com.example.springboot.postservice.controller;

import com.example.springboot.postservice.dto.PostCreateDto;
import com.example.springboot.postservice.dto.PostDto;
import com.example.springboot.postservice.service.PostLikeService;
import com.example.springboot.postservice.service.PostService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping(path = "/likes")
@RestController
@RequiredArgsConstructor
public class PostLikesController {

    private final PostLikeService postLikeService;


    @PostMapping(path = "/{postId}")
    public ResponseEntity<Void> likePost(@PathVariable Long postId) {
        postLikeService.likePost(postId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable Long postId) {
        postLikeService.unlikePost(postId);
        return ResponseEntity.ok().build();
    }


}

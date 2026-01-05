package com.example.springboot.postservice.controller;

import com.example.springboot.postservice.auth.AuthContextHolder;
import com.example.springboot.postservice.client.ConnectionsServiceClient;
import com.example.springboot.postservice.dto.PersonDto;
import com.example.springboot.postservice.dto.PostCreateDto;
import com.example.springboot.postservice.dto.PostDto;
import com.example.springboot.postservice.service.PostService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(path = "/core")
@RequiredArgsConstructor
@Slf4j
public class PostController {

    private final PostService postService;
    private final ConnectionsServiceClient connectionsServiceClient;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PostDto> createPost(@RequestPart("post") PostCreateDto postCreateDto,
            @RequestPart("file") MultipartFile file) {
        PostDto postDto = postService.createPost(postCreateDto, AuthContextHolder.getCurrentUserId(), file);
        return new ResponseEntity<>(postDto, HttpStatus.OK);

    }

    @GetMapping(path = "/{postId}")
    public ResponseEntity<PostDto> getPost(@PathVariable Long postId) {
        log.info("User Id is : {}", AuthContextHolder.getCurrentUserId());
        PostDto postDto = postService.findPostById(postId);
        // List<PersonDto> connections =
        // connectionsServiceClient.getSecondDegreeConnection(AuthContextHolder.getCurrentUserId());
        // log.info(connections.toString());
        return new ResponseEntity<>(postDto, HttpStatus.OK);
    }

    @GetMapping(path = "/users/{userId}/allPosts")
    public ResponseEntity<List<PostDto>> getPostByUserId(@PathVariable Long userId) {
        List<PostDto> allPostByAUser = postService.getPostByUserId(userId);
        return new ResponseEntity<>(allPostByAUser, HttpStatus.OK);
    }

}

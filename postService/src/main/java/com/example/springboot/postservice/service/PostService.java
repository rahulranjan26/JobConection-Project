package com.example.springboot.postservice.service;


import com.example.springboot.postservice.auth.AuthContextHolder;
import com.example.springboot.postservice.client.ConnectionsServiceClient;
import com.example.springboot.postservice.client.UploaderServiceClient;
import com.example.springboot.postservice.dto.PersonDto;
import com.example.springboot.postservice.dto.PostCreateDto;
import com.example.springboot.postservice.dto.PostDto;
import com.example.springboot.postservice.entity.Post;
import com.example.springboot.postservice.event.PostCreated;
import com.example.springboot.postservice.exception.ResourceNotFoundException;
import com.example.springboot.postservice.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostService {

    private final PostRepository postRepository;
    private final ModelMapper modelMapper;
    private final ConnectionsServiceClient connectionsServiceClient;
    private final KafkaTemplate<Long, PostCreated> kafkaTemplate;
    private final UploaderServiceClient uploaderServiceClient;


    public PostDto createPost(PostCreateDto postCreateDto, Long userId, MultipartFile file) {
        log.info("Creating post for user with id : {}", userId);
        Post post = new Post();
        post.setContent(postCreateDto.getContent());
        post.setUserId(userId);
        List<PersonDto> connections = connectionsServiceClient.getSecondDegreeConnection(userId);
        ResponseEntity<String> url = uploaderServiceClient.uploadFile(file);
        String url_2 = url.getBody();
        post.setImageUrl(url_2);
        Post savedPost = postRepository.save(post);
        for (PersonDto personDto : connections) {
            PostCreated postCreated = PostCreated.builder()
                    .userId(personDto.getUserId())
                    .content(savedPost.getContent())
                    .ownerId(userId)
                    .postId(savedPost.getPostId())
                    .build();
            kafkaTemplate.send("post_created_topic", postCreated);
        }

        return modelMapper.map(savedPost, PostDto.class);

    }


    public PostDto findPostById(Long postId) {
        log.info("Fetching post with id : {}", postId);
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + postId));
        return modelMapper.map(post, PostDto.class);
    }

    public List<PostDto> getPostByUserId(Long userId) {
        List<Post> allPostByUser = postRepository.findPostByUserId(userId);
        return allPostByUser.stream()
                .map((element) -> modelMapper.map(element, PostDto.class))
                .toList();
    }


}

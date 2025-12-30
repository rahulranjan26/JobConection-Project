package com.example.springboot.postservice.service;


import com.example.springboot.postservice.auth.AuthContextHolder;
import com.example.springboot.postservice.client.ConnectionsServiceClient;
import com.example.springboot.postservice.entity.Post;
import com.example.springboot.postservice.entity.PostLike;
import com.example.springboot.postservice.event.PostLiked;
import com.example.springboot.postservice.exception.BadRequestException;
import com.example.springboot.postservice.exception.ResourceNotFoundException;
import com.example.springboot.postservice.repository.PostLikeRepository;
import com.example.springboot.postservice.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostLikeService {

    private final PostLikeRepository postLikeRepository;
    private final ModelMapper modelMapper;
    private final PostRepository postRepository;
    private final ConnectionsServiceClient connectionsServiceClient;
    private final KafkaTemplate<Long, PostLiked> kafkaTemplate;

    public void likePost(Long postId) {
        Long userId = AuthContextHolder.getCurrentUserId();
        log.info("Liking post with id: {} for user with userId : {}", postId, userId);
        Post post = postRepository
                .findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post with id: " + postId + " not found"));

        boolean hasAlreadyLiked = postLikeRepository.existsByUserIdAndPostId(userId, postId);
        if (hasAlreadyLiked) {
            throw new BadRequestException("Post with id: " + postId + " is already liked");
        }
        PostLike postLike = new PostLike();
        postLike.setUserId(userId);
        postLike.setPostId(postId);
        postLikeRepository.save(postLike);

        // TODO : Send notification to the owner of the post that the post has been liked.

        PostLiked postLiked = PostLiked.builder()
                .postId(postId)
                .userId(userId)
                .ownerId(post.getUserId())
                .build();
        kafkaTemplate.send("post_liked_topic", postLiked);



    }
    @Transactional
    public void unlikePost(Long postId) {
        Long userId = AuthContextHolder.getCurrentUserId();
        log.info("Unliking post with id: {} for user with userId : {}", postId, userId);

        postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post with id: " + postId + " not found"));
        boolean hasAlreadyLiked = postLikeRepository.existsByUserIdAndPostId(userId, postId);
        if (! hasAlreadyLiked) {
            throw new BadRequestException("Post with id: " + postId + " is already unliked");
        }
        postLikeRepository.deletePostByUserIdAndPostId(userId, postId);
    }
}

package com.example.springboot.notificationservice.consumer;


import com.example.springboot.notificationservice.service.NotificationService;
import com.example.springboot.notificationservice.entity.Notification;
import com.example.springboot.postservice.event.PostCreated;
import com.example.springboot.postservice.event.PostLiked;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostConsumer {

    private final NotificationService notificationService;

    @KafkaListener(topics = "post_created_topic")
    public void handlePostCreated(PostCreated postCreated) {
        log.info("Received post created event: {}", postCreated);
        String message = String.format("Your connection with id: %d has  created this post: %s", postCreated.getUserId(), postCreated.getContent());
        Notification notification = Notification.builder()
                .message(message)
                .userId(postCreated.getUserId())
                .build();
        notificationService.sendNotification(notification);
    }

    @KafkaListener(topics = "post_liked_topic")
    public void handlePostLike(PostLiked postLiked) {
        log.info("Received post liked event: {}", postLiked);
        String message = String.format("Your connection with id: %d has  liked the post", postLiked.getUserId());
        Notification notification = Notification.builder()
                .message(message)
                .userId(postLiked.getOwnerId())
                .build();
        notificationService.sendNotification(notification);
    }

}

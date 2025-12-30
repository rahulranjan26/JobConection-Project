package com.example.springboot.notificationservice.service;


import com.example.springboot.notificationservice.entity.Notification;
import com.example.springboot.notificationservice.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public void sendNotification(Notification notification) {
        notificationRepository.save(notification);
    }


}

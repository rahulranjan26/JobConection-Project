package com.example.springboot.connectionservice.consumer;


import com.example.springboot.connectionservice.entity.Person;
import com.example.springboot.connectionservice.service.ConnectionService;
import com.example.springboot.userservice.event.UserCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class createUserConsumer {

    private final ConnectionService connectionService;

    @KafkaListener(topics = "user_created_topic")
    public void userCreatedConsumer(UserCreatedEvent userCreatedEvent) {
        Person person = Person.builder()
                .userId(userCreatedEvent.getUserId())
                .name(userCreatedEvent.getName())
                .build();
        connectionService.createNewUser(person);
    }


}

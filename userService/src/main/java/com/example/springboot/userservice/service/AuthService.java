package com.example.springboot.userservice.service;



import com.example.springboot.userservice.dto.UserDto;
import com.example.springboot.userservice.dto.UserLoginDto;
import com.example.springboot.userservice.dto.UserSignUpDto;
import com.example.springboot.userservice.entity.User;
import com.example.springboot.userservice.event.UserCreatedEvent;
import com.example.springboot.userservice.repository.UserRepository;
import com.example.springboot.userservice.utils.BCryptUtility;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.modelmapper.ModelMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final JWTService jwtService;
    private final KafkaTemplate<Long, UserCreatedEvent> kafkaTemplate;


    public UserDto signUp(UserSignUpDto userSignUpDto) {
        log.info("Sign up User");
        if (userRepository.existsByEmail(userSignUpDto.getEmail())) {
            throw new RuntimeException("User with email : {} Exists" + userSignUpDto.getEmail());
        }
        User user = modelMapper.map(userSignUpDto, User.class);
        user.setPassword(BCryptUtility.hashPassword(userSignUpDto.getPassword()));
        User savedUser = userRepository.save(user);
        UserCreatedEvent userCreatedEvent = UserCreatedEvent.builder()
                .userId(savedUser.getUserId())
                .name(savedUser.getName())
                .build();
        kafkaTemplate.send("user_created_topic", userCreatedEvent);
        return modelMapper.map(savedUser, UserDto.class);
    }

    public String login(UserLoginDto userLoginDto) {
        log.info("Login User with email : {}", userLoginDto.getEmail());
        User user = userRepository.findByEmail(userLoginDto.getEmail())
                .orElseThrow(() -> new RuntimeException("Username or password is wrong"));
        if (!BCryptUtility.checkPassword(userLoginDto.getPassword(), user.getPassword())) {
            throw new RuntimeException("Wrong username or password");
        }
        return jwtService.generateAccessToken(user);

    }


}

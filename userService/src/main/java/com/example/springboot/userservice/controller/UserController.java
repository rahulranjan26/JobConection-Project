package com.example.springboot.userservice.controller;

import com.example.springboot.userservice.dto.UserDto;
import com.example.springboot.userservice.dto.UserLoginDto;
import com.example.springboot.userservice.dto.UserSignUpDto;
import com.example.springboot.userservice.repository.UserRepository;
import com.example.springboot.userservice.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(path = "/auth")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final AuthService authService;


    @PostMapping(path = "/login")
    public ResponseEntity<String> login(@RequestBody UserLoginDto userLoginDto) {
        return new ResponseEntity<>(authService.login(userLoginDto), HttpStatus.OK);
    }

    @PostMapping(path = "/signup")
    public ResponseEntity<UserDto> signup(@RequestBody UserSignUpDto signupDto) {
        UserDto dto = authService.signUp(signupDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }


}

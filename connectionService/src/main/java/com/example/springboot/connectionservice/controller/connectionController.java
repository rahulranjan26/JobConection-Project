package com.example.springboot.connectionservice.controller;


import com.example.springboot.connectionservice.entity.Person;
import com.example.springboot.connectionservice.service.ConnectionService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(path = "/core")
@RestController
@RequiredArgsConstructor
@Slf4j
public class connectionController {

    private final ConnectionService connectionService;


    @GetMapping(path = "/{userId}/second-degree")
    public List<Person> getSecondDegreeConnection(@PathVariable Long userId, HttpServletRequest request) {
        log.info("User Id is : {}", request.getHeader("X-User-Id"));
        return connectionService.getSecondDegreeConnections(userId);
    }

    @GetMapping("/{userId}/first-degree")
    public ResponseEntity<List<Person>> getFirstDegreeConnections(@PathVariable Long userId) {
        List<Person> personList = connectionService.getFirstDegreeConnectionsOfUser(userId);
        return ResponseEntity.ok(personList);
    }

    @PostMapping("/request/{userId}")
    public ResponseEntity<Void> sendConnectionRequest(@PathVariable Long userId) {
        connectionService.sendConnectionRequest(userId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/accept/{userId}")
    public ResponseEntity<Void> acceptConnectionRequest(@PathVariable Long userId) {
        connectionService.acceptConnectionRequest(userId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/reject/{userId}")
    public ResponseEntity<Void> rejectConnectionRequest(@PathVariable Long userId) {
        connectionService.rejectConnectionRequest(userId);
        return ResponseEntity.noContent().build();
    }
}

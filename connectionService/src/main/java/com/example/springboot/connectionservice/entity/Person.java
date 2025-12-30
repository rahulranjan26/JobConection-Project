package com.example.springboot.connectionservice.entity;


import lombok.Builder;
import lombok.Data;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

import java.util.UUID;

@Node
@Data
@Builder
public class Person {
    @Id
    @GeneratedValue
    private UUID personId;

    private Long userId;

    private String name;


}

package com.tjc.kafka_demo.controller;

import com.tjc.kafka_demo.model.User;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

@AllArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @PostMapping
    public ResponseEntity<User> create() throws ExecutionException, InterruptedException {
        var user = User.builder()
                .id(UUID.randomUUID().toString())
                .userName("user")
                .age(25)
                .build();

        Message<User> msg = MessageBuilder.withPayload(user)
                .setHeader(KafkaHeaders.TOPIC, "tjc.kafka-demo-user-created")
                //.setHeader(KafkaHeaders.KEY, user.getUserName())
                .setHeader("X-AgentName", "kafka-demo-app")
                .build();

        kafkaTemplate.send(msg).get();

        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }
}

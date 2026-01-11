package com.tjc.kafka_demo.service;

import com.tjc.kafka_demo.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserListener {

    @KafkaListener(topics = "tjc.kafka-demo-user-created", containerFactory = "kafkaListenerContainerFactory")
    public void consume(
            @Payload User user,
            @Header("X-AgentName") String agentName,
            @Header(KafkaHeaders.RECEIVED_PARTITION) int partition,
            @Header(KafkaHeaders.OFFSET) int offset,
            Acknowledgment ack
    ) {
        log.info("userId: {}, agentName: {}, partition: {}, offset: {}",
                user.getId(), agentName, partition, offset);
        ack.acknowledge();
    }
}
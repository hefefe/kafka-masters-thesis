package com.mw.kafka.consumer.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class KafkaEmailEventConsumer {

    @KafkaListener(topics = "${kafka-test-app.kafka.topics.name}", groupId = "listenerGroup1")
    public void consumeEvent(@Payload String message) {
        System.out.println(String.format("Content: %s", message));
    }
}
package com.mw.master.kafka.service;

import com.mw.master.kafka.data.TestMessage;
import com.mw.master.kafka.type.MessageSizeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService{

    private final KafkaTemplate kafkaTemplate;

    @Value("${kafka-test-app.kafka.topics.name}")
    private String topic;

    @Override
    public void sendMessage(Integer size, MessageSizeUnit unit) {
        var randomMessage = new char[size*unit.getSize()/2];
        Arrays.fill(randomMessage, 'a');
        var testMessage = new TestMessage(String.valueOf(randomMessage), Instant.now());
        kafkaTemplate.send(topic, testMessage);
    }
}

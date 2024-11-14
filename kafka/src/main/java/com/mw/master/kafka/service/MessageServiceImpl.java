package com.mw.master.kafka.service;

import com.mw.master.kafka.data.TestMessage;
import com.mw.master.kafka.type.MessageSizeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService{

    private final KafkaTemplate kafkaTemplate;

    @Value("${kafka-test-app.kafka.topics.name}")
    private String topic;

    @Override
    public ResponseEntity<Void> sendMessage(Integer size, MessageSizeUnit unit) {
        var charSize = Math.min(size * unit.getSize(), MessageSizeUnit.MB.getSize());
        var randomMessage = new char[charSize];
        Arrays.fill(randomMessage, 'a');
        var message = new TestMessage(String.valueOf(randomMessage), Instant.now());
        CompletableFuture<Object> future = kafkaTemplate.send(topic, message);
        AtomicReference<ResponseEntity<Void>> response = new AtomicReference<>(new ResponseEntity<>(HttpStatus.OK));
        future.whenComplete((result,ex) -> {
            if(ex != null){
                response.set(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
            }
        });
        return response.get();
    }
}

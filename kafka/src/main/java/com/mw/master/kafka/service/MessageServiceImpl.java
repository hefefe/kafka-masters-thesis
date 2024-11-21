package com.mw.master.kafka.service;

import com.mw.master.kafka.data.TestMessage;
import com.mw.master.kafka.type.MessageSizeUnit;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final KafkaTemplate kafkaTemplate;
    private final MeterRegistry meterRegistry;
    private static final List<String> COUNTER_TYPES = List.of("error", "operation");



    @Value("${kafka-test-app.kafka.topics.name}")
    private String topic;

    @Override
    public ResponseEntity<Void> sendMessage(Integer size, MessageSizeUnit unit) {
        var charSize = Math.min(size * unit.getSize(), MessageSizeUnit.MB.getSize());
        var randomMessage = new char[charSize];
        Arrays.fill(randomMessage, 'a');
        var message = new TestMessage(String.valueOf(randomMessage), Instant.now());
        CompletableFuture<Object> future = kafkaTemplate.send(topic, message);
        future.whenComplete((result, ex) -> {
            getCounter(COUNTER_TYPES.get(1)).increment();
            if (ex != null)
                getCounter(COUNTER_TYPES.get(0)).increment();
        });
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private Counter getCounter(String type) {
        return Counter.builder("spring_kafka_operations")
                .tag("type", type)
                .register(meterRegistry);
    }

    @EventListener(ApplicationReadyEvent.class)
    private void buildCounter(){
        COUNTER_TYPES.forEach(this::getCounter);
    }

}

package com.mw.kafka.consumer.consumer;

import com.mw.kafka.consumer.data.TestMessage;
import com.mw.kafka.consumer.util.JSONUtils;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

@Service
@Slf4j
public class KafkaEmailEventConsumer {

    private final MeterRegistry meterRegistry;
    private static final List<Integer> values = List.of(0, 25, 50, 75, 100, 200, 300, 400, 500, 600, 700, 800, 900, 1000, 1500, 2000, 2500, 3000, 4000, 5000);

    public KafkaEmailEventConsumer(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
        values.forEach(val -> getCounter(val.toString()));

    }

    private Counter getCounter(String upto) {
        return Counter.builder("broker_latency_ms")
                .tag("upto", upto)
                .register(meterRegistry);
    }

    private void incrementCounter(String upto) {
        getCounter(upto).increment();
    }

    @KafkaListener(topics = "${kafka-test-app.kafka.topics.name}", groupId = "listenerGroup1")
    public void consumeEvent(@Header(KafkaHeaders.RECEIVED_TOPIC) String topic, @Payload String message) {
        var kafkaMessageData = JSONUtils.convertToObject(message, TestMessage.class);
        var latency = Duration.between(kafkaMessageData.time(), Instant.now()).toMillis();
        incrementSpecificCounter(latency);
    }

    private void incrementSpecificCounter(long latency) {
        for (int i = 1; i < values.size(); i++) {
            if (latency >= values.get(values.size() - 1)) {
                incrementCounter(values.get(values.size() - 1).toString());
                break;
            }
            if (latency < values.get(i)) {
                incrementCounter(values.get(i - 1).toString());
                break;
            }
        }
    }
}
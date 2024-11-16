package com.mw.kafka.consumer.consumer;

import com.mw.kafka.consumer.data.TestMessage;
import com.mw.kafka.consumer.util.JSONUtils;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.DistributionSummary;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaEmailEventConsumer {

    private final Counter opsCounter;
    private final DistributionSummary kafkaLatencySummary;

    @KafkaListener(topics = "${kafka-test-app.kafka.topics.name}", groupId = "listenerGroup1")
    public void consumeEvent(@Header(KafkaHeaders.RECEIVED_TOPIC) String topic, @Payload String message) {
        var kafkaMessageData = JSONUtils.convertToObject(message, TestMessage.class);
        kafkaLatencySummary.record((double) Duration.between(kafkaMessageData.time(), Instant.now()).toMillis());
        opsCounter.increment();
    }
}
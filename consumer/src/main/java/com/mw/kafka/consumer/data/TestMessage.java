package com.mw.kafka.consumer.data;

import java.time.Instant;

public record TestMessage(String message, Instant time) {
}

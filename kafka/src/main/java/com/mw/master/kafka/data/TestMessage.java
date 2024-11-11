package com.mw.master.kafka.data;

import java.time.Instant;

public record TestMessage(String message, Instant time) {
}

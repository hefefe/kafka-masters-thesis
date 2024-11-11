package com.mw.kafka.consumer.api;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Event {
    private String message;
}

package com.mw.master.kafka.api;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Event {

    private String message;
}

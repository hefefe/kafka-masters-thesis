package com.mw.master.kafka.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MessageSizeUnit {
    B(1),
    KB(1000),
    MB(1000000);

    private final Integer size;

}

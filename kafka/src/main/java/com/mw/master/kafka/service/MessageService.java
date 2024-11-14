package com.mw.master.kafka.service;

import com.mw.master.kafka.type.MessageSizeUnit;
import org.springframework.http.ResponseEntity;

public interface MessageService {

    ResponseEntity<Void> sendMessage(Integer size, MessageSizeUnit unit);
}

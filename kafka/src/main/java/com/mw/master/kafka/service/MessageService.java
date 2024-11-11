package com.mw.master.kafka.service;

import com.mw.master.kafka.type.MessageSizeUnit;

public interface MessageService {

    void sendMessage(Integer size, MessageSizeUnit unit);
}

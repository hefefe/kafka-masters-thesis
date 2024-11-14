package com.mw.master.kafka.controller;

import com.mw.master.kafka.service.MessageService;
import com.mw.master.kafka.type.MessageSizeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("api/v1/message/test")
public class MessageController {

    private final MessageService messageService;

    @GetMapping
    public ResponseEntity<Void> SendTestMessageToKafka(@RequestParam(defaultValue = "1") Integer size, @RequestParam(defaultValue = "KB") MessageSizeUnit unit){
        return messageService.sendMessage(size, unit);
    }
}

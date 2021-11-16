package com.shvets.kafkaandcouchbase.kafka.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.shvets.kafkaandcouchbase.kafka.model.InputKafkaMessage;

@Service
public class GenerateMessageService {

    public List<InputKafkaMessage> createMessage(int count) {
        List<InputKafkaMessage> messages = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            messages.add(InputKafkaMessage.builder().value(String.valueOf(Long.valueOf(i))).startTime(new Date()).build());
        }
        return messages;
    }
}

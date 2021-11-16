package com.shvets.kafkaandcouchbase.kafka.service;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.shvets.kafkaandcouchbase.kafka.model.InputKafkaMessage;

@Service
public class GenerateMessageService {

    public InputKafkaMessage createMessage(Date startTime, long value) {
        return InputKafkaMessage.builder().value(String.valueOf(value)).startTime(startTime).build();
    }
}

package com.shvets.kafkaandcouchbase.kafka.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.shvets.kafkaandcouchbase.kafka.model.InputKafkaMessage;

@Service
public class GenerateMessageService {

    public List<InputKafkaMessage> createTestMessage(int count) {
        @SuppressWarnings("DuplicatedCode")
        List<InputKafkaMessage> messages = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            messages.add(InputKafkaMessage.builder().value(String.valueOf(Long.valueOf(i))).startTime(new Date()).build());
        }
        return messages;
    }

    public List<InputKafkaMessage> createMessage(int count) {
        @SuppressWarnings("DuplicatedCode")
        List<InputKafkaMessage> messages = new ArrayList<>();
        Random random = new Random();
        for (int i = 1; i <= count; i++) {
            String type = String.valueOf(random.nextInt(3));
            String value = String.valueOf(random.nextInt(100));
            String building = String.valueOf(random.nextInt(100));
            messages.add(InputKafkaMessage.builder()
                                          .value(value)
                                          .deviceType(type)
                                          .deviceAddress(String.format("Penza, Voronova street, building #%s", building))
                                          .startTime(new Date()).build());
        }
        return messages;
    }
}

package com.shvets.kafkaandcouchbase.kafka.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SenderFactory {

    private final Sender sender;

    public SenderFactory(List<Sender> senders, @Value("${bootstrap.sender-type:sync}") String type) {
        this.sender = senders.stream()
                             .filter(bootstrapSender -> type.equals(bootstrapSender.getType()))
                             .findFirst()
                             .orElseThrow(() -> new IllegalStateException("bootstrap sender is undefined. Type = " + type));
    }

    public Sender getSender() {
        return sender;
    }
}

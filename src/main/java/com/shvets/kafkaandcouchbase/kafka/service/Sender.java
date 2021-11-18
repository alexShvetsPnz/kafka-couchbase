package com.shvets.kafkaandcouchbase.kafka.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.shvets.kafkaandcouchbase.kafka.model.InputKafkaMessage;

@Service
public interface Sender {

    String getType();

    void sendMessages(String topic, List<InputKafkaMessage> messages);
}
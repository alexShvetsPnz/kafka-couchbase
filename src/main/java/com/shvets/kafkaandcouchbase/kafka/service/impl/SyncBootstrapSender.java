package com.shvets.kafkaandcouchbase.kafka.service.impl;

import java.util.List;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import com.shvets.kafkaandcouchbase.kafka.model.InputKafkaMessage;
import com.shvets.kafkaandcouchbase.kafka.service.BootstrapSender;
import com.shvets.kafkaandcouchbase.kafka.service.LogBootStrapResultsService;

@Service
@Slf4j
public class SyncBootstrapSender implements BootstrapSender {

    private final KafkaTemplate<String, InputKafkaMessage> kafkaTemplate;
    private final LogBootStrapResultsService logBootStrapResultsService;

    public SyncBootstrapSender(KafkaTemplate<String, InputKafkaMessage> kafkaTemplate,
                               LogBootStrapResultsService logBootStrapResultsService) {
        this.kafkaTemplate = kafkaTemplate;
        this.logBootStrapResultsService = logBootStrapResultsService;
    }

    @Override
    public String getType() {
        return "sync";
    }

    @SneakyThrows
    @Override
    public void sendMessages(String topic, List<InputKafkaMessage> messages) {
        log.info("{}: Producer started...", Thread.currentThread().getName());
        for (InputKafkaMessage message : messages) {
            kafkaTemplate.send(topic, message);
        }
        logBootStrapResultsService.logResults(Thread.currentThread().getName(), "", messages);
        log.info("{}: Producer finished...", Thread.currentThread().getName());
    }
}
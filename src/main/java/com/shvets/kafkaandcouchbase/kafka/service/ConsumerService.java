package com.shvets.kafkaandcouchbase.kafka.service;

import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

import com.shvets.kafkaandcouchbase.kafka.model.InputKafkaMessage;

@Service
@Slf4j
public class ConsumerService {

    private final AtomicInteger receivedMessagedCount = new AtomicInteger(0);

    @KafkaListener(topics = "#{'${spring.kafka.consumer.topic}'}")
    public void consume(InputKafkaMessage message,
                        Acknowledgment acknowledgment,
                        @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) {
        handle(message);
        acknowledgment.acknowledge();
    }

    private void handle(InputKafkaMessage message) {
        log.info("received message = {}", message);
        receivedMessagedCount.getAndIncrement();
    }

}

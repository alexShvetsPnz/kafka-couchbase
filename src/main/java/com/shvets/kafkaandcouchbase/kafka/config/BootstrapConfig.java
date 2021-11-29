package com.shvets.kafkaandcouchbase.kafka.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;

import com.shvets.kafkaandcouchbase.kafka.service.SenderFactory;
import com.shvets.kafkaandcouchbase.kafka.service.impl.GenerateMessageService;

@Configuration
@Slf4j
public class BootstrapConfig {

    @Bean
    ApplicationRunner runAdditionalClientCacheInitialization(GenerateMessageService generateMessageService,
                                                             SenderFactory senderFactory,
                                                             @Value("${spring.kafka.producer.topic}") String topic,
                                                             @Value("${bootstrap.count:1000}") Integer count) {
        return args -> senderFactory.getSender().sendMessages(topic, generateMessageService.createTestMessage(count));
    }
}

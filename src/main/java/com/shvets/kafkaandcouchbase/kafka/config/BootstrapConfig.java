package com.shvets.kafkaandcouchbase.kafka.config;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

import lombok.extern.slf4j.Slf4j;

import com.shvets.kafkaandcouchbase.kafka.model.InputKafkaMessage;
import com.shvets.kafkaandcouchbase.kafka.service.GenerateMessageService;

@Configuration
@Slf4j
public class BootstrapConfig {

    @Bean
    ApplicationRunner runAdditionalClientCacheInitialization(KafkaTemplate<String, InputKafkaMessage> kafkaTemplate,
                                                             GenerateMessageService generateMessageService,
                                                             @Value("${spring.kafka.producer.topic}") String topic) {
        return args -> {
            log.info("Producer started...");
            for (long i = 0; i < 10_000; i++) {
                kafkaTemplate.send(topic, generateMessageService.createMessage(new Date(), i));
            }
        };
    }
}

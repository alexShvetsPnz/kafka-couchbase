package com.shvets.kafkaandcouchbase.kafka.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;

import com.shvets.kafkaandcouchbase.kafka.service.BootstrapSender;
import com.shvets.kafkaandcouchbase.kafka.service.impl.GenerateMessageService;

@Configuration
@Slf4j
public class BootstrapConfig {

    @Bean
    ApplicationRunner runAdditionalClientCacheInitialization(GenerateMessageService generateMessageService,
                                                             List<BootstrapSender> bootstrapSenders,
                                                             @Value("${spring.kafka.producer.topic}") String topic,
                                                             @Value("${bootstrap.count:1000}") Integer count,
                                                             @Value("${bootstrap.sender-type:sync}") String type) {
        BootstrapSender sender = bootstrapSenders.stream()
                                                 .filter(bootstrapSender -> type.equals(bootstrapSender.getType()))
                                                 .findFirst()
                                                 .orElseThrow(() -> new IllegalStateException("bootstrap sender is undefined. Type = " + type));
        return args -> sender.sendMessages(topic, generateMessageService.createMessage(count));
    }
}

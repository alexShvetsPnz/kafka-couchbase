package com.shvets.kafkaandcouchbase.kafka.schedule;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import lombok.extern.slf4j.Slf4j;

import com.shvets.kafkaandcouchbase.kafka.service.SenderFactory;
import com.shvets.kafkaandcouchbase.kafka.service.impl.GenerateMessageService;

@Configuration
@EnableScheduling
@Slf4j
public class ScheduleConfig {

    private final GenerateMessageService generateMessageService;
    private final SenderFactory senderFactory;
    private final String topic;

    public ScheduleConfig(GenerateMessageService generateMessageService,
                          SenderFactory senderFactory,
                          @Value("${spring.kafka.producer.topic}") String topic) {
        this.generateMessageService = generateMessageService;
        this.senderFactory = senderFactory;
        this.topic = topic;
    }

    @Scheduled(fixedRate = 5000)
    public void scheduleFixedRateTask() {
        senderFactory.getSender().sendMessages(topic, generateMessageService.createMessage(1));
    }
}

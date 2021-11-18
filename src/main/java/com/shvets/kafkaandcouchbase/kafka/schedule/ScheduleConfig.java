package com.shvets.kafkaandcouchbase.kafka.schedule;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import lombok.extern.slf4j.Slf4j;

import com.shvets.kafkaandcouchbase.kafka.service.BootstrapSender;
import com.shvets.kafkaandcouchbase.kafka.service.impl.GenerateMessageService;

@Configuration
@EnableScheduling
@Slf4j
public class ScheduleConfig {

    private final GenerateMessageService generateMessageService;
    private final List<BootstrapSender> bootstrapSenders;
    private final String topic;
    private final String type;

    public ScheduleConfig(GenerateMessageService generateMessageService,
                          List<BootstrapSender> bootstrapSenders,
                          @Value("${spring.kafka.producer.topic}") String topic,
                          @Value("${bootstrap.sender-type:sync}") String type) {
        this.generateMessageService = generateMessageService;
        this.bootstrapSenders = bootstrapSenders;
        this.topic = topic;
        this.type = type;
    }

    @Scheduled(fixedRate = 1000)
    public void scheduleFixedRateTask() {
//        log.info("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
    }
}

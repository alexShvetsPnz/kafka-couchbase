package com.shvets.kafkaandcouchbase.kafka.service.impl;

import static org.springframework.util.StringUtils.*;

import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

import com.shvets.kafkaandcouchbase.kafka.model.InputKafkaMessage;
import com.shvets.kafkaandcouchbase.persistence.model.Device;
import com.shvets.kafkaandcouchbase.persistence.model.Metric;
import com.shvets.kafkaandcouchbase.persistence.repository.DeviceRepository;
import com.shvets.kafkaandcouchbase.persistence.repository.MetricRepository;

@Service
@Slf4j
public class ConsumerService {

    private final AtomicInteger receivedMessagedCount = new AtomicInteger(0);
    private final DeviceRepository deviceRepository;
    private final MetricRepository metricRepository;

    public ConsumerService(DeviceRepository deviceRepository,
                           MetricRepository metricRepository) {
        this.deviceRepository = deviceRepository;
        this.metricRepository = metricRepository;
    }

    @KafkaListener(topics = "#{'${spring.kafka.consumer.topic}'}")
    public void consume(InputKafkaMessage message,
                        Acknowledgment acknowledgment,
                        @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) {
        handle(message, partition);
        acknowledgment.acknowledge();
    }

    private void handle(InputKafkaMessage message, int partition) {
        log.info("{}: received message = {} from partition {}", Thread.currentThread().getName(), message, partition);
        if (messageIsReal(message)) {
            handlePayload(message);
        } else {
            handleTestMessage(message);
        }
        log.info("receivedMessagedCount = " + receivedMessagedCount.incrementAndGet());
    }

    private void handleTestMessage(InputKafkaMessage message) {
        log.info("test message");
    }

    private void handlePayload(InputKafkaMessage message) {
        log.info("real message");
        Device device = deviceRepository.findByTypeAndHouseAddress(message.getDeviceType(), message.getDeviceAddress());
        metricRepository.save(Metric.builder()
                                    .value(Integer.valueOf(message.getValue()))
                                    .deviceAddress(device.getHouse().getAddress())
                                    .deviceType(device.getType())
                                    .deviceId(device.getId())
                                    .timestamp(message.getStartTime().getTime())
                                    .build());
        log.info("device for metric found. {}", device);
    }

    private boolean messageIsReal(InputKafkaMessage message) {
        return hasText(message.getValue())
                && hasText(message.getDeviceType())
                && hasText(message.getDeviceAddress());
    }

}

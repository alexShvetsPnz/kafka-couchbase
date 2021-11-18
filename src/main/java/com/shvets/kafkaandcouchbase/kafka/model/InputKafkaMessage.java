package com.shvets.kafkaandcouchbase.kafka.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class InputKafkaMessage implements HasIdentifier {

    private String value;      // payload
    private Date startTime;    // when the producer started
    private String deviceAddress;
    private String deviceType;

    public String getIdentifier() {
        return String.format("%s%S", deviceAddress, deviceType);
    }
}


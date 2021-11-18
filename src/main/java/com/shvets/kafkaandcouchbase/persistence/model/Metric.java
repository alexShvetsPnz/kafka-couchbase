package com.shvets.kafkaandcouchbase.persistence.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.Field;
import org.springframework.data.couchbase.core.mapping.id.GeneratedValue;
import org.springframework.data.couchbase.core.mapping.id.GenerationStrategy;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Metric {

    @Id
    @GeneratedValue(strategy = GenerationStrategy.UNIQUE)
    String id;

    @Field
    Integer value;

    @Field
    String deviceId;

    @Field
    String deviceType;

    @Field
    String deviceAddress;

    @Field
    Long timestamp;

}

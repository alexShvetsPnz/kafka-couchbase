package com.shvets.kafkaandcouchbase.persistence.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.Field;
import org.springframework.data.couchbase.core.mapping.id.GeneratedValue;
import org.springframework.data.couchbase.core.mapping.id.GenerationStrategy;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Document
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationStrategy.UNIQUE)
    String id;

    @Field
    String type;

    @Field
    ShortHouse house;

    @AllArgsConstructor
    @NoArgsConstructor
    public static class ShortHouse {
        String id;
        String address;
    }
}


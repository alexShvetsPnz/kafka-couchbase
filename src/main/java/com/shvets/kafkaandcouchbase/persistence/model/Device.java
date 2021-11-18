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
import lombok.ToString;

@Document
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
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
    @Getter
    public static class ShortHouse {
        String id;
        String address;
    }
}


package com.shvets.kafkaandcouchbase.kafka.service;

import java.util.List;

import com.shvets.kafkaandcouchbase.kafka.model.HasIdentifier;

public interface LogBootStrapResultsService {

    void logResults(String prefix, String suffix, List<? extends HasIdentifier> messages);
}
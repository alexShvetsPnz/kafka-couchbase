package com.shvets.kafkaandcouchbase.kafka.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

import com.shvets.kafkaandcouchbase.kafka.model.HasIdentifier;
import com.shvets.kafkaandcouchbase.kafka.service.LogBootStrapResultsService;

@Service
@Slf4j
public class LogBootStrapResultsImpl implements LogBootStrapResultsService {

    @Override
    public void logResults(String prefix, String suffix, List<? extends HasIdentifier> messages) {
        int size = messages.size();
        String first = messages.get(0).getIdentifier();
        String last = messages.get(size - 1).getIdentifier();
        log.info("{}. Messages sent = {}, [{}-{}]. {}", prefix, size, first, last, suffix);
    }
}

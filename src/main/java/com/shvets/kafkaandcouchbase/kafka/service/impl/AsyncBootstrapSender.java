package com.shvets.kafkaandcouchbase.kafka.service.impl;

import java.util.List;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.springframework.stereotype.Service;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import com.google.common.collect.Lists;
import com.shvets.kafkaandcouchbase.kafka.model.InputKafkaMessage;
import com.shvets.kafkaandcouchbase.kafka.service.BootstrapSender;

@Service
@Slf4j
public class AsyncBootstrapSender implements BootstrapSender {

    private static final int threads = 10;
    private static ExecutorService WORKER_THREAD_POOL
            = Executors.newFixedThreadPool(10);
    private static final CompletionService<Void> executor = new ExecutorCompletionService<>(WORKER_THREAD_POOL);
    private final SyncBootstrapSender syncBootstrapSender;

    public AsyncBootstrapSender(SyncBootstrapSender syncBootstrapSender) {
        this.syncBootstrapSender = syncBootstrapSender;
    }

    @Override
    public String getType() {
        return "async";
    }

    @SneakyThrows
    @Override
    public void sendMessages(String topic, List<InputKafkaMessage> messages) {

        long totalMessages = messages.size();
        final int messagePerThread = (int) (totalMessages / threads);
        log.debug("messagePerThread:{}", messagePerThread);
        List<List<InputKafkaMessage>> partition = Lists.partition(messages, messagePerThread);
        for (List<InputKafkaMessage> inputKafkaMessages : partition) {
            executor.submit(() -> syncBootstrapSender.sendMessages(topic, inputKafkaMessages), null);
        }
        long startProcessingTime = System.currentTimeMillis();
        Future<Void> future = executor.take();
        future.get();
        long totalProcessingTime = System.currentTimeMillis() - startProcessingTime;
        log.info("processing time = {}", totalProcessingTime);
    }
}
package com.shvets.kafkaandcouchbase.kafka.config;

import com.shvets.kafkaandcouchbase.kafka.model.InputKafkaMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.*;

@Configuration
@Slf4j
public class KafkaConfig {

    @Bean
    public ConsumerFactory<String, InputKafkaMessage> consumerFactory(KafkaProperties kafkaProperties) {
        return new DefaultKafkaConsumerFactory<>(kafkaProperties.buildConsumerProperties());
    }

    @Bean
    public KafkaTemplate<String, InputKafkaMessage> kafkaTemplate(ProducerFactory<String, InputKafkaMessage> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }

    @Bean
    public ProducerFactory<String, InputKafkaMessage> producerFactory(KafkaProperties kafkaProperties) {
        return new DefaultKafkaProducerFactory<>(kafkaProperties.buildProducerProperties());
    }

}

package com.example.demo.manager;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class KafkaManager {
    private final String bootstrapServer;

    public KafkaManager() {
        this("localhost:9092");
    }

    public KafkaManager(String bootstrapServer) {
        this.bootstrapServer = bootstrapServer;
    }

    public KafkaProducer<String, String> createProducer() {
        Properties config = new Properties();

        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, this.bootstrapServer);

        return new KafkaProducer<>(config);
    }

    public KafkaConsumer<String, String> createConsumer(String groupId) {
        Properties config = new Properties();

        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, this.bootstrapServer);
        config.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);

        return new KafkaConsumer<>(config);
    }

    public void dispose() {
        //TODO: close producers/consumers
    }
}

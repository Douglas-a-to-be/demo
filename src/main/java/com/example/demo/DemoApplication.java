package com.example.demo;

import jakarta.annotation.PreDestroy;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.example.demo.manager.KafkaManager;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootApplication
public class DemoApplication {
    private static boolean shutdown = false;
    private static ExecutorService executor;

    //Kafka
    public static KafkaProducer<String, String> producer;
    public static KafkaConsumer<String, String> consumer;

    private static final int nrFakeEvents = 1000000;

    public static void main(String[] args) {
        KafkaManager kafkaManager = new KafkaManager();

        //Thread manager
        executor = Executors.newFixedThreadPool(10);

        //Producer
        producer = kafkaManager.createProducer();

        //Consumer
        consumer = kafkaManager.createConsumer("myGroupID");
        consumer.subscribe(List.of(Constants.topicA));

        executor.execute(() -> {
            // poll for new data
            while (!shutdown) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));

                for (ConsumerRecord<String, String> record : records) {
                    Date start = new Date();

                    for (int i = 0; i <= nrFakeEvents; i++) {
                        ProducerRecord<String, String> fakeRecord = new ProducerRecord<>(
                                Constants.topicB,
                                i + " - \"" + record.value() + "\""
                                );

                        producer.send(fakeRecord);
                    }

                    Date end = new Date();
                    long diff = end.getTime() - start.getTime();

                    ProducerRecord<String, String> fakeRecord = new ProducerRecord<>(
                            Constants.topicB,
                            "Took " + diff + " ms"
                            );

                    producer.send(fakeRecord);
                }
            }

            consumer.close();
        });

        SpringApplication.run(DemoApplication.class, args);
    }

    @PreDestroy
    public void onDestroy() throws Exception {
        System.out.println("Spring Container is destroyed!");

        shutdown = true;

        //Close executor
        executor.shutdown();

        //Close Kafka stuff
        producer.close();
    }

}
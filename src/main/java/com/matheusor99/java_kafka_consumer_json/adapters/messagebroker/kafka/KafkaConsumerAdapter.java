package com.matheusor99.java_kafka_consumer_json.adapters.messagebroker.kafka;

import com.matheusor99.java_kafka_consumer_json.adapters.messagebroker.kafka.config.KafkaConfig;
import com.matheusor99.java_kafka_consumer_json.domain.event.Product;
import com.matheusor99.java_kafka_consumer_json.ports.inbound.MessageBrokerPortInbound;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Collections;

@Service
@Slf4j
public class KafkaConsumerAdapter {
    private KafkaConsumer<String, Product> consumer;
    private final String topicProduct;
    private final MessageBrokerPortInbound messageBrokerPortInbound;

    @Autowired
    public KafkaConsumerAdapter(final KafkaConfig config,
                                @Value("${kafka.topic-product}") final String topicProduct,
                                final MessageBrokerPortInbound messageBrokerPortInbound) {
        consumer = new KafkaConsumer<>(config.propertiesConsumer());
        this.topicProduct = config.getTopicProduct();
        this.messageBrokerPortInbound = messageBrokerPortInbound;
    }

    @PostConstruct
    public void consume() {
        new Thread(this::processRecords).start();
    }

    private void processRecords() {
        consumer.subscribe(Collections.singletonList(topicProduct));
        log.info("Kafka Consumer started. Listening to topic: {}", topicProduct);
        
        while (true) {
            try{
                var records = consumer.poll(Duration.ofMillis(1000));
                if (!records.isEmpty()) {
                    log.info("Records received: " + records.count());
                    
                    for (var record : records) {
                        processMessage(record);
                    }
                    consumer.commitSync();
                    log.info("Records processed and committed");
                }
            } catch (Exception e) {
                log.error("Error processing records: " + e.getMessage());
            }

        }
    }

    private void processMessage(final ConsumerRecord<String, Product> record) {
        messageBrokerPortInbound.consumeProduct(record.key(), record.value());
    }
}

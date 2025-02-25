package com.matheusor99.java_kafka_consumer_json.adapters.messagebroker.kafka.config;

import com.matheusor99.java_kafka_consumer_json.domain.event.Product;
import lombok.Data;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.validation.annotation.Validated;

import java.util.Properties;

@Configuration
@ConfigurationProperties(prefix = "kafka", ignoreInvalidFields = true)
@Data
@Validated
@Order(1)
public class KafkaConfig {
    private String bootstrapServers;
    private String topicProduct;
    private String groupId;
    private String autoOffset;
    private boolean enableAutoCommit;

    @Bean
    public Properties propertiesConsumer() {
        var properties = new Properties();

        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, autoOffset);
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, enableAutoCommit);
        properties.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 1);
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class.getName());
        properties.put(JsonDeserializer.VALUE_DEFAULT_TYPE, Product.class.getName());
        properties.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        properties.put(JsonDeserializer.USE_TYPE_INFO_HEADERS, false);

        return properties;
    }
}

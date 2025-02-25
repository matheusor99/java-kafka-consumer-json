package com.matheusor99.java_kafka_consumer_json.domain;

import com.matheusor99.java_kafka_consumer_json.domain.event.Product;
import com.matheusor99.java_kafka_consumer_json.ports.inbound.MessageBrokerPortInbound;
import com.matheusor99.java_kafka_consumer_json.ports.outbound.DatabasePortOutbound;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ApplicationCoreImpl implements MessageBrokerPortInbound {

    private final DatabasePortOutbound databasePortOutbound;

    @Autowired
    public ApplicationCoreImpl(final DatabasePortOutbound databasePortOutbound) {
        this.databasePortOutbound = databasePortOutbound;
    }

    @Override
    public void consumeProduct(final String key, final Product value) {
        final long initProcess = System.currentTimeMillis();

        try {
            databasePortOutbound.saveProduct(key, value);
        } catch (Exception e) {
            log.error("Error processing product: {}", e.getMessage());
        } finally {
            log.info("Processing time: {} ms", System.currentTimeMillis() - initProcess);
        }
    }
}

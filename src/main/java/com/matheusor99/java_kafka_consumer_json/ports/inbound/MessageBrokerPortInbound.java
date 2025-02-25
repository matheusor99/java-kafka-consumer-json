package com.matheusor99.java_kafka_consumer_json.ports.inbound;

import com.matheusor99.java_kafka_consumer_json.domain.event.Product;

public interface MessageBrokerPortInbound {
    void consumeProduct(String key, Product value);
}

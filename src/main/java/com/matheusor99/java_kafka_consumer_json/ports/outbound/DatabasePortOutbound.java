package com.matheusor99.java_kafka_consumer_json.ports.outbound;

import com.matheusor99.java_kafka_consumer_json.domain.event.Product;

public interface DatabasePortOutbound {
    void saveProduct(String key, Product value);
}

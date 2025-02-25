package com.matheusor99.java_kafka_consumer_json.adapters.database;

import com.matheusor99.java_kafka_consumer_json.adapters.database.entity.ProductData;
import com.matheusor99.java_kafka_consumer_json.adapters.database.repository.ProductRepository;
import com.matheusor99.java_kafka_consumer_json.domain.event.Product;
import com.matheusor99.java_kafka_consumer_json.ports.outbound.DatabasePortOutbound;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DatabaseH2Adapter implements DatabasePortOutbound {
    private final ProductRepository productRepository;

    @Autowired
    public DatabaseH2Adapter(final ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void saveProduct(final String key, final Product value) {
        final ProductData productData = ProductData.getInstance(key, value);
        productRepository.save(productData);
        log.info("Product saved: {}", productData);
    }
}

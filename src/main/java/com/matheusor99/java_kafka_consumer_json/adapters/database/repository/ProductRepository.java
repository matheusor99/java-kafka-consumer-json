package com.matheusor99.java_kafka_consumer_json.adapters.database.repository;

import com.matheusor99.java_kafka_consumer_json.adapters.database.entity.ProductData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<ProductData, String> {
}

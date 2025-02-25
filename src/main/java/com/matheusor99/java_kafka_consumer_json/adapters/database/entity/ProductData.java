package com.matheusor99.java_kafka_consumer_json.adapters.database.entity;

import com.matheusor99.java_kafka_consumer_json.domain.event.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "product")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductData {

    @Id
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private Double price;

    public static ProductData getInstance(final String id, final Product value) {
        return ProductData.builder()
                .id(id)
                .name(value.name())
                .description(value.description())
                .price(value.price())
                .build();
    }

    @Override
    public String toString() {
        return "ProductData{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                '}';
    }

}

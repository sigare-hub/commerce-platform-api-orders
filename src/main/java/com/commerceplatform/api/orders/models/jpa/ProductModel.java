package com.commerceplatform.api.orders.models.jpa;

import jakarta.persistence.*;

@Entity
@Table(name = "products")
public class ProductModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id", nullable = false)
    private Long id;
    private String name;
    private String description;

    @Column(name = "image_url")
    private String imageUrl;
}

package com.commerceplatform.api.orders.models.jpa;

import jakarta.persistence.*;

@Entity
@Table(name = "customers")
public class CustomerModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id", nullable = false)
    private Long id;
    private String fullName;
}

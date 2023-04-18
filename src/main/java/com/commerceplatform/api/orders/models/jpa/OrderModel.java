package com.commerceplatform.api.orders.models.jpa;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "orders")
public class OrderModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id", nullable = false)
    private Long id;
    private Long userId;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
        name = "orders_list",
        uniqueConstraints = @UniqueConstraint(columnNames = { "order_id", "product_id" }),
        joinColumns = @JoinColumn(name = "order_id"),
        inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<ProductModel> products;
    private String status;
    private Double total;
    @Column(name = "created_at")
    private LocalDate createdAt = LocalDate.now();
}

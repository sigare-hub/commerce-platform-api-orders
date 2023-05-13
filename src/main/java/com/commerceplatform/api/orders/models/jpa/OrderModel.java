package com.commerceplatform.api.orders.models.jpa;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "order")
public class OrderModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private Long customerId;

    private List<Long> productIds;

    private String status;
    private Double total;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    public OrderModel() {
    }

    public OrderModel(Long id, Long customerId, List<Long> productIds, String status, Double total, LocalDateTime createdAt) {
        this.id = id;
        this.customerId = customerId;
        this.productIds = productIds;
        this.status = status;
        this.total = total;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public List<Long> getproductIds() {
        return productIds;
    }

    public void setproductIds(List<Long> productIds) {
        this.productIds = productIds;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}

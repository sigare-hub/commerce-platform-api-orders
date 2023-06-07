package com.commerceplatform.api.orders.models.jpa;

import com.commerceplatform.api.orders.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.Builder;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Entity
@Table(name = "orders")
@Builder
public class OrderModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double total;

//    @ManyToOne(fetch = FetchType.LAZY, optional = true)
//    @JoinColumn(name = "customer_id", referencedColumnName = "id", nullable = true)
//    private Customer customer;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private LocalDateTime orderPlacedIn = LocalDateTime.now(ZoneOffset.UTC);

    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)
    @JoinColumn(name = "order_id",referencedColumnName = "id",insertable = false,updatable = false)
    private List<OrderItem> orderItems;

    public OrderModel() {
    }

    public OrderModel(Long id, double total, OrderStatus status, LocalDateTime orderPlacedIn, List<OrderItem> orderItems) {
        this.id = id;
        this.total = total;
        this.status = status;
        this.orderPlacedIn = orderPlacedIn;
        this.orderItems = orderItems;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public LocalDateTime getOrderPlacedIn() {
        return orderPlacedIn;
    }

    public void setOrderPlacedIn(LocalDateTime orderPlacedIn) {
        this.orderPlacedIn = orderPlacedIn;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }
}

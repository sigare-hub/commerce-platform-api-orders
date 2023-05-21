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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private LocalDateTime orderPlacedIn = LocalDateTime.now(ZoneOffset.UTC);

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<OrderItem> orderItems;

    public OrderModel() {
    }

    public OrderModel(Long id, double total, Customer customer, OrderStatus status, LocalDateTime orderPlacedIn, List<OrderItem> orderItems) {
        this.id = id;
        this.total = total;
        this.customer = customer;
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

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
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

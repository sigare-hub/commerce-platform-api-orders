package com.commerceplatform.api.orders.repositories;

import com.commerceplatform.api.orders.models.jpa.OrderItem;
import com.commerceplatform.api.orders.models.jpa.OrderModel;
import jakarta.persistence.criteria.Order;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<OrderModel, Long> {
    List<OrderItem> findByOrderItems(OrderItem orderItem);
//    Optional<Order> findAllByCustomerId(Long customerId);
    @EntityGraph(attributePaths = "orderItems.product")
    List<OrderModel> findAll();
}

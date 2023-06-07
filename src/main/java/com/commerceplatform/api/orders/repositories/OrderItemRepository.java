package com.commerceplatform.api.orders.repositories;

import com.commerceplatform.api.orders.models.jpa.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    Optional<List<OrderItem>> findAllByOrderId(Long orderId);
}

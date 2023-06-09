package com.commerceplatform.api.orders.repositories;

import com.commerceplatform.api.orders.models.jpa.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}

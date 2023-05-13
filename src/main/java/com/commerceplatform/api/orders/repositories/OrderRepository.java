package com.commerceplatform.api.orders.repositories;

import com.commerceplatform.api.orders.models.jpa.OrderModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderModel, Long> {}

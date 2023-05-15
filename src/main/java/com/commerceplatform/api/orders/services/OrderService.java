package com.commerceplatform.api.orders.services;

import com.commerceplatform.api.orders.enums.OrderStatus;
import com.commerceplatform.api.orders.integrations.api.products.ApiProductsIntegration;
import com.commerceplatform.api.orders.integrations.api.products.dtos.ProductDto;
import com.commerceplatform.api.orders.models.jpa.OrderModel;
import com.commerceplatform.api.orders.repositories.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final ApiProductsIntegration apiProductsIntegration;

    public OrderService(OrderRepository orderRepository, ApiProductsIntegration apiProductsIntegration) {
        this.orderRepository = orderRepository;
        this.apiProductsIntegration = apiProductsIntegration;
    }

    public OrderModel createOrder(Long customerId, List<Integer> productIds) {
        var order = new OrderModel();
        order.setStatus(OrderStatus.ORDER_PLACED);
        return orderRepository.save(order);
    }
}

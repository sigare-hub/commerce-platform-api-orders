package com.commerceplatform.api.orders.services;

import com.commerceplatform.api.orders.dtos.OrderDto;
import com.commerceplatform.api.orders.dtos.OrderItemDto;
import com.commerceplatform.api.orders.integrations.api.connections.ApiProductsIntegration;
import com.commerceplatform.api.orders.models.jpa.OrderModel;
import com.commerceplatform.api.orders.repositories.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    private final ApiProductsIntegration apiProductsIntegration;
    private final OrderRepository orderRepository;

    public OrderService(ApiProductsIntegration apiProductsIntegration, OrderRepository orderRepository) {
        this.apiProductsIntegration = apiProductsIntegration;
        this.orderRepository = orderRepository;
    }

    @Transactional
    public OrderModel createOrder(OrderDto input) {
        List<Long> ids = new ArrayList<>();
        List<OrderItemDto> orderItems = input.getOrderItems();

        if(!orderItems.isEmpty()) {
            for (OrderItemDto orderItem : orderItems) {
                ids.add(orderItem.getProductId());
            }
        }

        var products = apiProductsIntegration.getProductsByIds(ids);
        System.out.println(products);
//
//        var customer = new Customer();
        return null;
    }
//
//    @Transactional
//    public OrderModel createOrder(OrderDto input) {
//        var customer = new Customer();
//        customer.setId(input.getCustomer());
//
//        List<OrderItem> validOrderItems = new ArrayList<>();
//
//        for (OrderItem orderItemDto : input.getOrderItems()) {
//            Long productId = orderItemDto.getProduct();
//            Optional<Product> productOptional = productRepository.findById(productId);
//
//            if (productOptional.isPresent()) {
//                Product product = productOptional.get();
//
//                OrderItem orderItem = new OrderItem();
//                orderItem.setProduct(product);
//                orderItem.setQuantity(orderItemDto.getQuantity());
//                orderItem.setPrice(orderItemDto.getPrice());
//
//                validOrderItems.add(orderItem);
//            }
//        }
//
//        OrderModel order = OrderDtoMapper.mapper(customer, input);
//        order.setOrderItems(validOrderItems);
//
//        return orderRepository.save(order);
//    }

    public List<OrderModel> findAll() {
        return this.orderRepository.findAll();
    }

    public Optional<OrderModel> findById(Long orderId) {
        return this.orderRepository.findById(orderId);
    }
}

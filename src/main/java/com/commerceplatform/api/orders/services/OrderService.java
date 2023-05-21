package com.commerceplatform.api.orders.services;

import com.commerceplatform.api.orders.dtos.OrderDto;
import com.commerceplatform.api.orders.dtos.OrderItemDto;
import com.commerceplatform.api.orders.dtos.ProductDto;
import com.commerceplatform.api.orders.dtos.mappers.OrderDtoMapper;
import com.commerceplatform.api.orders.integrations.api.connections.ApiProductsIntegration;
import com.commerceplatform.api.orders.models.jpa.Customer;
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
        List<OrderItemDto> orderItems = input.getOrderItems();

        if(orderItems.isEmpty()) {
            throw new RuntimeException("Você não pode criar um pedido sem produtos");
        }

        List<OrderItemDto> validOrderItems = validateOrderItems(orderItems);

        var customer = new Customer();
        customer.setId(input.getCustomer());

        if(validOrderItems.size() == orderItems.size()) {
            return orderRepository.save(OrderDtoMapper.mapper(input, customer));
        } else {
            System.out.println("Alguns itens de pedido são inválidos.");
        }
        throw new RuntimeException("Bad request");
    }

    private List<OrderItemDto> validateOrderItems(List<OrderItemDto> orderItems) {
        // Pegar somente os ids de cada item no pedido
        List<Long> productIds = orderItems.stream()
                .map(OrderItemDto::getProductId).toList();

        // Verificar na API de produtos se os produtos existem
        List<ProductDto> products = apiProductsIntegration.getProductsByIds(productIds);
        var validOrderItems = new ArrayList<OrderItemDto>();
        var invalidOrderItems = new ArrayList<OrderItemDto>();

        /// Validar se o produto existe e salvar na lista de itens válidos
        for (OrderItemDto orderItem : orderItems) {
            Long orderItemId = orderItem.getProductId();
            boolean productExists = products
                .stream()
                .anyMatch(product -> product.getId().equals(orderItemId));

            if (productExists) {
                validOrderItems.add(orderItem);
            } else {
                invalidOrderItems.add(orderItem);
            }
        }
        if(!invalidOrderItems.isEmpty()) {
            // Lançar uma custom exception que retorne esses ids inválidos em um JSON
            throw new RuntimeException("Invalid products: " + invalidOrderItems);
        }

        return validOrderItems;
    }

    public List<OrderModel> findAll() {
        return this.orderRepository.findAll();
    }

    public Optional<OrderModel> findById(Long orderId) {
        return this.orderRepository.findById(orderId);
    }
}

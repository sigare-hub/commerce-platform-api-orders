package com.commerceplatform.api.orders.services;

import com.commerceplatform.api.orders.dtos.OrderDto;
import com.commerceplatform.api.orders.dtos.OrderItemDto;
import com.commerceplatform.api.orders.dtos.ProductDto;
import com.commerceplatform.api.orders.dtos.mappers.OrderDtoMapper;
import com.commerceplatform.api.orders.exceptions.BadRequestException;
import com.commerceplatform.api.orders.exceptions.ValidationException;
import com.commerceplatform.api.orders.integrations.api.connections.ApiProductsIntegration;
import com.commerceplatform.api.orders.models.jpa.Customer;
import com.commerceplatform.api.orders.models.jpa.OrderItem;
import com.commerceplatform.api.orders.models.jpa.OrderModel;
import com.commerceplatform.api.orders.models.jpa.Product;
import com.commerceplatform.api.orders.repositories.CustomerRepository;
import com.commerceplatform.api.orders.repositories.OrderItemRepository;
import com.commerceplatform.api.orders.repositories.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OrderService {
    private final ApiProductsIntegration apiProductsIntegration;
    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    public OrderService(ApiProductsIntegration apiProductsIntegration, CustomerRepository customerRepository, OrderRepository orderRepository, OrderItemRepository orderItemRepository) {
        this.apiProductsIntegration = apiProductsIntegration;
        this.customerRepository = customerRepository;
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
    }

    @Transactional
    public OrderModel createOrder(OrderDto input) {
        List<OrderItemDto> orderItems = input.getOrderItems();
        var customerId = input.getCustomerId();

        if(Objects.nonNull(customerId)) {
            // apiCustomerIntegration para verificar se o usuario eh válido
            var customer = new Customer();
            customer.setId(input.getCustomerId());
            customerRepository.save(customer);
        }

        if(orderItems.isEmpty()) {
            throw new BadRequestException("You cannot create an order without at least informing an item");
        }

        List<OrderItemDto> validOrderItems = validateOrderItems(orderItems);
        List<OrderItem> newOrderItems = new ArrayList<>();
//
//        for (OrderItemDto item : validOrderItems) {
//            var newOrderItem = new OrderItem();
//            var product = new Product();
//
//            product.setId(item.getProductId());
//            // Defina os atributos do newOrderItem com base nos dados do item, se necessário
//            newOrderItem.setProduct(product); // Exemplo: associando um novo objeto Product
//            newOrderItem.setQuantity(item.getQuantity());
//            newOrderItem.setPrice(item.getPrice());
//            newOrderItems.add(newOrderItem);
//        }
//
//        orderItemRepository.saveAll(newOrderItems); // Salva todos os OrderItems de uma vez
//
//        if(validOrderItems.size() == orderItems.size()) {
//            return orderRepository.save(OrderDtoMapper.mapper(input));
//        }

        throw new BadRequestException("Unable to create order");
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
            var invalidResponse = new HashMap<String, List<String>>();
            var invalidIds = new ArrayList<String>();

            for (OrderItemDto item :  validOrderItems) {
                invalidIds.add(item.getProductId().toString());
            }
            invalidResponse.put("invalid_products_ids", invalidIds);
            throw new ValidationException(invalidResponse);
        }

        return validOrderItems;
    }

    public List<OrderModel> findAll() {
        return this.orderRepository.findAll();
    }

    public Optional<OrderModel> findById(Long orderId) {
        return this.orderRepository.findById(orderId);
    }

    public List<OrderItem> saveAllOrderItems(List<OrderItem> orderItems) {
        var opa = this.orderRepository.saveAllOrderItem(orderItems);

        opa.get();
    }
}

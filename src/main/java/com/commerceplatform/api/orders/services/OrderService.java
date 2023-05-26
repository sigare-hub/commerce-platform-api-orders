package com.commerceplatform.api.orders.services;

import com.commerceplatform.api.orders.dtos.OrderDto;
import com.commerceplatform.api.orders.dtos.OrderItemDto;
import com.commerceplatform.api.orders.dtos.ProductDto;
import com.commerceplatform.api.orders.enums.OrderStatus;
import com.commerceplatform.api.orders.exceptions.BadRequestException;
import com.commerceplatform.api.orders.exceptions.ValidationException;
import com.commerceplatform.api.orders.integrations.api.connections.ApiProductsIntegration;
import com.commerceplatform.api.orders.models.jpa.Customer;
import com.commerceplatform.api.orders.models.jpa.OrderItem;
import com.commerceplatform.api.orders.models.jpa.OrderModel;
import com.commerceplatform.api.orders.models.jpa.Product;
import com.commerceplatform.api.orders.repositories.CustomerRepository;
import com.commerceplatform.api.orders.repositories.OrderRepository;
import com.commerceplatform.api.orders.repositories.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OrderService {
    private final ApiProductsIntegration apiProductsIntegration;
    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public OrderService(ApiProductsIntegration apiProductsIntegration, CustomerRepository customerRepository, OrderRepository orderRepository, ProductRepository productRepository) {
        this.apiProductsIntegration = apiProductsIntegration;
        this.customerRepository = customerRepository;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    // secured route -> necessary bearer token
    @Transactional
    public OrderModel createOrder(OrderDto input) {
        try {
            var customerId = input.getCustomerId();
            var orderItemsDto = input.getOrderItems();

            if(Objects.nonNull(customerId)) {
                var existCustomer = customerRepository.findById(customerId);
                if(existCustomer.isEmpty()) {
                    // apiCustomerIntegration para verificar se o usuario eh válido
                    var customer = new Customer();
                    customer.setId(input.getCustomerId());
                    customerRepository.save(customer);
                }
            }

            if(orderItemsDto.isEmpty()) {
                throw new BadRequestException("You cannot create an order without at least informing an item");
            }

            List<OrderItemDto> validatedOrderItems = validateOrderItems(orderItemsDto);

            var mapperOrderItems = new ArrayList<OrderItem>();

            for(OrderItemDto validatedItem : validatedOrderItems) {
                Optional<Product> existingProductOptional = productRepository.findById(validatedItem.getProductId());

                var orderItem = new OrderItem();

                if(existingProductOptional.isPresent()) {
                    orderItem.setProduct(existingProductOptional.get());
                } else {
                    var product = new Product();
                    product.setId(validatedItem.getProductId());

                    var createdProduct = productRepository.save(product);
                    orderItem.setProduct(createdProduct);
                }
                orderItem.setQuantity(validatedItem.getQuantity());
                orderItem.setPrice(validatedItem.getPrice());
                mapperOrderItems.add(orderItem);
            }

            var order = new OrderModel();
            var total = mapperOrderItems.stream()
                .mapToDouble(orderItem -> orderItem.getPrice() * orderItem.getQuantity())
                .sum();

            order.setTotal(total);
            order.setStatus(OrderStatus.PROCESSING);
            order.setOrderItems(mapperOrderItems);

            return orderRepository.save(order);
        } catch (Exception e) {
            throw new BadRequestException("Unable to create order");
        }
    }

    private List<OrderItemDto> validateOrderItems(List<OrderItemDto> orderItems) {
        try {
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
        } catch (Exception e) {
            throw new BadRequestException("Unable to save products");
        }
    }

    public List<OrderModel> findAll() {
        return this.orderRepository.findAll();
    }

    public Optional<OrderModel> findById(Long orderId) {
        return this.orderRepository.findById(orderId);
    }
}

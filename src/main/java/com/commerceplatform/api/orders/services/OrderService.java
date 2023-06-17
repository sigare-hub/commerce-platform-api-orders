package com.commerceplatform.api.orders.services;

import com.commerceplatform.api.orders.dtos.CustomerDto;
import com.commerceplatform.api.orders.dtos.OrderDto;
import com.commerceplatform.api.orders.dtos.OrderItemDto;
import com.commerceplatform.api.orders.dtos.ProductDto;
import com.commerceplatform.api.orders.dtos.mappers.OrderDtoMapper;
import com.commerceplatform.api.orders.enums.OrderStatus;
import com.commerceplatform.api.orders.exceptions.BadRequestException;
import com.commerceplatform.api.orders.exceptions.NotFoundException;
import com.commerceplatform.api.orders.exceptions.ValidationException;
import com.commerceplatform.api.orders.integrations.api.connections.ApiAccountsIntegrationApi;
import com.commerceplatform.api.orders.integrations.api.connections.ApiProductsIntegration;
import com.commerceplatform.api.orders.models.jpa.Customer;
import com.commerceplatform.api.orders.models.jpa.OrderItem;
import com.commerceplatform.api.orders.models.jpa.Order;
import com.commerceplatform.api.orders.models.jpa.Product;
import com.commerceplatform.api.orders.repositories.CustomerRepository;
import com.commerceplatform.api.orders.repositories.OrderItemRepository;
import com.commerceplatform.api.orders.repositories.OrderRepository;
import com.commerceplatform.api.orders.repositories.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OrderService {
    private final ApiAccountsIntegrationApi apiAccountsIntegration;
    private final ApiProductsIntegration apiProductsIntegration;
    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final OrderItemRepository orderItemRepository;

    public OrderService(
        ApiAccountsIntegrationApi apiAccountsIntegration,
        ApiProductsIntegration apiProductsIntegration,
        CustomerRepository customerRepository,
        OrderRepository orderRepository,
        ProductRepository productRepository,
        OrderItemRepository orderItemRepository
    ) {
        this.apiAccountsIntegration = apiAccountsIntegration;
        this.apiProductsIntegration = apiProductsIntegration;
        this.customerRepository = customerRepository;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.orderItemRepository = orderItemRepository;
    }

    public List<OrderDto> findAll() {
        var orders = orderRepository.findAll();
        var orderDtos = new ArrayList<OrderDto>();

        for(Order order : orders) {
            var orderDto = getOrderItemsPerOrder(order);
            orderDtos.add(orderDto);
        }

        return orderDtos;
    }

    public OrderDto findById(Long id) {
        var order = orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Order not found"));
        return getOrderItemsPerOrder(order);
    }

    // secured route -> necessary bearer token
    @Transactional
    public OrderDto createOrder(OrderDto input) {
        var inputCustomerId = input.getCustomerId();
        var inputOrderItemsDto = input.getOrderItems();

        if(Objects.isNull(inputCustomerId)) {
            throw new BadRequestException("Attribute 'customer_id' cannot be null");
        }

        if(inputOrderItemsDto.isEmpty()) {
            throw new BadRequestException("You cannot create an order without at least informing an item");
        }

        Customer validatedCustomer = validateCustomerOnApi(inputCustomerId);
        List<OrderItemDto> validatedOrderItems = validateOrderItemsOnApi(inputOrderItemsDto);

        ArrayList<OrderItem> mapperOrderItems = getOrderItems(validatedOrderItems);

        var order = new Order();
        var total = mapperOrderItems.stream()
            .mapToDouble(orderItem -> orderItem.getPrice() * orderItem.getQuantity())
            .sum();

        order.setTotal(total);
        order.setCustomer(validatedCustomer);
        order.setStatus(OrderStatus.ORDER_PLACED);

        var createdOrder = orderRepository.save(order);

        for(OrderItem orderItem : mapperOrderItems) {
            orderItem.setOrder(createdOrder);
            orderItemRepository.save(orderItem);
        }

        return getOrderItemsPerOrder(createdOrder);
    }

    private ArrayList<OrderItem> getOrderItems(List<OrderItemDto> validatedOrderItems) {
        var mapperOrderItems = new ArrayList<OrderItem>();

        for(OrderItemDto validatedItem : validatedOrderItems) {
            var existingProductOptional = productRepository.findByExternalId(validatedItem.getProductId());

            var orderItem = new OrderItem();

            if(existingProductOptional.isPresent()) {
                orderItem.setProduct(existingProductOptional.get());
            } else {
                var product = new Product();
                product.setExternalId(validatedItem.getProductId());

                var createdProduct = productRepository.save(product);
                orderItem.setProduct(createdProduct);
            }
            orderItem.setQuantity(validatedItem.getQuantity());
            orderItem.setPrice(validatedItem.getPrice());
            mapperOrderItems.add(orderItem);
        }
        return mapperOrderItems;
    }


    private Customer validateCustomerOnApi(Long id) {
        CustomerDto customerDto = apiAccountsIntegration.getCustomerById(id);

        var localCustomerOpt = customerRepository.findById(customerDto.getId());

        if(localCustomerOpt.isEmpty()) {
            var customer = new Customer();
            customer.setId(customerDto.getId());
            return customerRepository.save(customer);
        }
        return localCustomerOpt.get();
    }

    private List<OrderItemDto> validateOrderItemsOnApi(List<OrderItemDto> orderItems) {
        List<Long> productIds = orderItems.stream()
            .map(OrderItemDto::getProductId).toList();

        List<ProductDto> products = apiProductsIntegration.getProductsByIds(productIds);
        var validOrderItems = new ArrayList<OrderItemDto>();
        var invalidOrderItems = new ArrayList<OrderItemDto>();

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

    private OrderDto getOrderItemsPerOrder(Order order) {
        var orderItems = orderItemRepository.findOrderItemsByOrderId(order.getId());
        var orderItemsDto = new ArrayList<OrderItemDto>();

        for(OrderItem orderItem : orderItems) {
            orderItemsDto.add(OrderItemDto.builder()
                .id(orderItem.getId())
                .orderId(orderItem.getOrder().getId())
                .price(orderItem.getPrice())
                .productId(orderItem.getProduct().getId())
                .quantity(orderItem.getQuantity())
                .build());
        }

        return OrderDtoMapper.toDto(order, orderItemsDto);
    }

    public void updateOrderStatus(OrderDto orderDto) {
        var order = findById(orderDto.getId());
        order.setStatus(orderDto.getStatus());
        orderRepository.save(OrderDtoMapper.toEntity(order));
    }
}

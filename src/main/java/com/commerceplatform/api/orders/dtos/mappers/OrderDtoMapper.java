package com.commerceplatform.api.orders.dtos.mappers;

import com.commerceplatform.api.orders.dtos.OrderDto;
import com.commerceplatform.api.orders.dtos.OrderItemDto;
import com.commerceplatform.api.orders.models.jpa.Customer;
import com.commerceplatform.api.orders.models.jpa.OrderItem;
import com.commerceplatform.api.orders.models.jpa.OrderModel;
import com.commerceplatform.api.orders.models.jpa.Product;

import java.util.ArrayList;

public class OrderDtoMapper {
    private OrderDtoMapper() {
        throw new IllegalStateException("Você não pode instanciar essa classe de utilitário");
    }


    public static OrderModel mapper(OrderDto orderDto, Customer customer) {
        var order = new OrderModel();
        var orderItems = new ArrayList<OrderItem>();

        order.setCustomer(customer);
        order.setTotal(orderDto.getTotal());
        order.setStatus(orderDto.getStatus());

        for (OrderItemDto orderItemDto : orderDto.getOrderItems()) {
            var orderItem = new OrderItem();
            var product = new Product();
            product.setId(orderItemDto.getProductId());

            orderItem.setProduct(product);
            orderItem.setPrice(orderItemDto.getPrice());
            orderItem.setQuantity(orderItemDto.getQuantity());
            orderItem.setOrder(order);

            orderItems.add(orderItem);
        }

        order.setOrderItems(orderItems);

        return OrderModel.builder()
            .id(orderDto.getId())
            .orderItems(orderItems)
            .total(orderDto.getTotal())
            .status(orderDto.getStatus())
            .orderPlacedIn(orderDto.getOrderPlacedIn())
            .customer(customer)
            .build();
    }
}

package com.commerceplatform.api.orders.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Queue;

@Configuration
public class MessengerConfig {
    public static final String QUEUE = "ORDERS_QUEUE";
    public static final String EXCHANGE = "ORDERS-EXCHANGE";
    public static final String ROUTING_KEY = "ORDERS_ROUTING_KEY";

    @Bean
    public Queue queue() {
        return new Queue(QUEUE);
    }
}

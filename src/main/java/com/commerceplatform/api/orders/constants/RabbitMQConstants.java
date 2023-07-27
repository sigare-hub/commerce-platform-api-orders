package com.commerceplatform.api.orders.constants;

public class RabbitMQConstants {
    public static final String ORDER_QUEUE =  "ORDER_QUEUE";
    public static final String PRODUCT_QUEUE =  "PRODUCT_QUEUE";


    private RabbitMQConstants() {
        throw new IllegalStateException("Utility class");
    }
}

package com.commerceplatform.api.orders.connections;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQConnection {
    private final String EXCHANGE_NAME = "amqp.direct";

    private Queue queue(String queueName) {
        return new Queue(queueName, true, false, false);
    }

    private DirectExchange directExchange() {
        return new DirectExchange(EXCHANGE_NAME);
    }

    private Binding binding(Queue queue, DirectExchange directExchange) {
        return new Binding(
            queue.getName(),
            Binding.DestinationType.EXCHANGE,
            directExchange.getName(),
            queue.getName(),
            null);
    }

    private void add(String queue) {
        this.queue(queue);
    }
}

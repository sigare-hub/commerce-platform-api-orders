package com.commerceplatform.api.orders.connections;

import com.commerceplatform.api.orders.constants.RabbitMQConstants;
import jakarta.annotation.PostConstruct;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQConnection {
    private final String EXCHANGE_NAME = "amqp.direct";
    private final AmqpAdmin amqpAdmin;

    public  RabbitMQConnection(AmqpAdmin amqpAdmin) {
        this.amqpAdmin = amqpAdmin;
    }

    private Queue queue(String queueName) {
        return new Queue(queueName, true, false, false);
    }

    private DirectExchange directExchange() {
        return new DirectExchange(EXCHANGE_NAME);
    }

    private Binding binding(Queue queue, DirectExchange directExchange) {
        return new Binding(
            queue.getName(),
            Binding.DestinationType.QUEUE,
            directExchange.getName(),
            queue.getName(),
            null);
    }

    @PostConstruct // assim que a classe for construir pelo spring, ele vai executar esse metodo
    private void add() {
        Queue orderQueue = this.queue(RabbitMQConstants.ORDER_QUEUE);
        Queue productQueue = this.queue(RabbitMQConstants.PRODUCT_QUEUE);

        DirectExchange exchange = this.directExchange();

        Binding orderBiding = this.binding(orderQueue, exchange);
        Binding productBinding = this.binding(productQueue, exchange);

        this.amqpAdmin.declareQueue(orderQueue);
        this.amqpAdmin.declareQueue(productQueue);

        this.amqpAdmin.declareExchange(exchange);

        this.amqpAdmin.declareBinding(orderBiding);
        this.amqpAdmin.declareBinding(productBinding);
    }
}

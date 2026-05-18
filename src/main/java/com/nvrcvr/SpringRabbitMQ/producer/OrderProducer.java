package com.nvrcvr.SpringRabbitMQ.producer;

import com.nvrcvr.SpringRabbitMQ.dto.Order;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendOrder(Order order, String eventType) {

        String routingKey="order." +eventType;

        rabbitTemplate.convertAndSend("order.exchange",routingKey,order);

        System.out.println("Sent order with ID " + order.getOrderId() + " and routing key " + routingKey);

    }
}

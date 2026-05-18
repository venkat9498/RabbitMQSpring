package com.nvrcvr.SpringRabbitMQ.consumer;


import com.nvrcvr.SpringRabbitMQ.dto.Order;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
public class OrderGenericConsumer {

    private static final Logger log = LoggerFactory.getLogger(OrderGenericConsumer.class);

    @RabbitListener(queues ="order.generic.queue",ackMode = "MANUAL")
    public void orderGenericConsumer(Order order, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag){
        try {
            log.info("Order generic event: {}", order.getOrderId());
            // Handle other events (update, etc.)
            channel.basicAck(tag, false);
        } catch (Exception e) {
            log.error("Failed to process generic order event", e);
            try {
                channel.basicNack(tag, false, false);
            } catch (IOException ex) { log.error("Nack failed", ex); }
        }
    }
}

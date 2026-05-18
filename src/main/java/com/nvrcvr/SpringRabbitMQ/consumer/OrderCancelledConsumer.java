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
public class OrderCancelledConsumer {

    private static final Logger log= LoggerFactory.getLogger(OrderCancelledConsumer.class);
    @RabbitListener(queues = "order.cancel.queue", ackMode = "MANUAL")
    public void consume(Order order, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) {
        try {
            log.info("Order cancelled event: {}", order.getOrderId());
            // Handle other events (update, etc.)
            channel.basicAck(tag, false);
        } catch (Exception e) {
            log.error("Failed to process cancelled order event", e);
            try {
                channel.basicNack(tag, false, false);
            } catch (IOException ex) { log.error("Nack failed", ex); }
        }
    }
}
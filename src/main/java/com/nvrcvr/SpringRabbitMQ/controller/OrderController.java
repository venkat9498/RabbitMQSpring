package com.nvrcvr.SpringRabbitMQ.controller;

import com.nvrcvr.SpringRabbitMQ.dto.Order;
import com.nvrcvr.SpringRabbitMQ.producer.OrderProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    private OrderProducer orderProducer;

    @PostMapping("/{eventType}")
    public String orderProducer(@RequestBody Order order, @PathVariable("eventType") String eventType){

        orderProducer.sendOrder(order,eventType);
        return "Order sent to queue: " + order.getOrderId();
    }
}

package com.nvrcvr.SpringRabbitMQ.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class Order {

    private String orderId;
    private double amount;
    private String email;
}

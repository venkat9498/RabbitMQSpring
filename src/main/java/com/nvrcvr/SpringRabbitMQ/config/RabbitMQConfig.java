package com.nvrcvr.SpringRabbitMQ.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new JacksonJsonMessageConverter();
    }
    @Bean
    public Queue orderCreatedQueue(){
        return QueueBuilder.durable("order.create.queue")
                .withArgument("x-dead-letter-exchange","dlx.exchange")
                .withArgument("x-dead-letter-routing-key","dead")
                .build();
    }

    @Bean
    public Queue orderCancelQueue(){
        return QueueBuilder.durable("order.cancel.queue")
                .withArgument("x-dead-letter-exchange","dlx.exchange")
                .withArgument("x-dead-letter-routing-key","dead")
                .build();
    }
    @Bean
    public Queue orderGenericQueue(){
        return QueueBuilder.durable("order.generic.queue")
                .withArgument("x-dead-letter-exchange","dlx.exchange")
                .withArgument("x-dead-letter-routing-key","dead")
                .build();
    }

    @Bean
    public DirectExchange orderExchange(){
        return new DirectExchange("order.exchange");
    }

    @Bean
    public Binding orderCreatedBinding(@Qualifier("orderCreatedQueue") Queue orderCreateQueue, DirectExchange orderExchange){
        return BindingBuilder.bind(orderCreateQueue)
                .to(orderExchange)
                .with("order.create");
    }

    @Bean
    public Binding orderCancelBinding(@Qualifier("orderCancelQueue") Queue orderCancelQueue,DirectExchange orderExchange){
        return BindingBuilder.bind(orderCancelQueue)
                .to(orderExchange)
                .with("order.cancel");
    }
    @Bean
    public Binding orderGenericBinding(@Qualifier("orderGenericQueue") Queue orderGenericQueue,DirectExchange orderExchange){
        return BindingBuilder.bind(orderGenericQueue)
                .to(orderExchange)
                .with("order.generic");
    }

    @Bean
    public Queue deadLetterQueue(){
        return QueueBuilder.durable("dead.letter.queue").build();
    }
    @Bean
    public DirectExchange deadLetterExchange(){
        return new DirectExchange("dlx.exchange");
    }

    @Bean
    public Binding deadLetterBinding(Queue deadLetterQueue,DirectExchange deadLetterExchange){
        return BindingBuilder.bind(deadLetterQueue)
                .to(deadLetterExchange)
                .with("dead");
    }
}

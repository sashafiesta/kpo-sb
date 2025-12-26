package com.sashafiesta.shopaholic.order.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Bean
    public DirectExchange shopExchange() {
        return new DirectExchange("shop.exchange");
    }

    @Bean
    public Queue orderResultQueue() {
        return new Queue("order.result.queue", true);
    }

    @Bean
    public Binding binding(Queue orderResultQueue, DirectExchange shopExchange) {
        return BindingBuilder.bind(orderResultQueue).to(shopExchange).with("payment.result");
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
    
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter());
        return template;
    }
}

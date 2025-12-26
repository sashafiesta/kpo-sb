package com.sashafiesta.shopaholic.order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class OrderResultConsumer {

    private final OrderService orderService;

    @RabbitListener(queues = "order.result.queue")
    public void receivePaymentResult(Map<String, Object> payload) {
        System.out.println("Received payment result: " + payload);
        
        try {
            Long orderId = ((Number) payload.get("orderId")).longValue();
            String status = (String) payload.get("status");

            orderService.updateOrderStatus(orderId, status);
        } catch (Exception e) {
            System.err.println("Error processing payment result: " + e.getMessage());
        }
    }
}
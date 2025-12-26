package com.sashafiesta.shopaholic.payment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class PaymentConsumer {

    private final PaymentProcessingService paymentProcessingService;

    @RabbitListener(queues = "payment.queue")
    public void receivePaymentRequest(Map<String, Object> payload) {
        System.out.println("Received payment request: " + payload);
        paymentProcessingService.processPaymentMessage(payload);
    }
}

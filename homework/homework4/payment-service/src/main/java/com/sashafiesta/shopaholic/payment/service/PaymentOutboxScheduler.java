package com.sashafiesta.shopaholic.payment.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sashafiesta.shopaholic.payment.entity.OutboxMessage;
import com.sashafiesta.shopaholic.payment.repository.OutboxRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PaymentOutboxScheduler {

    private final OutboxRepository outboxRepository;
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    @Scheduled(fixedDelay = 5000)
    @Transactional
    public void sendOutboxMessages() {
        List<OutboxMessage> messages = outboxRepository.findAll();
        
        for (OutboxMessage message : messages) {
            try {
                Object payload = objectMapper.readValue(message.getPayload(), Object.class);
                rabbitTemplate.convertAndSend(message.getExchange(), message.getRoutingKey(), payload);
                outboxRepository.delete(message);
                System.out.println("Payment result sent for ID: " + message.getId());
            } catch (Exception e) {
                System.err.println("Error sending payment result: " + e.getMessage());
            }
        }
    }
}

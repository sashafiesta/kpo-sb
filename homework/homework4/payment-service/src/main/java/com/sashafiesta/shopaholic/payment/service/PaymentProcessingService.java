package com.sashafiesta.shopaholic.payment.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sashafiesta.shopaholic.payment.entity.InboxMessage;
import com.sashafiesta.shopaholic.payment.entity.OutboxMessage;
import com.sashafiesta.shopaholic.payment.repository.InboxRepository;
import com.sashafiesta.shopaholic.payment.repository.OutboxRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PaymentProcessingService {

    private final InboxRepository inboxRepository;
    private final OutboxRepository outboxRepository;
    private final AccountService accountService;
    private final ObjectMapper objectMapper;

    @Transactional
    @SneakyThrows
    public void processPaymentMessage(Map<String, Object> payload) {
        Long orderId = ((Number) payload.get("orderId")).longValue();
        Long userId = ((Number) payload.get("userId")).longValue();
        BigDecimal amount = new BigDecimal(payload.get("amount").toString());

        String messageId = "order-" + orderId;
        
        if (inboxRepository.existsById(messageId)) {
            System.out.println("Message already processed: " + messageId);
            return; 
        }

        InboxMessage inbox = InboxMessage.builder()
                .messageId(messageId).payload(objectMapper.writeValueAsString(payload))
                .receivedAt(LocalDateTime.now()).processed(true)
                .build();
        inboxRepository.save(inbox);

        String status;
        try {
            boolean success = accountService.debit(userId, amount);
            
            if (success) {
                status = "FINISHED";
            } else {
                System.out.println("Insufficient funds for user " + userId);
                status = "CANCELLED";
            }
        } catch (Exception e) {
            System.err.println("Payment failed (System Error): " + e.getMessage());
            status = "CANCELLED";
        }

        Map<String, Object> responsePayload = Map.of(
            "orderId", orderId,
            "status", status
        );

        OutboxMessage outbox = OutboxMessage.builder()
                .exchange("shop.exchange")
                .routingKey("payment.result")
                .payload(objectMapper.writeValueAsString(responsePayload))
                .createdAt(LocalDateTime.now())
                .build();
        
        outboxRepository.save(outbox);
    }
}

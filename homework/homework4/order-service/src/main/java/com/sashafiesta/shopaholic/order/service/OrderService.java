package com.sashafiesta.shopaholic.order.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sashafiesta.shopaholic.order.dto.OrderRequest;
import com.sashafiesta.shopaholic.order.entity.Order;
import com.sashafiesta.shopaholic.order.entity.OrderStatus;
import com.sashafiesta.shopaholic.order.entity.OutboxMessage;
import com.sashafiesta.shopaholic.order.repository.OrderRepository;
import com.sashafiesta.shopaholic.order.repository.OutboxRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OutboxRepository outboxRepository;
    private final ObjectMapper objectMapper;

    @Transactional
    @SneakyThrows
    public Order createOrder(OrderRequest request) {
        Order order = Order.builder()
                .userId(request.getUserId())
                .amount(request.getAmount())
                .description(request.getDescription())
                .status(OrderStatus.NEW)
                .build();
        
        order = orderRepository.save(order);

        Map<String, Object> eventPayload = Map.of(
            "orderId", order.getId(),
            "userId", order.getUserId(),
            "amount", order.getAmount()
        );

        String jsonPayload = objectMapper.writeValueAsString(eventPayload);

        OutboxMessage outbox = OutboxMessage.builder()
                .exchange("shop.exchange")
                .routingKey("payment.process")
                .payload(jsonPayload)
                .createdAt(LocalDateTime.now())
                .build();
        
        outboxRepository.save(outbox);

        return order;
    }

    @Transactional(readOnly = true)
    public Optional<Order> findOrderById(Long id) {
        return orderRepository.findById(id);
    }

    @Transactional
    public void updateOrderStatus(Long orderId, String statusStr) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found: " + orderId));
        
        if (order.getStatus() == OrderStatus.FINISHED || order.getStatus() == OrderStatus.CANCELLED) {
            return;
        }

        try {
            OrderStatus newStatus = OrderStatus.valueOf(statusStr);
            order.setStatus(newStatus);
            orderRepository.save(order);
            System.out.println("Order " + orderId + " updated to " + newStatus);
        } catch (IllegalArgumentException e) {
            System.err.println("Unknown status received: " + statusStr);
        }
    }
}
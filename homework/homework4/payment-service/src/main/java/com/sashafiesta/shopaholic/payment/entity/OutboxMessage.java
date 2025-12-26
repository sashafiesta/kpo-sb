package com.sashafiesta.shopaholic.payment.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "outbox_messages")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OutboxMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String exchange;
    private String routingKey;

    @Column(columnDefinition = "TEXT")
    private String payload;

    private LocalDateTime createdAt;
}

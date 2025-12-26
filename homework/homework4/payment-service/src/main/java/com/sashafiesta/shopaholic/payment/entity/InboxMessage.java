package com.sashafiesta.shopaholic.payment.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "inbox_messages")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InboxMessage {

    @Id
    @Column(name = "message_id")
    private String messageId;

    @Column(columnDefinition = "TEXT")
    private String payload;

    private boolean processed;

    private LocalDateTime receivedAt;
}

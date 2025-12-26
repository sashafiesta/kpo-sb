package com.sashafiesta.shopaholic.payment.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Entity
@Table(name = "accounts")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    @Id
    @Column(name = "user_id")
    private Long userId;

    @Column(nullable = false)
    private BigDecimal balance;

    @Version
    private Long version;
}

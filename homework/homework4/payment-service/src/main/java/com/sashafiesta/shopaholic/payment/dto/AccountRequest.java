package com.sashafiesta.shopaholic.payment.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class AccountRequest {
    private Long userId;
    private BigDecimal amount;
}

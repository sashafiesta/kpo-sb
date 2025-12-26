package com.sashafiesta.shopaholic.order.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class OrderRequest {
    private Long userId;
    private BigDecimal amount;
    private String description;
}

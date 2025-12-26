package com.sashafiesta.shopaholic.payment.repository;

import com.sashafiesta.shopaholic.payment.entity.OutboxMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OutboxRepository extends JpaRepository<OutboxMessage, Long> {
}

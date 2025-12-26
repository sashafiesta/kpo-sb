package com.sashafiesta.shopaholic.order.repository;

import com.sashafiesta.shopaholic.order.entity.OutboxMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OutboxRepository extends JpaRepository<OutboxMessage, Long> {
}

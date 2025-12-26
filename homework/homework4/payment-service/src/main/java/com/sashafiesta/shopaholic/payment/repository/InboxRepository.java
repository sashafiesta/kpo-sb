package com.sashafiesta.shopaholic.payment.repository;

import com.sashafiesta.shopaholic.payment.entity.InboxMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface InboxRepository extends JpaRepository<InboxMessage, String> {
    Optional<InboxMessage> findByMessageId(String messageId);
}

package com.sashafiesta.shopaholic.order.repository;

import com.sashafiesta.shopaholic.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}

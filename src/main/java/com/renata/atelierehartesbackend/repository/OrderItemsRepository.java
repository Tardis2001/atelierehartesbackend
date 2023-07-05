package com.renata.atelierehartesbackend.repository;

import com.renata.atelierehartesbackend.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemsRepository extends JpaRepository<OrderItem,Integer> {
}
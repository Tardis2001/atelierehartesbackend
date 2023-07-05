package com.renata.atelierehartesbackend.repository;

import com.renata.atelierehartesbackend.model.Order;
import com.renata.atelierehartesbackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findAllByUserOrderByCreatedDateDesc(User user);

}

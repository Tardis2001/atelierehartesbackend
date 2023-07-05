package com.renata.atelierehartesbackend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.renata.atelierehartesbackend.model.Cart;
import com.renata.atelierehartesbackend.model.User;

@Repository
public interface CartRepo extends JpaRepository<Cart,Integer>{
    
    List<Cart> findAllByUserOrderByCreatedDateDesc(User user);

    List<Cart> deleteByUser(User user);
}

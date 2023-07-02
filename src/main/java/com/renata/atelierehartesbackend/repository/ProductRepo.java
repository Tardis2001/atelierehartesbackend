package com.renata.atelierehartesbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.renata.atelierehartesbackend.model.Product;

@Repository
public interface ProductRepo extends JpaRepository<Product,Integer>{
    
}

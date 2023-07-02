package com.renata.atelierehartesbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.renata.atelierehartesbackend.model.Category;

@Repository
public interface CategoryRepo extends JpaRepository<Category,Integer>{

    Category findByCategoryName(String categoryName);
    
}

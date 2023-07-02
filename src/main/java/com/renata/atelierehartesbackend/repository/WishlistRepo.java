package com.renata.atelierehartesbackend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.renata.atelierehartesbackend.model.Wishlist;

@Repository
public interface WishlistRepo extends JpaRepository <Wishlist,Integer>{
    List<Wishlist> findAllByUserIdOrderByCreatedDateDesc(Integer userid);
}

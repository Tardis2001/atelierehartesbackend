package com.renata.atelierehartesbackend.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.renata.atelierehartesbackend.model.Wishlist;
import com.renata.atelierehartesbackend.repository.WishlistRepo;

@Service
@Transactional
public class WishlistService {  
    
    @Autowired
    private WishlistRepo wishlistRepo;

    public void createWishlist(Wishlist wishlist) {
        wishlistRepo.save(wishlist);
    }

    public List<Wishlist> readWishList(Integer userId) {
        return wishlistRepo.findAllByUserIdOrderByCreatedDateDesc(userId);
    }

    
}

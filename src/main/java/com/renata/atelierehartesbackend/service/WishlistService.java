package com.renata.atelierehartesbackend.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import com.renata.atelierehartesbackend.exceptions.CustomException;
import com.renata.atelierehartesbackend.model.User;
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


    public boolean deleteWishlist(Integer wishlistid, User user) {
        Optional<Wishlist> optionalWishlist = wishlistRepo.findById(wishlistid);

        if(optionalWishlist.isEmpty()){
            throw new CustomException("Wishlist item id is invalid " + wishlistid);
        }

        if(optionalWishlist.get() .getUser() != user){
            throw new CustomException("Wishlist item doesn't belong to the user " + wishlistid);
        }

        wishlistRepo.deleteById(wishlistid);
        return true;
    }
}

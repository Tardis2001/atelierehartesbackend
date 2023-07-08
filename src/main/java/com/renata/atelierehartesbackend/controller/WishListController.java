package com.renata.atelierehartesbackend.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.renata.atelierehartesbackend.common.ApiResponse;
import com.renata.atelierehartesbackend.dto.product.ProductDto;
import com.renata.atelierehartesbackend.enums.Role;
import com.renata.atelierehartesbackend.model.Product;
import com.renata.atelierehartesbackend.model.User;
import com.renata.atelierehartesbackend.model.Wishlist;
import com.renata.atelierehartesbackend.service.ProductService;
import com.renata.atelierehartesbackend.service.TokenService;
import com.renata.atelierehartesbackend.service.WishlistService;

@RestController
@RequestMapping("/wishlist")
public class WishListController {
    @Autowired
    WishlistService wishlistService;

    @Autowired
    TokenService tokenService;
    @PostMapping("/create")
    public ResponseEntity<ApiResponse> addToWishlist(@RequestBody Product product, @RequestParam("token") String token){
        tokenService.authenticate(token);
        User user = tokenService.getUser(token);
        Wishlist wishlist = new Wishlist(user,product);
        wishlistService.createWishlist(wishlist);
        return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Added to wishlist"),HttpStatus.CREATED);
        
    }
    @GetMapping("/")
    public ResponseEntity<List<ProductDto>> getWishList(@PathVariable("token") String token)
    {
        int user_id = tokenService.getUser(token).getId();
        List<Wishlist> body = wishlistService.readWishList(user_id);
        List<ProductDto> products = new ArrayList<ProductDto>();
        for (Wishlist wishList : body) {
                products.add(ProductService.getDtoFromProduct(wishList.getProduct()));
        }


        return new ResponseEntity<>(products,HttpStatus.OK);
 
    }
    @DeleteMapping("/delete/{wishlistId}")
    public ResponseEntity<ApiResponse> removeWishList(@PathVariable("wishlistid") Integer wishlistid,@RequestParam("token") String token){

        tokenService.authenticate(token);
        User user = tokenService.getUser(token);
        if(user.getRole() == Role.admin || user.getRole() == Role.manager) {
            if(wishlistService.deleteWishlist(wishlistid,user)){
                return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Product deleted"), HttpStatus.OK);
            }
            return new ResponseEntity<ApiResponse>(new ApiResponse(false, "Product id not found"), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<ApiResponse>(new ApiResponse(false,"You don't have permission"),HttpStatus.UNAUTHORIZED);
    }

}

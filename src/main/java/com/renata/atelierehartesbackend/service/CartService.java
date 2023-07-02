package com.renata.atelierehartesbackend.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.renata.atelierehartesbackend.dto.cart.AddCartDto;
import com.renata.atelierehartesbackend.dto.cart.CartDto;
import com.renata.atelierehartesbackend.dto.cart.CartItemDto;
import com.renata.atelierehartesbackend.exceptions.CustomException;
import com.renata.atelierehartesbackend.model.Cart;
import com.renata.atelierehartesbackend.model.Product;
import com.renata.atelierehartesbackend.model.User;
import com.renata.atelierehartesbackend.repository.CartRepo;

@Service
@Transactional
public class CartService {

    

    @Autowired
    private CartRepo cartRepo;

    public void addtoCart(AddCartDto addCartDto, Product product, User user) {
        Cart cart = new Cart(product, addCartDto.getQuantity(), user);
        cartRepo.save(cart);
    }

    public CartDto listCartItems(User user) {
        
        List<Cart> cartList = cartRepo.findAllByUserOrderByCreatedDateDesc(user);

        List<CartItemDto> cartItems = new ArrayList<>();
        double totalCost = 0;
        for (Cart cart:cartList){
            CartItemDto cartItemDto = new CartItemDto(cart);
            cartItems.add(cartItemDto);
        }
        for (CartItemDto cartItemDto :cartItems){
            totalCost += (cartItemDto.getProduct().getPrice()* cartItemDto.getQuantity());
        }

        return new CartDto(cartItems,totalCost);

    }

    public void deleteCartItem(Integer cartItemId, User user) {
        Optional<Cart> optionalCart = cartRepo.findById(cartItemId);

        if(optionalCart.isEmpty()){
            throw new CustomException("Cart item id is invalid " + cartItemId);
        }

        Cart cart = optionalCart.get();

        if(cart.getUser() != user){
            throw new CustomException("Cart item doesn't belong to the user " + cartItemId);
        }

        cartRepo.deleteById(cartItemId);
    }
    
}

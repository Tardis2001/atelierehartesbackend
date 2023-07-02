package com.renata.atelierehartesbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.renata.atelierehartesbackend.common.ApiResponse;
import com.renata.atelierehartesbackend.dto.cart.AddCartDto;
import com.renata.atelierehartesbackend.dto.cart.CartDto;
import com.renata.atelierehartesbackend.model.Product;
import com.renata.atelierehartesbackend.model.User;
import com.renata.atelierehartesbackend.service.CartService;
import com.renata.atelierehartesbackend.service.ProductService;
import com.renata.atelierehartesbackend.service.TokenService;

@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    @Autowired
    private TokenService tokenService;

    @Autowired 
    private ProductService productService;
    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addtoCart(@RequestBody AddCartDto addCartDto, @RequestParam("token") String token){
        
        tokenService.authenticate(token);
        User user = tokenService.getUser(token);
        Product product = productService.getProductById(addCartDto.getProductId());
        cartService.addtoCart(addCartDto,product,user);
        return new ResponseEntity<ApiResponse>(new ApiResponse(true,"added to the cart"),HttpStatus.CREATED);
    }
    
    @GetMapping("/")
    public ResponseEntity<CartDto> getCart(@RequestParam("token") String token){
        
        tokenService.authenticate(token);
        User user = tokenService.getUser(token); 
        CartDto cartDto = cartService.listCartItems(user);
        return new ResponseEntity<CartDto>(cartDto,HttpStatus.OK );
    }

    @DeleteMapping("/delete/{CartItemId}")
    public ResponseEntity<ApiResponse> removeItem(@PathVariable("CartItemId") Integer CartItemId,@RequestParam("token") String token){

        tokenService.authenticate(token);
        User user = tokenService.getUser(token);
        cartService.deleteCartItem(CartItemId,user);
        return new ResponseEntity<ApiResponse>(new ApiResponse(true,"Removed from the cart" ),HttpStatus.OK);
    }
}

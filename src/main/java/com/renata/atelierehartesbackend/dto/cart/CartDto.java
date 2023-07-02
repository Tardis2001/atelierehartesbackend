package com.renata.atelierehartesbackend.dto.cart;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartDto {
    
    List<CartItemDto> cartItem;
    private double totalCost;
    
}

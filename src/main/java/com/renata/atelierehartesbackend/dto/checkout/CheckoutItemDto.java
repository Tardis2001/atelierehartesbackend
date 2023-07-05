package com.renata.atelierehartesbackend.dto.checkout;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CheckoutItemDto {
    private String productName;
    private int quantity;
    private double price;
    private Integer productId;
    private int userId;
}

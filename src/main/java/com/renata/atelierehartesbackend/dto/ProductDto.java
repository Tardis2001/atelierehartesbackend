package com.renata.atelierehartesbackend.dto;

import javax.validation.constraints.NotNull;

import com.renata.atelierehartesbackend.model.Product;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
public class ProductDto {
    public ProductDto(Product product) {
    }
    private Integer id;
    private @NotNull String name;
    private @NotNull String imageURL;
    private @NotNull double price;
    private @NotNull String description;
    private @NotNull Integer categoryId;
}

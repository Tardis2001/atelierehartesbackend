package com.renata.atelierehartesbackend.controller;

import java.util.List;
import java.util.Optional;

import com.renata.atelierehartesbackend.config.MessageStrings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.renata.atelierehartesbackend.common.ApiResponse;
import com.renata.atelierehartesbackend.dto.product.ProductDto;
import com.renata.atelierehartesbackend.enums.Role;
import com.renata.atelierehartesbackend.model.Product;
import com.renata.atelierehartesbackend.model.User;
import com.renata.atelierehartesbackend.service.ProductService;
import com.renata.atelierehartesbackend.service.TokenService;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductService productService;
    @Autowired
    TokenService tokenService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createProduct(@RequestBody ProductDto productDto,@RequestParam("token") String token) {
        tokenService.authenticate(token);
        User user = tokenService.getUser(token);
        if(user.getRole() == Role.admin || user.getRole() == Role.manager ){
            productService.createProduct(productDto);
		    return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Product has been added"), HttpStatus.CREATED);
        }
        return new ResponseEntity<ApiResponse>(new ApiResponse(false, MessageStrings.USER_NOT_PERMITTED),HttpStatus.UNAUTHORIZED);
    }
    @GetMapping("/")
    public ResponseEntity<List<ProductDto>> getProduct(){
        List<ProductDto> body = productService.findAll();
        return new ResponseEntity<List<ProductDto>>(body,HttpStatus.OK);
    }

    @GetMapping("/findbyId/{productid}")
    public Product findbyId(@PathVariable String productid){
        return productService.findbyId(Integer.valueOf(productid));
    }
    @PostMapping("/update/")
    public ResponseEntity<ApiResponse> updateProduct(@RequestBody ProductDto productDto,@RequestParam("token") String token){
        tokenService.authenticate(token);
        User user = tokenService.getUser(token);
        if(user.getRole() == Role.admin || user.getRole() == Role.manager ){
            productService.updateProductItem(productDto);
            return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Product has been updated"), HttpStatus.OK);
        }
        return new ResponseEntity<ApiResponse>(new ApiResponse(false, MessageStrings.USER_NOT_PERMITTED), HttpStatus.UNAUTHORIZED);
        
    }

    @DeleteMapping("/delete/{productid}")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable String productid,@RequestParam("token") String token){

        tokenService.authenticate(token);
        User user = tokenService.getUser(token);
        if(user.getRole() == Role.admin || user.getRole() == Role.manager) {
            if(productService.deleteProductItem(Integer.valueOf(productid))){
                return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Product deleted"), HttpStatus.OK);
            }
            return new ResponseEntity<ApiResponse>(new ApiResponse(false, "Product id not found"), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<ApiResponse>(new ApiResponse(false, MessageStrings.USER_NOT_PERMITTED),HttpStatus.UNAUTHORIZED);
    }

}

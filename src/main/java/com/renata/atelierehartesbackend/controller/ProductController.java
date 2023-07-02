package com.renata.atelierehartesbackend.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.renata.atelierehartesbackend.common.ApiResponse;
import com.renata.atelierehartesbackend.dto.product.ProductDto;
import com.renata.atelierehartesbackend.enums.Role;
import com.renata.atelierehartesbackend.model.Category;
import com.renata.atelierehartesbackend.model.Product;
import com.renata.atelierehartesbackend.model.User;
import com.renata.atelierehartesbackend.service.CategoryService;
import com.renata.atelierehartesbackend.service.ProductService;
import com.renata.atelierehartesbackend.service.TokenService;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductService productService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    TokenService tokenService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createProduct(@RequestBody ProductDto productDto,@RequestParam("token") String token) {
        Optional<Category> optionalCategory = categoryService.findById(productDto.getCategoryId());
        if(!optionalCategory.isPresent()){
            return new ResponseEntity<ApiResponse>(new ApiResponse(false, "Category doesn't exists"), HttpStatus.BAD_REQUEST);
        }
        tokenService.authenticate(token);
        User user = tokenService.getUser(token);
        if(user.getRole() == Role.admin || user.getRole() == Role.manager ){
            productService.createProduct(productDto, optionalCategory);
		    return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Product has been added"), HttpStatus.CREATED);
        }
        return new ResponseEntity<ApiResponse>(new ApiResponse(false,"You don't have permission"),HttpStatus.UNAUTHORIZED);
    }
    @GetMapping("/")
    public ResponseEntity<List<ProductDto>> getProduct(){
        List<ProductDto> body = productService.findAll();
        return new ResponseEntity<List<ProductDto>>(body,HttpStatus.OK);
    }
    @GetMapping("/findbyId/{productid}")
    public Product findbyId(@PathVariable Integer id){
        return productService.findbyId(id);
    }
    @PostMapping("/update/{productid}")
    public ResponseEntity<ApiResponse> updateProduct(@PathVariable Integer productid,ProductDto productDto,@RequestParam("token") String token){
        if(productService.IsEmpty(productid)){
            return new ResponseEntity<ApiResponse>(new ApiResponse(false, "Product doesn't exists"), HttpStatus.BAD_REQUEST);
        }
        tokenService.authenticate(token);
        User user = tokenService.getUser(token);
        if(user.getRole() == Role.admin || user.getRole() == Role.manager ){
            return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Product has been updated"), HttpStatus.OK);
        }
        return new ResponseEntity<ApiResponse>(new ApiResponse(false,"You don't have permission"), HttpStatus.UNAUTHORIZED);
        
    }
}

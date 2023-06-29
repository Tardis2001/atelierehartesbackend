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
import org.springframework.web.bind.annotation.RestController;

import com.renata.atelierehartesbackend.common.ApiResponse;
import com.renata.atelierehartesbackend.dto.ProductDto;
import com.renata.atelierehartesbackend.model.Category;
import com.renata.atelierehartesbackend.model.Product;
import com.renata.atelierehartesbackend.service.CategoryService;
import com.renata.atelierehartesbackend.service.ProductService;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductService productService;
    @Autowired
    CategoryService categoryService;


    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createProduct(@RequestBody ProductDto productDto) {
        Optional<Category> optionalCategory = categoryService.findById(productDto.getCategoryId());
        if(!optionalCategory.isPresent()){
            return new ResponseEntity<ApiResponse>(new ApiResponse(false, "Category doesn't exists"), HttpStatus.BAD_REQUEST);
        }
        productService.createProduct(productDto, optionalCategory);
		return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Product has been added"), HttpStatus.CREATED);
	}
    @GetMapping("/")
    public ResponseEntity<List<ProductDto>> getProduct(){
        List<ProductDto> body = productService.findAll();
        return new ResponseEntity<List<ProductDto>>(body,HttpStatus.OK);
    }
    @GetMapping("/findbyId/{productid}")
    public ResponseEntity<Optional<Product>> findbyId(@PathVariable Integer id){
        Optional<Product> body = productService.findbyId(id);
        return new ResponseEntity<Optional<Product>>(body, HttpStatus.OK);
    }
    @PostMapping("/update/{productid}")
    public ResponseEntity<ApiResponse> updateProduct(@PathVariable Integer productid,ProductDto productDto){
        if(productService.IsEmpty(productid)){
            return new ResponseEntity<ApiResponse>(new ApiResponse(false, "Product doesn't exists"), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Product has been updated"), HttpStatus.OK);
    }
}

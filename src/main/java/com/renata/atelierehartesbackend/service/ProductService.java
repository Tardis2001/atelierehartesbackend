package com.renata.atelierehartesbackend.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.renata.atelierehartesbackend.common.ApiResponse;
import com.renata.atelierehartesbackend.exceptions.CustomException;
import com.renata.atelierehartesbackend.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.renata.atelierehartesbackend.dto.product.ProductDto;
import com.renata.atelierehartesbackend.exceptions.ProductNotExistException;
import com.renata.atelierehartesbackend.model.Product;
import com.renata.atelierehartesbackend.repository.ProductRepo;

/**
 * ProductService
 */
@Service
public class ProductService {
    
    @Autowired
    ProductRepo productRepo;

    public void createProduct(ProductDto productDto){
        productRepo.save(new Product(productDto));
    }

    public List<ProductDto> findAll(){       
        List<Product> products = productRepo.findAll();
        List<ProductDto> productDtos = new ArrayList<>();
        for(Product product : products) {
            ProductDto productDto = getDtoFromProduct(product);
            productDtos.add(productDto);
        }
        return productDtos;
    }
    public Product findbyId(Integer id){
        Optional<Product> optionalProduct = productRepo.findById(id);
        if(optionalProduct.isEmpty()){
            throw new ProductNotExistException("product id is invalid " + id );
        }
        return optionalProduct.get();
    }
    public void updateProduct(Integer id,ProductDto productDto) throws Exception{
        Optional<Product> optionalProduct = productRepo.findById(id);
        
        if(!optionalProduct.isPresent()){
            throw new Exception("product not present");
        }

        Product product = optionalProduct.get();
        product.setId(id);
        product.setName(productDto.getName());
        product.setImageURL(productDto.getImageURL());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        productRepo.save(product);
    }
    
    public static ProductDto getDtoFromProduct(Product product) {
        return new ProductDto(product);
    }

    public boolean IsEmpty(Integer id){
        return productRepo.findById(id).isEmpty();
    }
    public Product getProductById(Integer productId){
        Optional<Product> optionalProduct = productRepo.findById(productId);
        return optionalProduct.orElse(null);
            // throw new ProductNotExistException("Product id is invalid " + productId);
    }
    public void updateProductItem(ProductDto productDto){
        Product product = new Product(productDto);
        productRepo.save(product);
    }
    public boolean deleteProductItem(Integer productId) {
        Optional<Product> optionalProduct = productRepo.findById(productId);

        if(optionalProduct.isEmpty()){
            throw new CustomException("Product id is invalid " + productId);
        }
        productRepo.deleteById(productId);
        return true;
    }
}
package com.renata.atelierehartesbackend.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.protobuf.Option;
import com.renata.atelierehartesbackend.dto.product.ProductDto;
import com.renata.atelierehartesbackend.exceptions.ProductNotExistException;
import com.renata.atelierehartesbackend.model.Category;
import com.renata.atelierehartesbackend.model.Product;
import com.renata.atelierehartesbackend.repository.ProductRepo;

/**
 * ProductService
 */
@Service
public class ProductService {
    
    @Autowired
    ProductRepo productRepo;

    public void createProduct(ProductDto productDto,Optional<Category> optinalCategory){
        Product product = new Product();
        product.setName(productDto.getName());
        product.setImageURL(productDto.getImageURL());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setCategory(optinalCategory.get());
        productRepo.save(product);
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
    public void updateProduct(Integer id,ProductDto productDto,Optional<Category> optinalCategory) throws Exception{
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
        product.setCategory(optinalCategory.get());
        productRepo.save(product);
    }
    
    public static ProductDto getDtoFromProduct(Product product) {
        ProductDto productDto = new ProductDto(product);
        return productDto;
    }

    public boolean IsEmpty(Integer id){
        return productRepo.findById(id).isEmpty();
    }
    public Product getProductById(Integer productId){
        Optional<Product> optionalProduct = productRepo.findById(productId);
        if (!optionalProduct.isPresent())
            return null;
            // throw new ProductNotExistException("Product id is invalid " + productId);
        return optionalProduct.get();
    }
}
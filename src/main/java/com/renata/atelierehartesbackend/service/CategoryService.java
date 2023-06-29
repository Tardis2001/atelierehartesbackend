package com.renata.atelierehartesbackend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.renata.atelierehartesbackend.model.Category;
import com.renata.atelierehartesbackend.repository.CategoryRepo;
@Service
public class CategoryService {
    @Autowired
    CategoryRepo categoryRepo;
    public void CreateCategory(Category category){
        categoryRepo.save(category);
        
    }
    public Optional<Category> findById(Integer id){
        return categoryRepo.findById(id);
    }
    public List<Category> findAll(){
        return categoryRepo.findAll();
    }
    public void updateCategory(Integer id,Category updatecategory){
        Category category = categoryRepo.findById(id).get();
        category.setCategoryDescription(updatecategory.getCategoryDescription());
        category.setCategoryName(updatecategory.getCategoryName());
        category.setImageurl(updatecategory.getImageurl());

        categoryRepo.save(category); 
    } 
    public Category readCategory(String categoryName) {
		return categoryRepo.findByCategoryName(categoryName);
	}
    public void deleteCategory(Integer id){
        categoryRepo.deleteById(id);
    }
    public boolean IsEmpty(Integer id){
        return categoryRepo.findById(id).isEmpty();
    }
}

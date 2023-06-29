package com.renata.atelierehartesbackend.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.aspectj.bridge.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.renata.atelierehartesbackend.common.ApiResponse;
import com.renata.atelierehartesbackend.model.Category;
import com.renata.atelierehartesbackend.service.CategoryService;
import com.renata.atelierehartesbackend.utils.Helper;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @PostMapping("/create")
        public ResponseEntity<ApiResponse> createCategory(@Valid @RequestBody Category category) {
		if (Helper.notNull(categoryService.readCategory(category.getCategoryName()))) {
			return new ResponseEntity<ApiResponse>(new ApiResponse(false, "category already exists"), HttpStatus.CONFLICT);
		}
		categoryService.CreateCategory(category);
		return new ResponseEntity<ApiResponse>(new ApiResponse(true, "created the category"), HttpStatus.CREATED);
	}
    @GetMapping("/")
    public ResponseEntity<List<Category>> getCategories() {
        List<Category> body = categoryService.findAll();
        return new ResponseEntity<List<Category>>(body, HttpStatus.OK);
    }

    @GetMapping("/findbyId/{categoryid}")
    public Optional<Category> findById(@PathVariable Integer categoryid){
        return categoryService.findById(categoryid);
    } 

    @PostMapping("update/{categoryid}")
    public ResponseEntity<ApiResponse> updateCategory(@PathVariable Integer categoryid,@RequestBody Category category){      
        if(categoryService.IsEmpty(categoryid)){
            return new ResponseEntity<ApiResponse>(new ApiResponse(false,"category doesn't exists"),HttpStatus.BAD_REQUEST);
        }
        categoryService.updateCategory(categoryid, category);
        return new ResponseEntity<ApiResponse>(new ApiResponse(true,"updated the category"),HttpStatus.OK);
    }

    @DeleteMapping("delete/{categoryid}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer categoryid){
         if(categoryService.IsEmpty(categoryid)){
            return new ResponseEntity<ApiResponse>(new ApiResponse(false,"category doesn't exists"),HttpStatus.BAD_REQUEST);
        }
        categoryService.deleteCategory(categoryid);
        return new ResponseEntity<ApiResponse>(new ApiResponse(true,"category deleted"),HttpStatus.OK);
    }

}

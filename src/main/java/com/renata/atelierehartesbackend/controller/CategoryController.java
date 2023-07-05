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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.renata.atelierehartesbackend.common.ApiResponse;
import com.renata.atelierehartesbackend.enums.Role;
import com.renata.atelierehartesbackend.model.Category;
import com.renata.atelierehartesbackend.model.User;
import com.renata.atelierehartesbackend.service.CategoryService;
import com.renata.atelierehartesbackend.service.TokenService;
import com.renata.atelierehartesbackend.utils.Helper;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @Autowired
    TokenService tokenService;
    @PostMapping("/create")
        public ResponseEntity<ApiResponse> createCategory(@Valid @RequestBody Category category,@RequestParam("token") String token) {
		if (Helper.notNull(categoryService.readCategory(category.getCategoryName()))) {
			return new ResponseEntity<ApiResponse>(new ApiResponse(false, "category already exists"), HttpStatus.CONFLICT);
		}
        tokenService.authenticate(token);
        User user = tokenService.getUser(token);
        if(user.getRole() == Role.admin || user.getRole() == Role.manager ){
		    categoryService.CreateCategory(category);
		    return new ResponseEntity<ApiResponse>(new ApiResponse(true, "created the category"), HttpStatus.CREATED);
        }
        return new ResponseEntity<ApiResponse>(new ApiResponse(false, "You don't have permission"), HttpStatus.UNAUTHORIZED);
    }
    @GetMapping("/")
    public ResponseEntity<List<Category>> getCategories() {
        List<Category> body = categoryService.findAll();
        return new ResponseEntity<List<Category>>(body, HttpStatus.OK);
    }

    @GetMapping("/findbyId/{categoryid}")
    public ResponseEntity<Category> findById(@PathVariable Integer categoryid,@RequestParam("token") String token){
        Optional<Category> category = categoryService.findById(categoryid);
        return new ResponseEntity<Category>(category.get(),HttpStatus.OK);
    } 

    @PostMapping("update/{categoryid}")
    public ResponseEntity<ApiResponse> updateCategory(@PathVariable Integer categoryid,@RequestBody Category category,@RequestParam("token") String token){      
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

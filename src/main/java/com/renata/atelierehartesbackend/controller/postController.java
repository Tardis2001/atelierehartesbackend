package com.renata.atelierehartesbackend.controller;

import com.renata.atelierehartesbackend.common.ApiResponse;
import com.renata.atelierehartesbackend.config.MessageStrings;
import com.renata.atelierehartesbackend.dto.blog.PostDto;
import com.renata.atelierehartesbackend.enums.Role;
import com.renata.atelierehartesbackend.model.User;
import com.renata.atelierehartesbackend.service.PostService;
import com.renata.atelierehartesbackend.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/blog/posts")
public class postController {
    @Autowired
    PostService postService;
    @Autowired
    TokenService tokenService;
    @PostMapping("/addPost")
    public ResponseEntity<ApiResponse> addPost(@RequestBody PostDto post, @RequestParam("token") String token){
        tokenService.authenticate(token);
        User user = tokenService.getUser(token);
        if(user.getRole() == Role.admin || user.getRole() == Role.manager){
            postService.savePost(post,user);
            return new ResponseEntity<ApiResponse>(new ApiResponse(true, MessageStrings.POST_SAVED), HttpStatus.OK);
        }
        return new ResponseEntity<ApiResponse>(new ApiResponse(false, MessageStrings.USER_NOT_PERMITTED), HttpStatus.UNAUTHORIZED);
    }
    @GetMapping("/")
    public ResponseEntity<List<PostDto>> getPosts(){
        return new ResponseEntity<List<PostDto>>(postService.findAllPosts(),HttpStatus.OK);
    }
    @DeleteMapping("/deletepost/{postId}")
    public ResponseEntity<ApiResponse> removePost(@PathVariable String postId,@RequestParam("token") String token ){
        tokenService.authenticate(token);
        User user = tokenService.getUser(token);
        if(user.getRole() == Role.admin || user.getRole() == Role.manager) {
            if(postService.deletePost(Integer.valueOf(postId))){
                return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Product deleted"), HttpStatus.OK);
            }
            return new ResponseEntity<ApiResponse>(new ApiResponse(false, "Product id not found"), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<ApiResponse>(new ApiResponse(false, MessageStrings.USER_NOT_PERMITTED), HttpStatus.UNAUTHORIZED);

    }
    @PostMapping("/updatepost/{postId}")
    public ResponseEntity<ApiResponse> updatePost(@RequestBody PostDto post,@PathVariable String postId,@RequestParam("token") String token){
        tokenService.authenticate(token);
        User user = tokenService.getUser(token);
        if(user.getRole() == Role.admin || user.getRole() == Role.manager) {
            if(postService.updatePost(Integer.valueOf(postId))){
                return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Product deleted"), HttpStatus.OK);
            }
            return new ResponseEntity<ApiResponse>(new ApiResponse(false, "Product id not found"), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<ApiResponse>(new ApiResponse(false,MessageStrings.USER_NOT_PERMITTED),HttpStatus.UNAUTHORIZED);
    }
}

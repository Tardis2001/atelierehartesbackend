package com.renata.atelierehartesbackend.controller;

import com.renata.atelierehartesbackend.common.ApiResponse;
import com.renata.atelierehartesbackend.dto.blog.CommentDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class commentController {
    @PostMapping("/comment/addComment")
    public ResponseEntity<ApiResponse> addComment(@RequestBody CommentDto commentDto){
        return new ResponseEntity<>(new ApiResponse(false,"teste"), HttpStatus.OK);
    }
}

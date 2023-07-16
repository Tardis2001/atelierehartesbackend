package com.renata.atelierehartesbackend.controller;

import java.security.NoSuchAlgorithmException;

import com.renata.atelierehartesbackend.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.renata.atelierehartesbackend.dto.ResponseDto;
import com.renata.atelierehartesbackend.dto.user.SigninResponseDto;
import com.renata.atelierehartesbackend.dto.user.SigninDto;
import com.renata.atelierehartesbackend.dto.user.SignupDto;
import com.renata.atelierehartesbackend.service.UserService;
import com.renata.atelierehartesbackend.common.ApiResponse;

/**
 * UserController
 */
@RequestMapping("/user")
@RestController
 public class UserController {

    @Autowired
    UserService userService;
    @PostMapping("/signup")
    public ResponseDto signup(@RequestBody SignupDto signupDto){
        return userService.signup(signupDto);
    }
    @PostMapping("/signin")
    public SigninResponseDto signin(@RequestBody SigninDto signinDto){
        return userService.signin(signinDto);
    }
    @PostMapping("/checkadmin")
    public ResponseEntity<ApiResponse> checkadmin(@RequestParam("token") String token){
        return userService.checkadmin(token);
    }
    @GetMapping("/")
    public User getUser(@RequestParam("token") String token){

        return userService.getUser(token);
    }
}
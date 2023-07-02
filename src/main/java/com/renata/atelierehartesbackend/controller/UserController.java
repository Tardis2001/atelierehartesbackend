package com.renata.atelierehartesbackend.controller;

import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.renata.atelierehartesbackend.dto.ResponseDto;
import com.renata.atelierehartesbackend.dto.user.SigninResponseDto;
import com.renata.atelierehartesbackend.dto.user.SigninDto;
import com.renata.atelierehartesbackend.dto.user.SignupDto;
import com.renata.atelierehartesbackend.service.UserService;


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
    public SigninResponseDto signin(@RequestBody SigninDto signupDto){
        return userService.signin(signupDto);
    }
}
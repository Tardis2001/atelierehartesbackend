package com.renata.atelierehartesbackend.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

import javax.naming.AuthenticationException;
import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.renata.atelierehartesbackend.enums.ResponseStatus;
import com.renata.atelierehartesbackend.enums.Role;
import com.renata.atelierehartesbackend.dto.ResponseDto;
import com.renata.atelierehartesbackend.dto.user.SigninResponseDto;
import com.renata.atelierehartesbackend.dto.user.SigninDto;
import com.renata.atelierehartesbackend.dto.user.SignupDto;
import com.renata.atelierehartesbackend.exceptions.CustomException;
import com.renata.atelierehartesbackend.model.AutheticationToken;
import com.renata.atelierehartesbackend.model.User;
import com.renata.atelierehartesbackend.repository.UserRepo;
import com.renata.atelierehartesbackend.utils.Helper;
import com.renata.atelierehartesbackend.common.ApiResponse;
import com.renata.atelierehartesbackend.config.MessageStrings;
@Service
public class UserService {
    @Autowired
    UserRepo userRepository;
    
    @Autowired
    TokenService authenticationService;
    public ResponseDto signup(SignupDto signupDto){
        if(Objects.nonNull(userRepository.findByusername(signupDto.getUsername())) || Objects.nonNull(userRepository.findByemail(signupDto.getEmail()))){
            throw new CustomException("Username or email already present");
        }

        String encryptedpassword = signupDto.getPassword();
        try {
            encryptedpassword = hashPassword(signupDto.getPassword());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        User user = new User(signupDto.getFirstName(), signupDto.getLastName(), signupDto.getEmail(),signupDto.getUsername(), Role.user, encryptedpassword );

        User createdUser;
        try {
            // save the User
            createdUser = userRepository.save(user);
            // generate token for user
            final AutheticationToken authenticationToken = new AutheticationToken(createdUser);
            // save token in database
            authenticationService.saveConfirmationToken(authenticationToken);
            // success in creating
            return new ResponseDto(ResponseStatus.success.toString(), MessageStrings.USER_CREATED);
        } catch (Exception e) {
            // handle signup error
            throw new CustomException(e.getMessage());
        }
    }
    private String hashPassword(String password) throws NoSuchAlgorithmException{
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(password.getBytes());
        byte[] digest = md.digest();
        String hash = DatatypeConverter.printHexBinary(digest).toUpperCase();
        return hash;
    }
    public SigninResponseDto signin(SigninDto signupDto) {
        User user = userRepository.findByusername(signupDto.getUsername());
        if(!Helper.notNull(user)){
            try {
                throw new AuthenticationException("user is not valid");
            } catch (AuthenticationException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        System.out.println(user.getPassword());
        try {
            if(!user.getPassword().equals((hashPassword(signupDto.getPassword())))){
                throw new AuthenticationException("Wrong password");
            }
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (AuthenticationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        AutheticationToken token = authenticationService.getToken(user);

        if(!Helper.notNull(token)){
            throw new CustomException("token is not present");
        }

        return new SigninResponseDto ("success", token.getToken());
    }
    public ResponseEntity<ApiResponse> checkadmin(@RequestParam String token) {
        authenticationService.authenticate(token);
        User user = authenticationService.getUser(token); 
        
        if(user.getRole() == Role.admin){
                    return new ResponseEntity<ApiResponse>(new ApiResponse(true, "You have permission"), HttpStatus.OK);
        }
        return new ResponseEntity<ApiResponse>(new ApiResponse(false, "You don't have permission"), HttpStatus.UNAUTHORIZED);
    }

}

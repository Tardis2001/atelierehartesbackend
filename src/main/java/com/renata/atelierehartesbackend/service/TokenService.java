package com.renata.atelierehartesbackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.renata.atelierehartesbackend.config.MessageStrings;
import com.renata.atelierehartesbackend.exceptions.AuthenticationFailException;
import com.renata.atelierehartesbackend.model.AutheticationToken;
import com.renata.atelierehartesbackend.model.User;
import com.renata.atelierehartesbackend.repository.TokenRepo;
import com.renata.atelierehartesbackend.utils.Helper;

@Service
public class TokenService {
    @Autowired
    TokenRepo tokenRepo;
    
    public void saveConfirmationToken(AutheticationToken authenticationToken) {
        tokenRepo.save(authenticationToken);
    }
    public AutheticationToken getToken(User user) {
        return tokenRepo.findTokenByUser(user);
    }

    public User getUser(String token) {
        AutheticationToken authenticationToken = tokenRepo.findTokenByToken(token);
        if (Helper.notNull(authenticationToken)) {
            if (Helper.notNull(authenticationToken.getUser())) {
                return authenticationToken.getUser();
            }
        }
        return null;
    }

    public void authenticate(String token) throws AuthenticationFailException {
        if (!Helper.notNull(token)) {
            throw new AuthenticationFailException(MessageStrings.AUTH_TOKEN_NOT_PRESENT);
        }
        if (!Helper.notNull(getUser(token))) {
            throw new AuthenticationFailException(MessageStrings.AUTH_TOKEN_NOT_VALID);
        }
    }

}

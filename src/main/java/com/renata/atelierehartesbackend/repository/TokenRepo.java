package com.renata.atelierehartesbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.renata.atelierehartesbackend.model.AutheticationToken;
import com.renata.atelierehartesbackend.model.User;

@Repository
public interface TokenRepo extends JpaRepository<AutheticationToken,Integer>{

    AutheticationToken findTokenByToken(String token);
    AutheticationToken findTokenByUser(User user);
}

package com.renata.atelierehartesbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.renata.atelierehartesbackend.model.User;

@Repository
public interface UserRepo extends JpaRepository<User,Integer> {
    User findByusername(String username);
    User findByemail(String email);
}

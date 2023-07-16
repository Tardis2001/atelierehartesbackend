package com.renata.atelierehartesbackend.repository;

import com.renata.atelierehartesbackend.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepo extends JpaRepository<Post,Integer> {
}

package com.renata.atelierehartesbackend.repository;

import com.renata.atelierehartesbackend.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

@Repository
public interface CommentRepo extends JpaRepository<Comment,Integer> {
}

package com.renata.atelierehartesbackend.service;

import com.renata.atelierehartesbackend.dto.blog.PostDto;
import com.renata.atelierehartesbackend.exceptions.CustomException;
import com.renata.atelierehartesbackend.model.Post;
import com.renata.atelierehartesbackend.model.Product;
import com.renata.atelierehartesbackend.model.User;
import com.renata.atelierehartesbackend.repository.PostRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    PostRepo postRepo;
    
    public void savePost(PostDto post,User user) {
        postRepo.save(new Post(post.getTitle(),post.getBody(),user));
    }
    public List<PostDto> findAllPosts(){
        List<Post> posts = postRepo.findAll();
        List<PostDto> postsDto = new ArrayList<>();
        for(Post post : posts) {
            PostDto postDto =  new PostDto(post);
            postsDto.add(postDto);
        }
        return postsDto;
    }

    public boolean deletePost(Integer postId) {
        Optional<Post> optionalPost = postRepo.findById(postId);

        if(optionalPost.isEmpty()){
            throw new CustomException("Product id is invalid " + postId);
        }
        postRepo.deleteById(postId);
        return false;
    }

    public boolean updatePost(PostDto postDto,Integer postId) {
        Post post = new Post(postDto);
        return false;
    }
}

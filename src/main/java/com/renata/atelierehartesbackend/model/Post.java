package com.renata.atelierehartesbackend.model;

import com.renata.atelierehartesbackend.dto.blog.PostDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Post {

    private static final int MIN_TITLE_LENGTH = 7;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Length(min = MIN_TITLE_LENGTH, message = "Title must be at least " + MIN_TITLE_LENGTH + " characters long")
    @NotEmpty(message = "Please enter the title")
    @Column(nullable = false)
    private String title;

    @Column(nullable = false,length=999999999)
    @NotEmpty(message = "The body must not be null")
    private String body;


    @Column(name = "creation_date", updatable = false)
    private Date createdAt;
    public Post(String title, String body,User user) {
        this.title = title;
        this.body = body;
        createdAt = new Date();
    }

    public Post(PostDto postDto) {
        this.id = postDto.getId();
        this.title = postDto.getTitle();
        this.body = postDto.getBody();
    }
}

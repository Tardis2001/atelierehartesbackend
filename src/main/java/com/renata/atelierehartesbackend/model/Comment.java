package com.renata.atelierehartesbackend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;


@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    @Column(nullable = false,length=99999)
    @NotEmpty(message = "The body must not be null")
    private String body;

    @Column(name = "creation_date")
    private Date createdAt;

    @Column(name = "user")
    private String username;
    public Comment(String body,User user){
        this.body = body;
        this.username = user.getUsername();
        createdAt = new Date();

    }

}

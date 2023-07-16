package com.renata.atelierehartesbackend.dto.blog;

import com.renata.atelierehartesbackend.model.Comment;
import com.renata.atelierehartesbackend.model.Post;
import com.renata.atelierehartesbackend.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
    private Integer id;
    @NotNull
    private String body;
    @NotNull
    private Date createdAt;
    @NotNull
    private Post post;
    @NotNull
    private String username;
    public CommentDto(Comment comment){
        this.id = comment.getId();
        this.body = comment.getBody();
        this.username = comment.getUsername();
        this.createdAt = comment.getCreatedAt();
    }
}

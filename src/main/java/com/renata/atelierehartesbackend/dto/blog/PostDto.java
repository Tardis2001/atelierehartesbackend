package com.renata.atelierehartesbackend.dto.blog;

import com.renata.atelierehartesbackend.model.Post;
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
public class PostDto {
    private Integer id;
    @NotNull
    private String title;
    @NotNull
    private String body;

    public PostDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.body = post.getBody();
    }
}

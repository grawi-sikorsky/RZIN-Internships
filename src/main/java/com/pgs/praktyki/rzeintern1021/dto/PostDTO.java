package com.pgs.praktyki.rzeintern1021.dto;

import com.pgs.praktyki.rzeintern1021.models.Post;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.time.ZonedDateTime;

public class PostDTO {

    private Long id;

    private String title;

    private String content;

    private String username;

    private ZonedDateTime postdate;

    public PostDTO() {
    }

    public PostDTO(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.username = post.getUsername();
        this.postdate = post.getPostdate();
    }

    public Post toEntity() {
        Post post = new Post();

        post.setTitle(this.title);
        post.setContent(this.content);
        post.setUsername(this.username);
        post.setPostdate(this.postdate);

        return post;
    }

    public Post saveToProvidedEntity(Post incomingPost) {
        incomingPost.setTitle(this.title);
        incomingPost.setContent(this.content);
        incomingPost.setUsername(this.username);
        incomingPost.setPostdate(this.postdate);

        return incomingPost;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public ZonedDateTime getPostdate() {
        return postdate;
    }

    public void setPostdate(ZonedDateTime postdate) {
        this.postdate = postdate;
    }
}

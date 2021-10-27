package com.pgs.praktyki.rzeintern1021.models;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Entity
@Table(name = "user_posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 255)
    private String title;

    @Size(max = 512)
    private String content;

    @Size(max = 255)
    private String username;

    @Size(max = 255)
    private ZonedDateTime postdate;

    @Size(max = 255)
    private Long userId;

    @PrePersist
    public void prepersist() {
        this.postdate = ZonedDateTime.now(ZoneId.of("Europe/Warsaw"));
    }

    public Post() {
    }

    public Post(Long id, String title, String content, String username, ZonedDateTime postdate) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.username = username;
        this.postdate = postdate;
    }

    public Post(Long id, String title, String content, String username) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.username = username;

    }

    public Long getId() {
        return id;
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

    public void setUser(String username) {
        this.username = username;
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

    public Long getUserid() {
        return userId;
    }

    public void setUserid(Long userId) {
        this.userId = userId;
    }
}

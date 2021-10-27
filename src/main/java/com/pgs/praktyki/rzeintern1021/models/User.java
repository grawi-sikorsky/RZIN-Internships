package com.pgs.praktyki.rzeintern1021.models;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "user_entity")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 255)
    private String uuid;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "is_enabled")
    private Boolean isEnabled;

    @Size(max = 64)
    @Column(name = "activation_link")
    private String activationLink;

    @Size(max = 255)
    private String username;

    @Size(max = 255)
    private String password;

    @Size(max = 255)
    private String email;

    @Size(max = 255)
    private String firstname;

    @Size(max = 255)
    private String lastname;

    private Integer age;

    @Size(max = 255)
    private String phone;

    @Size(max = 255)
    @Column(name = "avatar_link")
    private String avatarLink;

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", referencedColumnName = "id")
    private List<Post> postList;

    @PrePersist
    public void prepersist() {
        this.uuid = UUID.randomUUID().toString();
        this.setActive(false);
        this.setActivationLink(UUID.randomUUID().toString());
        this.setEnabled(true);
    }

    public User() {
    }

    public User(Long id, String uuid, String username, String password, String email, String firstname, String lastname, Integer age) {
        this.id = id;
        this.uuid = uuid;
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.age = age;
    }

    public Long getId() {
        return id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<Post> getPostEntityList() {
        return postList;
    }

    public void setPostEntityList(List<Post> postList) {
        this.postList = postList;
    }

    public Boolean isActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public String getActivationLink() {
        return activationLink;
    }

    public void setActivationLink(String activationLink) {
        this.activationLink = activationLink;
    }

    public String getAvatarLink() {
        return avatarLink;
    }

    public void setAvatarLink(String avatarLink) {
        this.avatarLink = avatarLink;
    }

    public Boolean getEnabled() {
        return isEnabled;
    }

    public void setEnabled(Boolean enabled) {
        isEnabled = enabled;
    }
}

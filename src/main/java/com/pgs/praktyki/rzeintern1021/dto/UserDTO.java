package com.pgs.praktyki.rzeintern1021.dto;

import com.pgs.praktyki.rzeintern1021.models.User;

public class UserDTO {

    private String uuid;
    private String username;
    private String email;
    private String firstname;
    private String lastname;
    private Integer age;
    private String phone;
    private String avatarLink;

    public UserDTO() {
    }

    public UserDTO(User user) {
        this.username = user.getUsername();
        this.firstname = user.getFirstname();
        this.lastname = user.getLastname();
        this.email = user.getEmail();
        this.phone = user.getPhone();
        this.age = user.getAge();
        this.uuid = user.getUuid();
        this.avatarLink = user.getAvatarLink();
    }

    public User toEntity() {
        User user = new User();

        user.setUsername(this.username);
        user.setFirstname(this.firstname);
        user.setLastname(this.lastname);
        user.setEmail(this.email);
        user.setPhone(this.phone);
        user.setAge(this.age);
        user.setUuid(this.uuid);
        user.setAvatarLink(this.avatarLink);

        return user;
    }

    public User saveToEntity(User incUser) {
        incUser.setFirstname(this.firstname);
        incUser.setLastname(this.lastname);
        incUser.setPhone(this.phone);
        incUser.setAge(this.age);

        return incUser;
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

    public String getAvatarLink() {
        return avatarLink;
    }

    public void setAvatarLink(String avatarLink) {
        this.avatarLink = avatarLink;
    }
}

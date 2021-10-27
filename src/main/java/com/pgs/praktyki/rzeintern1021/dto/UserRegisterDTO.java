package com.pgs.praktyki.rzeintern1021.dto;

import com.pgs.praktyki.rzeintern1021.models.User;

import javax.validation.constraints.NotNull;

public class UserRegisterDTO {

    @NotNull(message = "Name is required")
    private String username;

    @NotNull(message = "Pass is required")
    private String password;

    @NotNull(message = "Email is required")
    private String email;

    @NotNull(message = "Firstname is required")
    private String firstname;

    @NotNull(message = "Lastname is required")
    private String lastname;

    @NotNull(message = "Age is required")
    private Integer age;

    @NotNull(message = "Phone is required")
    private String phone;

    public UserRegisterDTO() {
    }

    public UserRegisterDTO(User user) {
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.email = user.getEmail();
        this.firstname = user.getFirstname();
        this.lastname = user.getLastname();
        this.age = user.getAge();
        this.phone = user.getPhone();
    }

    public User toEntity() {
        User user = new User();

        user.setUsername(this.username);
        user.setPassword(this.password);
        user.setEmail(this.email);
        user.setFirstname(this.firstname);
        user.setLastname(this.lastname);
        user.setAge(this.age);
        user.setPhone(this.phone);

        return user;
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
}

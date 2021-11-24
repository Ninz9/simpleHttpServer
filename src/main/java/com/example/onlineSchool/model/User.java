package com.example.onlineSchool.model;

import com.example.onlineSchool.entity.UserEntity;

public class User {
    Long id;
    String username;

    public User() {
    }
    public static User toModel(UserEntity entity){
        User user = new User();
        user.setUsername(entity.getUsername());
        user.setId(entity.getId());
        return user;

    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

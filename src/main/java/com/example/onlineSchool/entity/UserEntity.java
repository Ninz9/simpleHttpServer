package com.example.onlineSchool.entity;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class UserEntity{
    @Id
    private Long id;
    private String username;
    private String password;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "teacher")
     private List<GroupEntity> teachClass;

   @ManyToMany
   private List<GroupEntity> studyInClasses;

    public UserEntity() {
        studyInClasses = new ArrayList<>();
    }

    public UserEntity(Long id,String username, String password){
        this.setId(id);
        this.setUsername(username);
        this.setPassword(password);
        studyInClasses = new ArrayList<>();
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void addGroup(GroupEntity group){
        studyInClasses.add(group);
    }

    public List<GroupEntity> getStudyInClasses() {
        return studyInClasses;
    }
}

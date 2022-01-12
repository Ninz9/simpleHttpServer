package com.example.onlineSchool.entity;

import javax.persistence.*;
import java.util.List;

@Entity
public class SubjectEntity {
    @Id
    private Long id;
    private String title;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "teacher")
    private List<GroupEntity> groupsStudySubject;




    public SubjectEntity() {
    }

    public SubjectEntity(Long id, String title){
        this.setId(id);
        this.setTitle(title);
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

    public List<GroupEntity> getGroupsStudySubject() {
        return groupsStudySubject;
    }

    public void setGroupsStudySubject(List<GroupEntity> groupsStudySubject) {
        this.groupsStudySubject = groupsStudySubject;
    }
}

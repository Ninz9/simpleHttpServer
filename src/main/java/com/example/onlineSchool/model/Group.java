package com.example.onlineSchool.model;

import com.example.onlineSchool.entity.GroupEntity;

public class Group {
    Long id_group;
    Long id_teacher;
    Long id_subject;
    public Group() {
    }
    public static Group toModel(GroupEntity entity){
        Group group = new Group();
        group.setId_group(entity.getId());
        group.setTeacher(entity.getTeacher().getId());
        group.setId_subject(entity.getSubject().getId());
        return group;
    }

    public Long getId_group() {
        return id_group;
    }

    public void setId_group(Long id_group) {
        this.id_group = id_group;
    }

    public Long getTeacher() {
        return id_teacher;
    }

    public void setTeacher(Long teacher) {
        this.id_teacher = teacher;
    }

    public Long getId_subject() {
        return id_subject;
    }

    public void setId_subject(Long id_subject) {
        this.id_subject = id_subject;
    }
}

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
        group.setGroup(entity.getId());
        group.setTeacher(entity.getTeacher().getId());
        group.setSubject(entity.getSubject().getId());
        return group;
    }

    public Long getGroup() {
        return id_group;
    }

    public void setGroup(Long id_group) {
        this.id_group = id_group;
    }

    public Long getTeacher() {
        return id_teacher;
    }

    public void setTeacher(Long teacher) {
        this.id_teacher = teacher;
    }

    public Long getSubject() {
        return id_subject;
    }

    public void setSubject(Long id_subject) {
        this.id_subject = id_subject;
    }
}

package com.example.onlineSchool.entity;

import javax.persistence.*;
import java.util.List;

@Entity
public class GroupEntity {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private UserEntity teacher;

    @ManyToOne
    @JoinColumn(name = "subject_id")
    private  SubjectEntity subject;


    @ManyToMany(cascade = CascadeType.ALL)
    private List<UserEntity> students;


    public GroupEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserEntity getTeacher() {
        return teacher;
    }

    public void setTeacher(UserEntity teacher) {
        this.teacher = teacher;
    }

    public SubjectEntity getSubject() {
        return subject;
    }

    public void setSubject(SubjectEntity subject) {
        this.subject = subject;
    }
    public void addStudent(UserEntity student){
        students.add(student);
    }

    public List<UserEntity> getStudents() {
        return students;
    }
}

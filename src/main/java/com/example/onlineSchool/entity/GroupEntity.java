package com.example.onlineSchool.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class GroupEntity {
    @Id
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
        students = new ArrayList<>();
    }

    public GroupEntity(Long id, SubjectEntity subject, UserEntity id_teacher){
       this.setId(id);
       this.setSubject(subject);
       this.setTeacher(id_teacher);
       students = new ArrayList<>();
    }
    public GroupEntity(Long id, SubjectEntity subject, UserEntity id_teacher, List<UserEntity> students){
        this.setId(id);
        this.setSubject(subject);
        this.setTeacher(id_teacher);
        this.students = students;
    }


    public GroupEntity(Long id){
        this.setId(id);
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

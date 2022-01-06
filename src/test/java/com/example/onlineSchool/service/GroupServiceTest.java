package com.example.onlineSchool.service;

import com.example.onlineSchool.entity.GroupEntity;
import com.example.onlineSchool.entity.SubjectEntity;
import com.example.onlineSchool.entity.UserEntity;
import com.example.onlineSchool.exception.GroupNotFoundExeption;
import com.example.onlineSchool.exception.StudentAlreadyStudyInThisGroup;
import com.example.onlineSchool.exception.UserNotFoundExeption;
import com.example.onlineSchool.model.User;
import com.example.onlineSchool.repository.GroupRepo;
import com.example.onlineSchool.repository.SubjectRepo;
import com.example.onlineSchool.repository.UserRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

import org.mockito.ArgumentCaptor;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;


@SpringBootTest
class GroupServiceTest {
    @Autowired
    GroupService groupService;



    @MockBean
    UserRepo userRepo;

    @MockBean
    GroupRepo groupRepo;

    @MockBean
    SubjectRepo subjectRepo;

    @Test
    void createGroup() {
        UserEntity teacher = new UserEntity(1L, "chertrom", "qwerty");
        SubjectEntity subject = new SubjectEntity(1L, "math");
        GroupEntity group = new GroupEntity(1L);

        BDDMockito.given(groupRepo.save(any(GroupEntity.class))).willReturn(group);
        BDDMockito.given(subjectRepo.findById(1L)).willReturn(java.util.Optional.of(subject));
        BDDMockito.given(userRepo.findById(1L)).willReturn(java.util.Optional.of(teacher));

        GroupEntity groupReturn = groupService.createGroup(group, teacher.getId(), subject.getId());
        Assertions.assertEquals(groupReturn.getId(),group.getId());
        Assertions.assertEquals(groupReturn.getSubject().getId(),subject.getId());
        Assertions.assertEquals(groupReturn.getTeacher().getId(), teacher.getId());

        ArgumentCaptor<GroupEntity> argumentCaptor = ArgumentCaptor.forClass(GroupEntity.class);
        Mockito.verify(groupRepo, Mockito.atLeastOnce()).save(argumentCaptor.capture());
        GroupEntity groupProvidedToSave = argumentCaptor.getValue();

        Assertions.assertEquals(group.getId(),groupProvidedToSave.getId());
        Assertions.assertEquals(group.getTeacher(),groupProvidedToSave.getTeacher());
        Assertions.assertEquals(group.getSubject(), groupProvidedToSave.getSubject());
    }

    @Test
    void addNewStudent() throws UserNotFoundExeption, GroupNotFoundExeption, StudentAlreadyStudyInThisGroup {
        UserEntity teacher = new UserEntity(1L, "chertrom", "qwerty");
        UserEntity newStudent1 = new UserEntity(2L,"Bob","qwerty1");
        UserEntity newStudent2 = new UserEntity(3L,"Bob1","1qwerty1");
        SubjectEntity subject = new SubjectEntity(1L, "math");
        GroupEntity group = new GroupEntity(1L,subject,teacher);

        List<UserEntity> controlRes = new ArrayList<>();
        controlRes.add(newStudent1);
        controlRes.add(newStudent2);

        BDDMockito.given(userRepo.findById(2L)).willReturn(java.util.Optional.of(newStudent1));
        BDDMockito.given(userRepo.findById(3L)).willReturn(java.util.Optional.of(newStudent2));
        BDDMockito.given(groupRepo.findById(1L)).willReturn(java.util.Optional.of(group));

         groupService.addNewStudent(1L,2L);
         groupService.addNewStudent(1L, 3L);

         Assertions.assertEquals(controlRes.size(),group.getStudents().size());

         for (int i = 0; i < group.getStudents().size();i++){
             Assertions.assertEquals(group.getStudents().get(i).getId(),controlRes.get(i).getId());
             Assertions.assertEquals(group.getStudents().get(i).getUsername(),controlRes.get(i).getUsername());
             Assertions.assertEquals(group.getStudents().get(i).getPassword(),controlRes.get(i).getPassword());

         }



    }

    @Test
    void changeTeacher() throws GroupNotFoundExeption, UserNotFoundExeption {
        UserEntity teacher = new UserEntity(1L, "chertrom", "qwerty");
        UserEntity newTeacher = new UserEntity(2L,"Bob","qwerty1");
        SubjectEntity subject = new SubjectEntity(1L, "math");
        GroupEntity group = new GroupEntity(1L,subject,teacher);

        BDDMockito.given(userRepo.findById(2L)).willReturn(java.util.Optional.of(newTeacher));

        BDDMockito.given(groupRepo.findById(1L)).willReturn(java.util.Optional.of(group));

        GroupEntity res = groupService.changeTeacher(1L,2L);

        Assertions.assertNotNull(res);
        Assertions.assertEquals(res.getTeacher().getId(),newTeacher.getId());
        Assertions.assertEquals(res.getTeacher().getUsername(),newTeacher.getUsername());
        Assertions.assertEquals(res.getTeacher().getPassword(), newTeacher.getPassword());




    }

    @Test
    void getOne() throws GroupNotFoundExeption {
        UserEntity teacher = new UserEntity(1L, "chertrom", "qwerty");
        SubjectEntity subject = new SubjectEntity(1L, "math");
        GroupEntity group = new GroupEntity(1L, subject, teacher);

        BDDMockito.given(groupRepo.findById(1L)).willReturn(java.util.Optional.of(group));

        GroupEntity res = groupService.getOne(1L);

        Assertions.assertEquals(res.getId(),group.getId());
        Assertions.assertEquals(res.getSubject().getId(),group.getSubject().getId());
        Assertions.assertEquals(res.getTeacher().getId(),group.getTeacher().getId());
    }

    @Test
    void getStudentsWhoStudyInGroup() throws GroupNotFoundExeption {
        UserEntity teacher = new UserEntity(1L, "chertrom", "qwerty");
        UserEntity newStudent1 = new UserEntity(2L,"Bob","qwerty1");
        UserEntity newStudent2 = new UserEntity(3L,"Bob1","1qwerty1");
        List<UserEntity> students = new ArrayList<>();
        students.add(newStudent1);
        students.add(newStudent2);
        List<User> controlRes = new ArrayList<>();
        controlRes.add(User.toModel(newStudent1));
        controlRes.add(User.toModel(newStudent2));
        SubjectEntity subject = new SubjectEntity(1L, "math");
        GroupEntity group = new GroupEntity(1L,subject,teacher,students);


        BDDMockito.given(groupRepo.findById(1L)).willReturn(java.util.Optional.of(group));

        List<User> res = groupService.getStudentsWhoStudyInGroup(1L);

        Assertions.assertEquals(controlRes.size(),group.getStudents().size());

        for (int i = 0; i < group.getStudents().size();i++){
            Assertions.assertEquals(res.get(i).getId(),controlRes.get(i).getId());
            Assertions.assertEquals(res.get(i).getUsername(),controlRes.get(i).getUsername());

        }
    }
}

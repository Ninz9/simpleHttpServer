package com.example.onlineSchool.controller;

import com.example.onlineSchool.entity.GroupEntity;
import com.example.onlineSchool.entity.SubjectEntity;
import com.example.onlineSchool.entity.UserEntity;
import com.example.onlineSchool.model.Group;
import com.example.onlineSchool.model.User;
import com.example.onlineSchool.service.GroupService;
import com.example.onlineSchool.service.UserService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.StringContains.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;


@SpringBootTest
@AutoConfigureMockMvc
class GroupControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    GroupService groupService;
    @Test
    void getGroupsWhereStudyUser() throws Exception {
        UserEntity user = new UserEntity(1L, "chertrom", "qwerty");
        SubjectEntity subject = new SubjectEntity(1L, "math");
        GroupEntity group = new GroupEntity(1L, subject , user);
        UserEntity student = new UserEntity(2L, "Bob", "qwerty");

        Group groupCheck = Group.toModel(group);
        BDDMockito.given(groupService.getOne(1L)).willReturn(group);

        mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .get("/groups")
                                .param("id", "1")
                                .param("flag", "1")
                ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.teacher", CoreMatchers.is(groupCheck.getTeacher().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id_subject", CoreMatchers.is(groupCheck.getId_subject().intValue())));


        List<User> retList = new ArrayList<>();
        retList.add(User.toModel(user));
        retList.add(User.toModel(student));
        BDDMockito.given(groupService.getStudentsWhoStudyInGroup(1L)).willReturn(retList);

        mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .get("/groups")
                                .param("id", "1")
                                .param("flag", "2")
                ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].username", CoreMatchers.is(user.getUsername())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].username",CoreMatchers.is(student.getUsername())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].id", CoreMatchers.is(user.getId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].id", CoreMatchers.is(student.getId().intValue())));

    }

    @Test
    void createGroup() throws Exception {
        UserEntity user = new UserEntity(1L, "chertrom", "qwerty");
        SubjectEntity subject = new SubjectEntity(1L, "math");
        GroupEntity group = new GroupEntity(1L);
        GroupEntity groupReturn = new GroupEntity(1L, subject , user);
        BDDMockito.given(groupService.createGroup(group, 1L,1L)).willReturn(groupReturn);
        String id_teacher = "1";
        String id_subject = "1";

        mockMvc.perform(
                MockMvcRequestBuilders.post("/groups")
                        .param("teacher_id", id_teacher)
                        .param("subject_id", id_subject)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}")
        ).andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(groupService, Mockito.atLeast(1)).createGroup(any(GroupEntity.class),any(Long.class), any(Long.class));

    }

    @Test
    void addStudent() throws Exception {
        UserEntity user = new UserEntity(1L, "chertrom", "qwerty");
        SubjectEntity subject = new SubjectEntity(1L, "math");
        GroupEntity group = new GroupEntity(1L, subject , user);
        UserEntity student = new UserEntity(2L, "chertrom1", "qwerty");
        group.addStudent(student);
        BDDMockito.given(groupService.addNewStudent(group.getId(), student.getId())).willReturn(group);

        mockMvc.perform(
                MockMvcRequestBuilders
                        .put("/groups")
                        .param("groupId", "1")
                        .param("studentID", "2")
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(containsString("Student has been added")));
    }

    @Test
    void changeTeacher() throws Exception {
        UserEntity user = new UserEntity(1L, "chertrom", "qwerty");
        SubjectEntity subject = new SubjectEntity(1L, "math");
        GroupEntity group = new GroupEntity(1L, subject , user);

        BDDMockito.given(groupService.changeTeacher(1L,1L)).willReturn(group);

        mockMvc.perform(
                MockMvcRequestBuilders
                        .put("/groups/changeTeacher")
                        .param("groupId", "1")
                        .param("teacherId", "1")
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(containsString("Teacher was changed")));
    }
}
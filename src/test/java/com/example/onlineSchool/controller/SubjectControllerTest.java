package com.example.onlineSchool.controller;

import com.example.onlineSchool.entity.SubjectEntity;
import com.example.onlineSchool.exception.SubjectAlreadyExistException;
import com.example.onlineSchool.service.SubjectService;
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
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.core.StringContains.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;


@SpringBootTest
@AutoConfigureMockMvc
class SubjectControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    SubjectService subjectService;


    @Test
    void createNewSubject() throws  Exception {
        SubjectEntity subject = new SubjectEntity(1L,"math");
        BDDMockito.given(subjectService.createNewSubject(any(SubjectEntity.class))).willReturn(subject);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/subjects", subject)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"math\"}")

        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(containsString("New subject!")));


        BDDMockito.given(subjectService.createNewSubject(any(SubjectEntity.class))).willThrow(SubjectAlreadyExistException.class);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/subjects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"math\"}")
        ).andExpect(MockMvcResultMatchers.status().isConflict());
        Mockito.verify(subjectService, Mockito.atLeast(2)).createNewSubject(any(SubjectEntity.class));
    }

    @Test
    void returnSubjectById() throws Exception {
        SubjectEntity subject = new SubjectEntity(1L,"math");
        BDDMockito.given(subjectService.getSubject(1L)).willReturn(subject);
        String id = "1";
        mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/subjects").param("id", id)
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", CoreMatchers.is(subject.getTitle())));
        Mockito.verify(subjectService, Mockito.atLeastOnce()).getSubject(1L);
    }

    @Test
    void deleteSubject() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/subjects/{id}", 1)
        ).andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(subjectService,Mockito.atLeastOnce()).deleteSubject(1L);
    }
}
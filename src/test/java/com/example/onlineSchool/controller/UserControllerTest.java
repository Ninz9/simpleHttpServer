package com.example.onlineSchool.controller;

import com.example.onlineSchool.entity.SubjectEntity;
import com.example.onlineSchool.entity.UserEntity;
import com.example.onlineSchool.exception.SubjectAlreadyExistException;
import com.example.onlineSchool.exception.UserAlreadyExistException;
import com.example.onlineSchool.exception.UserNotFoundExeption;
import com.example.onlineSchool.model.User;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.core.StringContains.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {


    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserService userService;


    @Test
    void registration() throws Exception {
        UserEntity user = new UserEntity(1L,"chertrom", "qwerty");
        BDDMockito.given(userService.registration(any(UserEntity.class))).willReturn(user);

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/users", user)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"username\":\"chertrom\", \"password\":\"qwerty\"}")

                ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(containsString("New USER!")));


        BDDMockito.given(userService.registration(any(UserEntity.class))).willThrow(UserAlreadyExistException.class);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"chertrom\", \"password\":\"qwerty\"}")
        ).andExpect(MockMvcResultMatchers.status().isBadRequest());
        Mockito.verify(userService, Mockito.atLeast(2)).registration(any(UserEntity.class));
    }

    @Test
    void getOneUser() throws Exception {
        User user = new User(1L, "chertrom");
        BDDMockito.given(userService.getOne(1L)).willReturn(user);
        String id = "1";
        String flag = "1";
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/users").param("id", id).param("flag", flag)
                ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username", CoreMatchers.is(user.getUsername())));
        Mockito.verify(userService, Mockito.atLeastOnce()).getOne(1L);


    }

    @Test
    void deleteUser() throws  Exception{

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/users/{id}", 1)
        ).andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(userService, Mockito.atLeastOnce()).deleteOne(1L);
    }

    @Test
    void changeUsername()  throws  Exception{
        UserEntity user = new UserEntity(1L,"chertrom", "qwerty");
        String id1 = "1";
        String id2 = "2";
        String newUsername = "chertrom";
        BDDMockito.given(userService.changeUsername(1L, newUsername)).willReturn(user);

        mockMvc.perform(
                MockMvcRequestBuilders
                        .put("/users")
                        .param("id", id1)
                        .param("newName", newUsername)

        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(containsString("Username was changed")));

        BDDMockito.given(userService.changeUsername(2L,"chertrom")).willThrow(UserNotFoundExeption.class);

        mockMvc.perform(
                MockMvcRequestBuilders
                        .put("/users")
                        .param("id", id2)
                        .param("newName", newUsername)

        ).andExpect(MockMvcResultMatchers.status().isBadRequest());
    }


}
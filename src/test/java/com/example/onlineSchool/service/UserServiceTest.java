package com.example.onlineSchool.service;

import com.example.onlineSchool.entity.GroupEntity;
import com.example.onlineSchool.entity.SubjectEntity;
import com.example.onlineSchool.entity.UserEntity;
import com.example.onlineSchool.exception.UserAlreadyExistException;
import com.example.onlineSchool.exception.UserDontHaveGroups;
import com.example.onlineSchool.exception.UserNotFoundExeption;
import com.example.onlineSchool.model.Group;
import com.example.onlineSchool.model.User;
import com.example.onlineSchool.repository.UserRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import com.example.onlineSchool.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
class UserServiceTest {
    @Autowired
    private UserService userService;

    @MockBean
    private UserRepo userRepo;

    @Test
    void testRegistrationMethod() throws UserAlreadyExistException {
        UserEntity user = new UserEntity( 1L,"chertrom", "qwerty");

        BDDMockito.given(userRepo.save(any(UserEntity.class))).willReturn(user);

        UserEntity returnUser = userService.registration(user);

        Assertions.assertEquals(user.getId(), returnUser.getId());
        Assertions.assertEquals(user.getUsername(), returnUser.getUsername());
        Assertions.assertEquals(user.getPassword(), returnUser.getPassword());

        ArgumentCaptor<UserEntity> argumentCaptor = ArgumentCaptor.forClass(UserEntity.class);
        Mockito.verify(userRepo, Mockito.atLeastOnce()).save(argumentCaptor.capture());

        UserEntity userProvidedToSave = argumentCaptor.getValue();
        Assertions.assertEquals(user.getUsername(), userProvidedToSave.getUsername());
        Assertions.assertEquals(user.getPassword(), userProvidedToSave.getPassword());
    }

    @Test
    void getOne() throws UserNotFoundExeption{
        UserEntity user = new UserEntity( 1L,"chertrom", "qwerty");
        User resControl = User.toModel(new UserEntity(1L, "chertrom", "qwerty"));

        BDDMockito.given(userRepo.findById(1L)).willReturn(java.util.Optional.of(user));
        User res = userService.getOne(1L);

        Assertions.assertEquals(res.getId(),resControl.getId());
        Assertions.assertEquals(res.getUsername(),resControl.getUsername());
    }




    @Test
    void deleteOne() throws UserNotFoundExeption{

        UserEntity user = new UserEntity( 1L,"chertrom", "qwerty");

        BDDMockito.given(userRepo.findById(user.getId())).willReturn(java.util.Optional.of(user));

        UserEntity res = userService.deleteOne(user.getId());

        Assertions.assertEquals(res.getId(), user.getId());
        Assertions.assertEquals(res.getUsername(), user.getUsername());
        Assertions.assertEquals(res.getPassword(), user.getPassword());
    }





    @Test
    void changeUsername() throws  UserNotFoundExeption{

        UserEntity userReturn = new UserEntity(1L,"chertrom","qwerty");
        UserEntity userControl = new UserEntity(1L,"Bob", "qwerty");


        BDDMockito.given(userRepo.findById(any())).willReturn(java.util.Optional.of(userReturn));
        UserEntity userResult = userService.changeUsername(userReturn.getId(), "Bob");

        Assertions.assertEquals(userResult.getUsername(), userControl.getUsername());
        Assertions.assertEquals(userResult.getId(), userControl.getId());
        Assertions.assertEquals(userResult.getPassword(), userControl.getPassword());




    }

    @Test
    void getGroups() throws UserNotFoundExeption, UserDontHaveGroups {

        List<Group> resControl = new ArrayList<>();
        UserEntity teacher1 = new UserEntity(1L,"JOHN", "qwerty");
        UserEntity teacher2 = new UserEntity(2L,"JOHN1", "qwerty");
        SubjectEntity subject1 = new SubjectEntity(1L, "math");
        SubjectEntity subject2 = new SubjectEntity(2L, "ICT");
        GroupEntity group1 = new GroupEntity(1L, subject1, teacher1);
        GroupEntity group2 = new GroupEntity(2L,  subject2, teacher2);

        resControl.add(Group.toModel(group1));
        resControl.add(Group.toModel(group2));

        UserEntity userReturn = new UserEntity(3L,"username", "qwerty");
        userReturn.addGroup(group1);
        userReturn.addGroup(group2);

        BDDMockito.given(userRepo.findById(any())).willReturn(java.util.Optional.of(userReturn));

        List<Group> res = userService.getGroups(userReturn.getId());

        Assertions.assertEquals(res.size(),resControl.size());
        for (int i = 0; i < res.size();i++){
            Assertions.assertEquals(res.get(i).getId_subject(),resControl.get(i).getId_subject());
            Assertions.assertEquals(res.get(i).getId_group(),resControl.get(i).getId_group());
            Assertions.assertEquals(res.get(i).getTeacher(),resControl.get(i).getTeacher());
        }

    }
}
package com.example.onlineSchool.service;

import com.example.onlineSchool.entity.GroupEntity;
import com.example.onlineSchool.entity.UserEntity;
import com.example.onlineSchool.exception.UserAlreadyExistException;
import com.example.onlineSchool.exception.UserDontHaveGroups;
import com.example.onlineSchool.exception.UserNotFoundExeption;
import com.example.onlineSchool.model.Group;
import com.example.onlineSchool.model.User;
import com.example.onlineSchool.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;


    public UserEntity registration(UserEntity user) throws UserAlreadyExistException {
        if (userRepo.findByUsername(user.getUsername()) != null) {
            throw new UserAlreadyExistException("User with this username already exist");
        }
        return userRepo.save(user);
    }



    public User getOne(Long id) throws UserNotFoundExeption {
        UserEntity  user = userRepo.findById(id).orElseThrow(()->new UserNotFoundExeption("User not found"));
        return User.toModel(user);
    }



    public Long deleteOne(Long id) throws UserNotFoundExeption {
        UserEntity tmp = userRepo.findById(id).orElseThrow(()->new UserNotFoundExeption("User not found"));
        userRepo.deleteById(id);
        return id;
    }



    public UserEntity changeUsername(Long id, String newUsername) throws UserNotFoundExeption, UserAlreadyExistException {
        UserEntity user = userRepo.findById(id).orElseThrow(()->new UserNotFoundExeption("User not found"));
        UserEntity user1 = userRepo.findByUsername(newUsername);
        if (user1 != null) throw new UserAlreadyExistException("User already exist");
        user.setUsername(newUsername);
        userRepo.save(user);
        return user;
    }


    public List<Group> getGroups(Long id) throws UserNotFoundExeption, UserDontHaveGroups{
        UserEntity user = userRepo.findById(id).orElseThrow(() -> new UserNotFoundExeption("User not found"));
        List<Group> tmp = new ArrayList<>();
        List<GroupEntity> studyInClasses = user.getStudyInClasses();
        for (GroupEntity i: studyInClasses){
            tmp.add(Group.toModel(i));
        }
        if (tmp.isEmpty()){
            throw new UserDontHaveGroups("User dont have groups");
        }
        return tmp;
    }

}

package com.example.onlineSchool.service;

import com.example.onlineSchool.entity.GroupEntity;
import com.example.onlineSchool.entity.UserEntity;
import com.example.onlineSchool.exception.UserAlreadyExistException;
import com.example.onlineSchool.exception.UserNotFoundExeption;
import com.example.onlineSchool.model.Group;
import com.example.onlineSchool.model.User;
import com.example.onlineSchool.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        UserEntity  user = userRepo.findById(id).get();
        if (user == null){
            throw new UserNotFoundExeption("User not found");
        }
        return User.toModel(user);
    }

    public Long deleteOne(Long id) throws UserNotFoundExeption {
        userRepo.deleteById(id);
        return id;
    }
    public  Long changeUsername(Long id, String newUsername) throws UserNotFoundExeption {
        UserEntity user = userRepo.findById(id).get();
        if (user == null){
            throw new UserNotFoundExeption("User not found");
        }
        user.setUsername(newUsername);
        userRepo.save(user);
        return id;
    }
    public List<Group> getGroups(Long id) throws UserNotFoundExeption{
        UserEntity user = userRepo.findById(id).orElseThrow(() -> new UserNotFoundExeption("User not found"));
        List<Group> tmp = null;
        for (GroupEntity i: user.getStudyInClasses()){
            tmp.add(Group.toModel(i));
        }
            return tmp;
    }
}

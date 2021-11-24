package com.example.onlineSchool.service;

import com.example.onlineSchool.entity.GroupEntity;
import com.example.onlineSchool.entity.SubjectEntity;
import com.example.onlineSchool.entity.UserEntity;
import com.example.onlineSchool.exception.GroupNotFoundedExeption;
import com.example.onlineSchool.exception.UserNotFoundExeption;
import com.example.onlineSchool.model.User;
import com.example.onlineSchool.repository.GroupRepo;
import com.example.onlineSchool.repository.SubjectRepo;
import com.example.onlineSchool.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupService {
    @Autowired
    GroupRepo groupRepo;
    @Autowired
    UserRepo userRepo;
    @Autowired
    SubjectRepo subjectRepo;

    public GroupEntity createGroup(GroupEntity group, Long userId, Long subjectID){
        UserEntity user  = userRepo.findById(userId).get();
        SubjectEntity subject = subjectRepo.findById(subjectID).get();
        group.setTeacher(user);
        group.setSubject(subject);
        return groupRepo.save(group);
    }


    public GroupEntity addNewStudent(Long groupId, Long studentId) throws UserNotFoundExeption, GroupNotFoundedExeption {
        GroupEntity group =  groupRepo.findById(groupId).get();
        UserEntity student  = userRepo.findById(studentId).get();
        if (student == null){
            throw new UserNotFoundExeption("User not found");
        }
        if (group == null){
            throw new GroupNotFoundedExeption("Group not founded");
        }
        group.addStudent(student);
        return groupRepo.save(group);
    }

    public Boolean changeTeacher (Long groupId, Long teacherId) throws GroupNotFoundedExeption, UserNotFoundExeption {
        GroupEntity group =  groupRepo.findById(groupId).get();
        UserEntity teacher  = userRepo.findById(teacherId).get();
        if (group == null){
            throw new GroupNotFoundedExeption("Group not founded");
        }
        if (teacher == null){
            throw new UserNotFoundExeption("User not founded");
        }
        group.setTeacher(teacher);
        groupRepo.save(group);
        return true;
    }


    public GroupEntity getOne(Long id) throws GroupNotFoundedExeption {
        GroupEntity group = groupRepo.findById(id).get();
        if (group == null){
            throw new GroupNotFoundedExeption("Group not found");
        }
        return group;
    }
}

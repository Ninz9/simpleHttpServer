package com.example.onlineSchool.service;

import com.example.onlineSchool.entity.GroupEntity;
import com.example.onlineSchool.entity.SubjectEntity;
import com.example.onlineSchool.entity.UserEntity;
import com.example.onlineSchool.exception.GroupNotFoundExeption;
import com.example.onlineSchool.exception.UserNotFoundExeption;
import com.example.onlineSchool.model.User;
import com.example.onlineSchool.repository.GroupRepo;
import com.example.onlineSchool.repository.SubjectRepo;
import com.example.onlineSchool.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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


    public GroupEntity addNewStudent(Long groupId, Long studentId) throws UserNotFoundExeption, GroupNotFoundExeption {
        GroupEntity group =  groupRepo.findById(groupId).get();
        UserEntity student  = userRepo.findById(studentId).get();
        if (student == null){
            throw new UserNotFoundExeption("User not found");
        }
        if (group == null){
            throw new GroupNotFoundExeption("Group not founded");
        }
        group.addStudent(student);
        student.addGroup(group);
        return groupRepo.save(group);
    }

    public Boolean changeTeacher (Long groupId, Long teacherId) throws GroupNotFoundExeption, UserNotFoundExeption {
        GroupEntity group =  groupRepo.findById(groupId).orElseThrow(() -> new GroupNotFoundExeption("Group not found"));
        UserEntity teacher  = userRepo.findById(teacherId).orElseThrow(() -> new UserNotFoundExeption("User not found"));
        group.setTeacher(teacher);
        groupRepo.save(group);
        return true;
    }


    public GroupEntity getOne(Long id) throws GroupNotFoundExeption {
        GroupEntity group = groupRepo.findById(id).get();
        if (group == null){
            throw new GroupNotFoundExeption("Group not found");
        }
        return group;
    }
    public List<User> getStudentsWhoStudyInGroup(Long id) throws  GroupNotFoundExeption{
        GroupEntity group = groupRepo.findById(id).orElseThrow(() -> new GroupNotFoundExeption("Group Not Found"));
        List<User> tmp = null;
        for (int i = 0; i< group.getStudents().size();i++){
            tmp.add(User.toModel(group.getStudents().get(i)));
        }
        return tmp;
    }


}

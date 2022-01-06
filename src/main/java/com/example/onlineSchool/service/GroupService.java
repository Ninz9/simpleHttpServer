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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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


    public GroupEntity addNewStudent(Long groupId, Long studentId) throws UserNotFoundExeption, GroupNotFoundExeption,StudentAlreadyStudyInThisGroup {
        GroupEntity group =  groupRepo.findById(groupId).orElseThrow(()->new GroupNotFoundExeption("Group not found"));
        UserEntity student  = userRepo.findById(studentId).orElseThrow(()->new UserNotFoundExeption("User not found"));
        if (groupHaveThisStudent(studentId, groupId)) throw new StudentAlreadyStudyInThisGroup("Student already study in this group");
        group.addStudent(student);
        student.addGroup(group);
        return groupRepo.save(group);
    }

    private boolean groupHaveThisStudent(Long id_student, Long id_group) throws GroupNotFoundExeption, UserNotFoundExeption {
        GroupEntity group = groupRepo.findById(id_group).get();
        for (UserEntity i: group.getStudents()){
            if (i.getId().equals(id_student)){
                return true;
            }
        }
        return false;
    }


    public GroupEntity changeTeacher (Long groupId, Long teacherId) throws GroupNotFoundExeption, UserNotFoundExeption {
        GroupEntity group =  groupRepo.findById(groupId).orElseThrow(() -> new GroupNotFoundExeption("Group not found"));
        UserEntity teacher  = userRepo.findById(teacherId).orElseThrow(() -> new UserNotFoundExeption("User not found"));
        group.setTeacher(teacher);
        groupRepo.save(group);
        return group;
    }


    public GroupEntity getOne(Long id) throws GroupNotFoundExeption {
        GroupEntity group = groupRepo.findById(id).orElseThrow(()->new GroupNotFoundExeption("Group not found"));
        return group;
    }


    public List<User> getStudentsWhoStudyInGroup(Long id) throws  GroupNotFoundExeption{
        GroupEntity group = groupRepo.findById(id).orElseThrow(() -> new GroupNotFoundExeption("Group Not Found"));
        List<User> tmp = new ArrayList<>();
        for (int i = 0; i< group.getStudents().size();i++){
            tmp.add(User.toModel(group.getStudents().get(i)));
        }
        return tmp;
    }


}

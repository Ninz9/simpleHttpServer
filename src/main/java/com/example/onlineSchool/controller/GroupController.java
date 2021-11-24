package com.example.onlineSchool.controller;

import com.example.onlineSchool.entity.GroupEntity;
import com.example.onlineSchool.exception.GroupNotFoundExeption;
import com.example.onlineSchool.exception.UserNotFoundExeption;
import com.example.onlineSchool.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/group")
public class GroupController {
    @Autowired
    GroupService groupService;

    @GetMapping
    public ResponseEntity getGroup(@RequestParam Long id, @RequestParam Long flag) {
        if (flag == 1) {
            try {
                return ResponseEntity.ok(groupService.getOne(id));
            } catch (GroupNotFoundExeption groupNotFoundedExeption) {
                return ResponseEntity.badRequest().body(groupNotFoundedExeption);
            } catch (Exception e) {
                return ResponseEntity.badRequest().body("ERROR");
            }
        } else {
            try {
                return ResponseEntity.ok(groupService.getStudentsWhoStudyInGroup(id));
            } catch (GroupNotFoundExeption groupNotFoundExeption) {
                return ResponseEntity.badRequest().body(groupNotFoundExeption);
            }
        }
    }




    @PostMapping
    public ResponseEntity createGroup(@RequestBody GroupEntity group, @RequestParam Long teacher_id,@RequestParam Long subject_id){
        try {
            groupService.createGroup(group,teacher_id,subject_id);
           return  ResponseEntity.ok("Group has been created");
        } catch (Exception e){
             return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity addStudent(@RequestParam Long groupId,@RequestParam Long studentId){
           try {
               groupService.addNewStudent(groupId,studentId);
               return ResponseEntity.ok("Student has been added");

           } catch (UserNotFoundExeption | GroupNotFoundExeption userNotFoundExeption) {
               return ResponseEntity.badRequest().body(userNotFoundExeption);
           } catch (Exception e) {
                return ResponseEntity.badRequest().body("ERROR");
           }
    }
    @PutMapping("/changeTeacher")
    public ResponseEntity changeTeacher(@RequestParam Long groupId,@RequestParam Long teacherId){
        try {
            groupService.changeTeacher(groupId,teacherId);
            return ResponseEntity.ok("Teacher was changed");
        } catch (UserNotFoundExeption | GroupNotFoundExeption userNotFoundExeption) {
            return ResponseEntity.badRequest().body(userNotFoundExeption);
        }
    }


}

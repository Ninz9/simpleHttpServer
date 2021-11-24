package com.example.onlineSchool.controller;


import com.example.onlineSchool.entity.SubjectEntity;

import com.example.onlineSchool.exception.SubjectAlreadyExistException;
import com.example.onlineSchool.exception.UserAlreadyExistException;
import com.example.onlineSchool.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/subject")
public class SubjectController {

    @Autowired
    private SubjectService subjectService;

@PostMapping
    public ResponseEntity createNewSubject(@RequestBody SubjectEntity subject){
    try {
        subjectService.createNewSubject(subject);
        return  ResponseEntity.ok("New subject!");
    }
    catch (SubjectAlreadyExistException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    } catch (Exception e){
        return ResponseEntity.badRequest().body("Error");
    }
}


}

package com.example.onlineSchool.controller;


import com.example.onlineSchool.entity.SubjectEntity;

import com.example.onlineSchool.exception.SubjectAlreadyExistException;
import com.example.onlineSchool.exception.SubjectNotFoundExeption;
import com.example.onlineSchool.exception.UserAlreadyExistException;
import com.example.onlineSchool.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/subjects")
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

    @GetMapping
    public ResponseEntity returnSubjectById(@RequestParam Long id){
      try {
          return ResponseEntity.ok(subjectService.getSubject(id));
      } catch (SubjectNotFoundExeption subjectNotFoundExeption){
          return ResponseEntity.badRequest().body(subjectNotFoundExeption);
      } catch (Exception e){
          return ResponseEntity.badRequest().body("ERROR");
      }

    }
    @DeleteMapping("/{id}")
    public ResponseEntity deleteSubject (@PathVariable Long id){
        try {
            return ResponseEntity.ok(subjectService.deleteSubject(id));
        }
        catch (SubjectNotFoundExeption subjectNotFoundExeption){
            return ResponseEntity.badRequest().body(subjectNotFoundExeption);
        }
    }

}

package com.example.onlineSchool.service;


import com.example.onlineSchool.entity.SubjectEntity;
import com.example.onlineSchool.exception.SubjectAlreadyExistException;
import com.example.onlineSchool.repository.SubjectRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubjectService {
    @Autowired
    SubjectRepo subjectRepo;



    public SubjectEntity createNewSubject(SubjectEntity subject) throws SubjectAlreadyExistException {
        if (subjectRepo.findByTitle(subject.getTitle()) != null){
             throw new SubjectAlreadyExistException("Subject already exist");
        }
        return subjectRepo.save(subject);
    }

}

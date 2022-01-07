package com.example.onlineSchool.service;

import com.example.onlineSchool.entity.SubjectEntity;
import com.example.onlineSchool.entity.UserEntity;
import com.example.onlineSchool.exception.SubjectAlreadyExistException;
import com.example.onlineSchool.exception.SubjectNotFoundExeption;
import com.example.onlineSchool.repository.SubjectRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;



@SpringBootTest
class SubjectServiceTest {

    @Autowired
    SubjectService subjectService;

    @MockBean
    SubjectRepo subjectRepo;


    @Test
    void createNewSubject() throws SubjectAlreadyExistException {
        SubjectEntity subject = new SubjectEntity( 1L, "math");

        BDDMockito.when(subjectRepo.save(any(SubjectEntity.class))).thenReturn(subject);

        SubjectEntity returnSubject = subjectService.createNewSubject(subject);

        Assertions.assertEquals(subject.getId(), returnSubject.getId());
        Assertions.assertEquals(subject.getId(), returnSubject.getId());
        Assertions.assertEquals(subject.getTitle(), returnSubject.getTitle());

        ArgumentCaptor<SubjectEntity> argumentCaptor = ArgumentCaptor.forClass(SubjectEntity.class);
        Mockito.verify(subjectRepo, Mockito.atLeastOnce()).save(argumentCaptor.capture());

        SubjectEntity subjectProvidedToSave = argumentCaptor.getValue();
        Assertions.assertEquals(subject.getId(), subjectProvidedToSave.getId());
        Assertions.assertEquals(subject.getTitle(), subjectProvidedToSave.getTitle());

    }

    @Test
    void getSubject() throws SubjectNotFoundExeption {
        SubjectEntity subject = new SubjectEntity( 1L, "math");
        BDDMockito.given(subjectRepo.findById(1L)).willReturn(java.util.Optional.of(subject));

        SubjectEntity res = subjectService.getSubject(1L);


        Assertions.assertNotNull(res);
        Assertions.assertEquals(res.getId(), subject.getId());
        Assertions.assertEquals(res.getTitle(), res.getTitle());

    }

    @Test
    void deleteSubject() throws SubjectNotFoundExeption {
        SubjectEntity subject = new SubjectEntity( 1L, "math");

        BDDMockito.given(subjectRepo.findById(1L)).willReturn(java.util.Optional.of(subject));

        boolean res = subjectService.deleteSubject(subject.getId());

        Assertions.assertTrue(res);
    }
}
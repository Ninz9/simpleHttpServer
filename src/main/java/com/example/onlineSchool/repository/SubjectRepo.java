package com.example.onlineSchool.repository;

import com.example.onlineSchool.entity.SubjectEntity;
import com.example.onlineSchool.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface SubjectRepo extends CrudRepository<SubjectEntity,Long> {
    SubjectEntity findByTitle(String title);
}

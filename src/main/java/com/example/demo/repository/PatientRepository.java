package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.PatientEntity;
import com.example.demo.entity.UserEntity;

@Repository
public interface PatientRepository extends JpaRepository<PatientEntity,String>{
    List<PatientEntity> findByUserEntity(UserEntity userEntity);
}

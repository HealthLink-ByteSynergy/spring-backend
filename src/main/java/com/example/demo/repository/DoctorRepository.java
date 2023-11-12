package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.DoctorEntity;
import com.example.demo.entity.UserEntity;

public interface DoctorRepository extends JpaRepository<DoctorEntity,String>{
    Optional<DoctorEntity> findByUserEntity(UserEntity userEntity);
}

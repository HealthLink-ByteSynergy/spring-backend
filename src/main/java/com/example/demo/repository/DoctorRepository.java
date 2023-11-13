package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.DoctorEntity;
import com.example.demo.entity.UserEntity;

@Repository
public interface DoctorRepository extends JpaRepository<DoctorEntity,String>{
    Optional<DoctorEntity> findByUserEntity(UserEntity userEntity);
    List<DoctorEntity> findAllBySpecialization(String specialization);
}

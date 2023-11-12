package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.PrescriptionEntity;

public interface PrescriptionRepository extends JpaRepository<PrescriptionEntity,String>{
    
}

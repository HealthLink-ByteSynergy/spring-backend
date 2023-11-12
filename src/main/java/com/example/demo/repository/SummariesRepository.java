package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.SummariesEntity;

public interface SummariesRepository extends JpaRepository<SummariesEntity,String>{
    
}

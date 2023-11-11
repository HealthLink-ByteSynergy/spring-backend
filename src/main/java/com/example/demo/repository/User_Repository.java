package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.User_Entity;

@Repository
public interface User_Repository extends JpaRepository<User_Entity, String> {
    Optional<User_Entity> findByEmail(String email);
}
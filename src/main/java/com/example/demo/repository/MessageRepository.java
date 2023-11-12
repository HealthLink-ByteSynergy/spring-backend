package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.MessageEntity;
import com.example.demo.entity.PatientEntity;

public interface MessageRepository extends JpaRepository<MessageEntity,String>{
     List<MessageEntity> findAllByRecPatientEntity(PatientEntity patientEntity);
     List<MessageEntity> findAllBySenPatientEntity(PatientEntity patientEntity);
}

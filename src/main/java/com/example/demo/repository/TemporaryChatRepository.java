package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.PatientEntity;
import com.example.demo.entity.TemporaryChatEntity;

@Repository
public interface TemporaryChatRepository extends JpaRepository<TemporaryChatEntity,String>{
    List<TemporaryChatEntity> findAllByPatientEntityInAndDocPatientEntityIn
     (List<PatientEntity> PatientEntity, List<PatientEntity> docPatientEntity);
    List<TemporaryChatEntity> deleteByPatientEntityInAndDocPatientEntityIn(List<PatientEntity> PatientEntity, List<PatientEntity> docPatientEntity);
    List<TemporaryChatEntity> findAllByPatientEntity(PatientEntity patientEntity);
    List<TemporaryChatEntity> findAllByDocPatientEntity(PatientEntity docPatientEntity);

}
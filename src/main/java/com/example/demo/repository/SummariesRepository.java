package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.DoctorEntity;
import com.example.demo.entity.PatientEntity;
import com.example.demo.entity.SummariesEntity;

@Repository
public interface SummariesRepository extends JpaRepository<SummariesEntity,String>{
    List<SummariesEntity> findAllByPatientEntity(PatientEntity patientEntity);
    List<SummariesEntity> findAllByDoctorEntity(DoctorEntity doctorEntity);
}

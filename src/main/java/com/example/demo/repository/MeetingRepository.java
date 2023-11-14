package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.DoctorEntity;
import com.example.demo.entity.MeetingEntity;
import com.example.demo.entity.PatientEntity;

import java.util.List;

@Repository
public interface MeetingRepository extends JpaRepository<MeetingEntity,String> {
    List<MeetingEntity> findAllByRecPatientEntity(PatientEntity patientEntity);
    List<MeetingEntity> findAllByDoctorEntity(DoctorEntity doctorEntity);
}

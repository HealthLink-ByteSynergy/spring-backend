package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.PatientEntity;

public interface PatientService {
    PatientEntity getByPatientId(String patientId);
    List<PatientEntity> getByUserId(String userId);
    PatientEntity savePatientDetails(PatientEntity patientEntity);
    void deleteByPatientId(String patientId);
    PatientEntity updatePatientDetails(PatientEntity patientEntity); //save
}

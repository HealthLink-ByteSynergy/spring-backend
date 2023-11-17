package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.PatientEntity;
import com.example.demo.exception.InvalidFormatException;
import com.example.demo.exception.ItemNotFoundException;

public interface PatientService {
    PatientEntity getByPatientId(String patientId) throws ItemNotFoundException;

    List<PatientEntity> getByUserId(String userId) throws ItemNotFoundException;

    PatientEntity savePatientDetails(PatientEntity patientEntity) throws InvalidFormatException;

    void deleteByPatientId(String patientId) throws ItemNotFoundException;
    
    PatientEntity updatePatientDetails(PatientEntity patientEntity) throws ItemNotFoundException; //save

}

package com.example.demo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.PatientEntity;
import com.example.demo.exception.InvalidFormatException;
import com.example.demo.exception.ItemNotFoundException;
import com.example.demo.service.PatientService;

import jakarta.transaction.Transactional;

import org.springframework.web.bind.annotation.RequestBody;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/patient")
@RequiredArgsConstructor
public class PatientController {
    
    private final PatientService patientService;

    @GetMapping("/id/{patientId}")
    public PatientEntity getPatient(@PathVariable("patientId") String patientId) throws ItemNotFoundException{
        return patientService.getByPatientId(patientId);
    }

    @GetMapping("/getByUserId/{userId}")
    public List<PatientEntity> getByUser(@PathVariable("userId") String userId) throws ItemNotFoundException{
        return patientService.getByUserId(userId);
    }
    
    @PostMapping("/save")
    public PatientEntity savePatient(@RequestBody PatientEntity patientEntity) throws InvalidFormatException{
        return patientService.savePatientDetails(patientEntity);
    }

    @Transactional
    @DeleteMapping("/delete/{patientId}")
    public void DeletePatient(@PathVariable("patientId") String patientId) throws ItemNotFoundException{
        patientService.deleteByPatientId(patientId);
    }

    @PutMapping("/update")
    public PatientEntity updatePatient(@RequestBody PatientEntity patientEntity) throws ItemNotFoundException{
        return patientService.updatePatientDetails(patientEntity);
    }

}

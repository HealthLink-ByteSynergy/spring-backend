package com.example.demo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.PatientEntity;
import com.example.demo.exception.InvalidFormatException;
import com.example.demo.exception.ItemNotFoundException;
import com.example.demo.service.PatientService;

import org.springframework.web.bind.annotation.RequestBody;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/patient")
@RequiredArgsConstructor
public class PatientController {
    
    private final PatientService patientService;

    @GetMapping("/id")
    public PatientEntity getPatient(@RequestBody String patientId) throws ItemNotFoundException{
        return patientService.getByPatientId(patientId);
    }

    @GetMapping("/getByUserId")
    public List<PatientEntity> getByUser(@RequestBody String patientId) throws ItemNotFoundException{
        return patientService.getByUserId(patientId);
    }
    
    @PostMapping("/save")
    public PatientEntity savePatient(@RequestBody PatientEntity patientEntity) throws InvalidFormatException{
        return patientService.savePatientDetails(patientEntity);
    }

    @DeleteMapping("/delete")
    public void DeletePatient(@RequestBody String patientId) throws ItemNotFoundException{
        patientService.deleteByPatientId(patientId);
    }

    @PutMapping("/update")
    public PatientEntity updatePatient(@RequestBody PatientEntity patientEntity) throws ItemNotFoundException{
        return patientService.updatePatientDetails(patientEntity);
    }

}

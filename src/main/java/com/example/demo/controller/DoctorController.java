package com.example.demo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.DoctorEntity;
import com.example.demo.exception.InvalidFormatException;
import com.example.demo.exception.ItemNotFoundException;
import com.example.demo.service.DoctorService;

import org.springframework.web.bind.annotation.RequestBody;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/doctor")
@RequiredArgsConstructor
public class DoctorController {
    
    private final DoctorService doctorService;

    @GetMapping("/doctorid/{doctorId}")
    public DoctorEntity getById(@PathVariable("doctorId") String doctorId) throws ItemNotFoundException{
        return doctorService.getBydoctorId(doctorId);
    }

    @GetMapping("/getdocidbypatientid/{docPatientId}")
    public DoctorEntity getByDocPatientId(@PathVariable("docPatientId") String docPatientId) throws ItemNotFoundException{
        return doctorService.getByPatientId(docPatientId);
    }

    @GetMapping("/userid/{userId}")
    public DoctorEntity getByUserId(@PathVariable("userId") String userId) throws ItemNotFoundException{
        return doctorService.getByUserId(userId);
    }

    @PostMapping("/getBySpecialization")
    public List<DoctorEntity> getBySpecialization(@RequestBody String specialization) throws ItemNotFoundException{
        return doctorService.getDetailsBySpecialization(specialization);
    }

    @PostMapping("/save")
    public DoctorEntity saveDetails(@RequestBody DoctorEntity doctorEntity) throws InvalidFormatException{
        return doctorService.saveDoctorDetails(doctorEntity);
    }

    @PutMapping("/update")
    public DoctorEntity updateDetails(@RequestBody DoctorEntity doctorEntity) throws ItemNotFoundException{
        //doctorId and userId
        return doctorService.updateDoctorAvailability(doctorEntity.getDoctorId(), doctorEntity.getIsAvailable());
    }
}

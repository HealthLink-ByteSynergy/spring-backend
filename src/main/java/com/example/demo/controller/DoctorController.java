package com.example.demo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/id")
    public DoctorEntity getById(@RequestBody String DoctorId) throws ItemNotFoundException{
        return doctorService.getBydoctorId(DoctorId);
    }

    @GetMapping("/getBySpecialization")
    public List<DoctorEntity> getBySpecialization(@RequestBody String specialization) throws ItemNotFoundException{
        return doctorService.getDetailsBySpecialization(specialization);
    }

    @PostMapping("/save")
    public DoctorEntity saveDetails(DoctorEntity doctorEntity) throws InvalidFormatException{
        return doctorService.saveDoctorDetails(doctorEntity);
    }

    @PutMapping("/update")
    public DoctorEntity updateDetails(DoctorEntity doctorEntity) throws ItemNotFoundException{
        return doctorService.updateDoctorAvailability(doctorEntity.getDoctorId(), doctorEntity.getIsAvailable());
    }
}

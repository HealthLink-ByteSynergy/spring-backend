package com.example.demo.service;

import com.example.demo.entity.DoctorEntity;
import com.example.demo.entity.IsAvailable;

import java.util.List;

public interface DoctorService {
    DoctorEntity getBydoctorId(String doctorId);
    DoctorEntity saveDoctorDetails(DoctorEntity doctorEntity);
    DoctorEntity updateDoctorAvailability(String doctorId, IsAvailable isAvailable); //use save route
    List<DoctorEntity> getDetailsBySpecialization(String Specilization);

}

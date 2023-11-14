package com.example.demo.service;

import com.example.demo.entity.DoctorEntity;
import com.example.demo.entity.IsAvailable;
import com.example.demo.exception.InvalidFormatException;
import com.example.demo.exception.ItemNotFoundException;

import java.util.List;

public interface DoctorService {
    DoctorEntity getBydoctorId(String doctorId) throws ItemNotFoundException;
    DoctorEntity saveDoctorDetails(DoctorEntity doctorEntity) throws InvalidFormatException;
    DoctorEntity updateDoctorAvailability(String doctorId, IsAvailable isAvailable) throws ItemNotFoundException; //use save route
    List<DoctorEntity> getDetailsBySpecialization(String Specilization) throws ItemNotFoundException;

}

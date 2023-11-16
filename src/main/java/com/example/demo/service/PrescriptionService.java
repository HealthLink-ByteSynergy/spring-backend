package com.example.demo.service;

import com.example.demo.entity.PrescriptionEntity;
import com.example.demo.exception.InvalidFormatException;
import com.example.demo.exception.ItemNotFoundException;

public interface PrescriptionService {
    PrescriptionEntity getByPrescriptionId(String prescriptionId) throws ItemNotFoundException; 
    
    PrescriptionEntity savePrescription(PrescriptionEntity prescriptionEntity) throws InvalidFormatException;
}

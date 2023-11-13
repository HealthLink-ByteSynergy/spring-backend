package com.example.demo.service;

import com.example.demo.entity.PrescriptionEntity;

public interface PrescriptionService {
    PrescriptionEntity getByPrescriptionId(String prescriptionId); //return a join type of thing
    PrescriptionEntity savePrescription(PrescriptionEntity prescriptionEntity);
}

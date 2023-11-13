package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.MedicineEntity;

public interface MedicineService {
    List<MedicineEntity> getMedicinesList(String medicineId);
    MedicineEntity saveMedicine(MedicineEntity medicineEntity);
}

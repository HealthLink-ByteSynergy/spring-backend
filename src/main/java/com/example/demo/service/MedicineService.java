package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.MedicineEntity;
import com.example.demo.exception.InvalidFormatException;
import com.example.demo.exception.ItemNotFoundException;

public interface MedicineService {
    List<MedicineEntity> getMedicinesList(String medicineId) throws ItemNotFoundException;
    MedicineEntity saveMedicine(MedicineEntity medicineEntity) throws InvalidFormatException;
}

package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.MedicineEntity;
import com.example.demo.exception.InvalidFormatException;
import com.example.demo.exception.ItemNotFoundException;
import com.example.demo.repository.MedicineRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MedicineServiceImpl implements MedicineService{

    private final MedicineRepository medicineRepository;
    
    @Override
    public List<MedicineEntity> getMedicinesList(String medicineId) throws ItemNotFoundException {
        try{
            return medicineRepository.findAllByMedicineId(medicineId);
        }
        catch(Exception ex){
            throw new ItemNotFoundException("No medicines with given medicineId found");
        }
    }

    @Override
    public MedicineEntity saveMedicine(MedicineEntity medicineEntity) throws InvalidFormatException {
        try{
            medicineEntity.setId(UUIDService.getUUID());
            return medicineRepository.save(medicineEntity);
        }
        catch(Exception ex){
            throw new InvalidFormatException("Invalid format for saving medicines data");
        }
    }
    
}

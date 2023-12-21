package com.example.demo.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.entity.PrescriptionEntity;
import com.example.demo.exception.InvalidFormatException;
import com.example.demo.exception.ItemNotFoundException;
import com.example.demo.repository.PrescriptionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PrescriptionServiceImpl implements PrescriptionService{
    
    private final PrescriptionRepository prescriptionRepository;
    
    @Override
    public PrescriptionEntity getByPrescriptionId(String prescriptionId) throws ItemNotFoundException {
        try{
            Optional<PrescriptionEntity> patient=prescriptionRepository.findByPrescriptionId(prescriptionId);
            if(patient.isPresent()) return patient.get();
        }
        catch(Exception ex){
            throw new ItemNotFoundException("This patient doesn't exist");
        }
        return null;
    }

    @Override
    public PrescriptionEntity savePrescription(PrescriptionEntity prescriptionEntity) throws InvalidFormatException {
         try{
            prescriptionEntity.setMedicineId(UUIDService.getUUID());
            prescriptionEntity.setPrescriptionId(UUIDService.getUUID());
            return prescriptionRepository.save(prescriptionEntity);
        }
        catch(Exception ex){
            throw new InvalidFormatException("Incorrect Parameters passed");
        }
    }
    
}

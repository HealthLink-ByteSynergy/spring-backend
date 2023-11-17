package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.entity.DoctorEntity;
import com.example.demo.entity.IsAvailable;
import com.example.demo.exception.InvalidFormatException;
import com.example.demo.exception.ItemNotFoundException;
import com.example.demo.repository.DoctorRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;

    @Override
    public DoctorEntity getBydoctorId(String doctorId) throws ItemNotFoundException {
        
        Optional<DoctorEntity> doctorEntity=doctorRepository.findById(doctorId);
        if(doctorEntity.isPresent()){
            return doctorEntity.get();
        }
        else {
            throw new ItemNotFoundException("There is no doctor with this doctorId");
        }

    }

    @Override
    public DoctorEntity saveDoctorDetails(DoctorEntity doctorEntity) throws InvalidFormatException {
        try{
            doctorEntity.setIsAvailable(IsAvailable.AVAILABLE);
            doctorEntity.setDoctorId(UUIDService.getUUID());
            return doctorRepository.save(doctorEntity);
        }
        catch(Exception ex){
            throw new InvalidFormatException(ex.getMessage());
        }
    }

    @Override
    public DoctorEntity updateDoctorAvailability(String doctorId, IsAvailable isAvailable) throws ItemNotFoundException {
        try{
            Optional<DoctorEntity> item=doctorRepository.findById(doctorId);
            if(item.isPresent()){
                DoctorEntity doctorEntity=item.get();
                doctorEntity.setIsAvailable(isAvailable);
                return doctorRepository.save(doctorEntity);
            }
            else throw new ItemNotFoundException("The doctor with the corresponding doctorId doesn't exist!!");
        }
        catch(Exception ex){
            throw new ItemNotFoundException("The doctor with the corresponding doctorId doesn't exist!!");
        }
    }

    @Override
    public List<DoctorEntity> getDetailsBySpecialization(String Specilization) throws ItemNotFoundException {
        try{
            return doctorRepository.findBySpecializationContainsAndIsAvailable(Specilization,IsAvailable.AVAILABLE);
        }
        catch(Exception ex){
            throw new ItemNotFoundException("No doctors with the given specialization are available");
        }
    }
    
}

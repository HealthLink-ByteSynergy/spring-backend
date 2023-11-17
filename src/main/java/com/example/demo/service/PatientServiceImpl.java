package com.example.demo.service;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.entity.PatientEntity;
import com.example.demo.entity.UserEntity;
import com.example.demo.exception.InvalidFormatException;
import com.example.demo.exception.ItemNotFoundException;
import com.example.demo.repository.PatientRepository;
import com.example.demo.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService{
    
    private final PatientRepository patientRepository;
    private final UserRepository userRepository;

    @Override
    public PatientEntity getByPatientId(String patientId) throws ItemNotFoundException {
        try{
            Optional<PatientEntity> patient=patientRepository.findById(patientId);
            if(patient.isPresent()) return patient.get();
        }
        catch(Exception ex){
            throw new ItemNotFoundException("This patient doesn't exist");
        }
        return null;
    }

    @Override
    public List<PatientEntity> getByUserId(String userId) throws ItemNotFoundException {
        
        try{
            UserEntity userEntity=new UserEntity();
            userEntity.setId(userId);
            return patientRepository.findByUserEntity(userEntity);
        }

        catch(Exception ex){
            throw new ItemNotFoundException("This user doesn't exist");
        }
    }

    @Override
    public PatientEntity savePatientDetails(PatientEntity patientEntity) throws InvalidFormatException {
        try{
            patientEntity.setPatientId(UUIDService.getUUID());
            UserEntity userEntity=userRepository.findById(patientEntity.getUserEntity().getId()).get();
            patientEntity.setUserEntity(userEntity);
            return patientRepository.save(patientEntity);
        }
        catch(Exception ex){
            throw new InvalidFormatException("Incorrect Parameters passed");
        }
    }

    @Override
    public void deleteByPatientId(String patientId) throws ItemNotFoundException {
        try{
            patientRepository.deleteById(patientId);
        }
        catch(Exception ex){
            throw new ItemNotFoundException(ex.getMessage());
        }
        
    }

    @Override
    public PatientEntity updatePatientDetails(PatientEntity patientEntity) throws ItemNotFoundException {
        try{//need to add more
            //pass both patient and userId;
            Optional<PatientEntity> item=patientRepository.findById(patientEntity.getPatientId());
            if(item.isPresent()){
                PatientEntity currentpatientEntity=item.get();
                patientEntity.setPatientId(currentpatientEntity.getPatientId());
                return patientRepository.save(patientEntity);
            }
            else throw new ItemNotFoundException("The patient with the corresponding patientId doesn't exist!!");
        }
        catch(Exception ex){
            throw new ItemNotFoundException("The patient with the corresponding patientId doesn't exist!!");
        }
    }

}

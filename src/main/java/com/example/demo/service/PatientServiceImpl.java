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

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService{
    
    private final PatientRepository patientRepository;

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
            Optional<PatientEntity> item=patientRepository.findById(patientEntity.getPatientId());
            if(item.isPresent()){
                PatientEntity currentpatientEntity=item.get();
                if(patientEntity.getAge()!=null) currentpatientEntity.setAge(patientEntity.getAge());
                if(patientEntity.getHeight()!=null) currentpatientEntity.setHeight(patientEntity.getHeight());
                if(patientEntity.getWeight()!=null) currentpatientEntity.setWeight(patientEntity.getWeight());
                if(patientEntity.getGender()!=null) currentpatientEntity.setGender(patientEntity.getGender());
                if(patientEntity.getMedicalCondition()!=null) currentpatientEntity.setMedicalCondition(patientEntity.getMedicalCondition());

                return patientRepository.save(currentpatientEntity);
            }
            else throw new ItemNotFoundException("The patient with the corresponding patientId doesn't exist!!");
        }
        catch(Exception ex){
            throw new ItemNotFoundException("The doctor with the corresponding doctorId doesn't exist!!");
        }
    }

}

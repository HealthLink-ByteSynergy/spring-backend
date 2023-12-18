package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.entity.PatientEntity;
import com.example.demo.entity.TemporaryChatEntity;
import com.example.demo.exception.InvalidFormatException;
import com.example.demo.exception.ItemNotFoundException;
import com.example.demo.repository.TemporaryChatRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TemporaryChatService {
    private final TemporaryChatRepository temporaryChatRepository;

    public TemporaryChatEntity getChatById(String tempId) throws ItemNotFoundException{
        Optional<TemporaryChatEntity> tempEntity=temporaryChatRepository.findById(tempId);
        if(tempEntity.isPresent()){
            return tempEntity.get();
        }
        else {
            throw new ItemNotFoundException("Invalid Id's");
        }
    }

    public TemporaryChatEntity saveTempDetails(TemporaryChatEntity tempEntity) throws InvalidFormatException {
        try{
            tempEntity.setTempId(UUIDService.getUUID());
            return temporaryChatRepository.save(tempEntity);
        }
        catch(Exception ex){
            throw new InvalidFormatException(ex.getMessage());
        }
    }

    public List<TemporaryChatEntity> getByPatientIds(TemporaryChatEntity temporaryChatEntity) throws ItemNotFoundException{
        try{
        List<PatientEntity> ids=new ArrayList<>();
        ids.add(temporaryChatEntity.getPatientEntity());
        ids.add(temporaryChatEntity.getDocPatientEntity());
           return temporaryChatRepository.findAllByPatientEntityInAndDocPatientEntityIn(ids, ids);
        }
        catch(Exception ex){
            throw new ItemNotFoundException(ex.getMessage());
        }
    }

    public List<TemporaryChatEntity> getByPatientId(TemporaryChatEntity temporaryChatEntity) throws ItemNotFoundException{
        try{
        
           return temporaryChatRepository.findAllByPatientEntity(temporaryChatEntity.getPatientEntity());
        }
        catch(Exception ex){
            throw new ItemNotFoundException(ex.getMessage());
        }
    }

    public List<TemporaryChatEntity> getByDocPatientId(TemporaryChatEntity temporaryChatEntity) throws ItemNotFoundException{
        try{
        
           return temporaryChatRepository.findAllByDocPatientEntity(temporaryChatEntity.getDocPatientEntity());
        }
        catch(Exception ex){
            throw new ItemNotFoundException(ex.getMessage());
        }
    }

    public void deleteByPatientIds(TemporaryChatEntity temporaryChatEntity) throws ItemNotFoundException{
        try{
        List<PatientEntity> ids=new ArrayList<>();
        ids.add(temporaryChatEntity.getPatientEntity());
        ids.add(temporaryChatEntity.getDocPatientEntity());
           temporaryChatRepository.deleteByPatientEntityInAndDocPatientEntityIn(ids, ids);
        }
        catch(Exception ex){
            throw new ItemNotFoundException(ex.getMessage());
        }
    }

}

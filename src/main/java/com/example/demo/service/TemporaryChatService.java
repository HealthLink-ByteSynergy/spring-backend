package com.example.demo.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.entity.DoctorEntity;
import com.example.demo.entity.IsAvailable;
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
            return temporaryChatRepository.save(tempEntity);
        }
        catch(Exception ex){
            throw new InvalidFormatException(ex.getMessage());
        }
    }
}

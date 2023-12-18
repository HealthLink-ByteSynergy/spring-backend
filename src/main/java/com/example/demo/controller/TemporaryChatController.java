package com.example.demo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.PatientEntity;
import com.example.demo.entity.TemporaryChatEntity;
import com.example.demo.exception.InvalidFormatException;
import com.example.demo.exception.ItemNotFoundException;
import com.example.demo.service.TemporaryChatService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/tempchat")
@RequiredArgsConstructor
public class TemporaryChatController {
    
    private final TemporaryChatService temporaryChatService;

    @GetMapping("/tempid/{tempId}")
    public TemporaryChatEntity getTempChatById(@PathVariable("tempId") String tempId) throws ItemNotFoundException{
        return temporaryChatService.getChatById(tempId);
    }

    @GetMapping("/patientid/{patientId}")
    public List<TemporaryChatEntity> getTempChatByPatientId(@PathVariable("patientId") String patientId) throws ItemNotFoundException{
        TemporaryChatEntity temporaryChatEntity = new TemporaryChatEntity();
        PatientEntity patientEntity= new PatientEntity();
        patientEntity.setPatientId(patientId);
        temporaryChatEntity.setPatientEntity(patientEntity);
        return temporaryChatService.getByPatientId(temporaryChatEntity);
    }
    
    @GetMapping("/docpatientid/{docpatientId}")
    public List<TemporaryChatEntity> getTempChatByDocPatientId(@PathVariable("docpatientId") String docpatientId) throws ItemNotFoundException{
        TemporaryChatEntity temporaryChatEntity = new TemporaryChatEntity();
        PatientEntity patientEntity= new PatientEntity();
        patientEntity.setPatientId(docpatientId);
        temporaryChatEntity.setDocPatientEntity(patientEntity);
        return temporaryChatService.getByDocPatientId(temporaryChatEntity);
    }

    @PostMapping("/addTempChat")
    public TemporaryChatEntity addTempChat(@RequestBody TemporaryChatEntity temporaryChatEntity) throws ItemNotFoundException, InvalidFormatException{
        return temporaryChatService.saveTempDetails(temporaryChatEntity);
    }

    @PostMapping("/getTempChat")
    public List<TemporaryChatEntity> getTempChats(@RequestBody TemporaryChatEntity temporaryChatEntity) throws ItemNotFoundException{
        return temporaryChatService.getByPatientIds(temporaryChatEntity);
    }

    @Transactional
    @DeleteMapping("/deleteTempChat")
    public void deleteByPatientAndDoctorPatient(@RequestBody TemporaryChatEntity temporaryChatEntity) throws ItemNotFoundException{
        temporaryChatService.deleteByPatientIds(temporaryChatEntity);
    }


}

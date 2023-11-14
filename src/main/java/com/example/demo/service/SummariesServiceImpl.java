package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.SummariesEntity;
import com.example.demo.repository.SummariesRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SummariesServiceImpl implements SummariesService{

    private final SummariesRepository summariesRepository;
    
    @Override
    public String generateTempChatSummary(String message) {
        throw new UnsupportedOperationException("Unimplemented method 'generateTempChatSummary'");
    }

    @Override
    public SummariesEntity saveSummary(SummariesEntity summariesEntity) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'saveSummary'");
    }

    @Override
    public SummariesEntity getSummaryById(String summaryId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getSummaryById'");
    }

    @Override
    public List<SummariesEntity> findAllByPatiendId(String patientId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAllByPatiendId'");
    }

    @Override
    public List<SummariesEntity> findAllByDoctorId(String doctorId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAllByDoctorId'");
    }
    
}

package com.example.demo.service;

import com.example.demo.entity.SummariesEntity;
import com.example.demo.exception.InvalidFormatException;
import com.example.demo.exception.ItemNotFoundException;

import java.util.List;

public interface SummariesService {
    String generateTempChatSummary(String message) throws InvalidFormatException;
    SummariesEntity saveSummary(SummariesEntity summariesEntity);
    SummariesEntity getSummaryById(String summaryId) throws ItemNotFoundException;
    List<SummariesEntity> findAllByPatiendId(String patientId) throws ItemNotFoundException;
    List<SummariesEntity> findAllByDoctorId(String doctorId) throws ItemNotFoundException;
}

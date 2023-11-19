package com.example.demo.service;

import com.example.demo.entity.SummariesEntity;
import com.example.demo.exception.InvalidFormatException;
import com.example.demo.exception.ItemNotFoundException;

import java.util.List;

public interface SummariesService {
    String generateTempChatSummary(String message, String length,String format) throws InvalidFormatException;

    SummariesEntity saveSummary(SummariesEntity summariesEntity) throws InvalidFormatException;

    SummariesEntity getSummaryById(String summaryId) throws ItemNotFoundException;

    List<SummariesEntity> findAllByPatientId(String patientId) throws ItemNotFoundException;

    List<SummariesEntity> findAllByDoctorId(String doctorId) throws ItemNotFoundException;
}

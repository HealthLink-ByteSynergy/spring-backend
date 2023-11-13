package com.example.demo.service;

import com.example.demo.entity.SummariesEntity;
import java.util.List;

public interface SummariesService {
    String generateTempChatSummary(String message);
    SummariesEntity saveSummary(SummariesEntity summariesEntity);
    SummariesEntity getSummaryById(String summaryId);
    List<SummariesEntity> findAllByPatiendId(String patientId);
    List<SummariesEntity> findAllByDoctorId(String doctorId);
}

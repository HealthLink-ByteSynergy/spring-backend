package com.example.demo.entity;

import java.util.Date;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "summaries")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SummariesEntity {
    
    @Id
    private String summaryId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="doctor_id")
    private DoctorEntity doctorEntity;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="patient_id")
    private PatientEntity patientEntity;

    @Column(length = 2000)
    private String text;

    private Date date;


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="prescription_id")
    private PrescriptionEntity prescriptionEntity;
}

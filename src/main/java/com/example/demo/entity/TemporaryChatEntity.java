package com.example.demo.entity;


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
@Table(name = "temporary_chat")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TemporaryChatEntity {

    @Id
    private String tempId;

    @ManyToOne()
    @JoinColumn(name="patient_id")
    private PatientEntity patientEntity;

    @ManyToOne()
    @JoinColumn(name="doctorPatient_id")
    private PatientEntity docPatientEntity;
}

package com.example.demo.entity;

import java.util.Date;


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
@Table(name = "meeting")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MeetingEntity {
    @Id
    private String meetingId;

    private String meetingLink;
    
    @ManyToOne
    @JoinColumn(name="patient_id")
    private PatientEntity recPatientEntity;

    @ManyToOne
    @JoinColumn(name="doctor_id")
    private DoctorEntity doctorEntity;

    private Date date;

}

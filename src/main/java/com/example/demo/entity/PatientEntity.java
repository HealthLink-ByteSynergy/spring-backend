package com.example.demo.entity;

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
@Table(name = "patient")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PatientEntity {
    
    @Id
    private String patientId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="user_id")
    private UserEntity userEntity;

    private String age;

    private String gender;

    private String height;

    private String weight;

    private String medicalCondition;

    @Column(length = 600)
    private String medication;

    @Column(length = 600)
    private String surgeries;

    private String smokingFrequency;

    private String drinkingFrequency;

    private String drugsUseFrequency;
    
}

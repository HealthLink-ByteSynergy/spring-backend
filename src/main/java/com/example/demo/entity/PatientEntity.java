package com.example.demo.entity;

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

    @ManyToOne()
    @JoinColumn(name="user_id")
    private UserEntity userEntity;

    private String age;

    private String name;

    private String gender;

    private String phoneNumber;

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
    
    @Override
    public String toString(){
        String output="The Patient Details are: \nAge: "+this.getAge()
        + "\nGender: " + this.getGender()
        + "\nHeight: " + this.getHeight()
        + "\nWeight: " + this.getWeight()
        + "\nMedical Condition: "+ this.getMedicalCondition()
        + "\nSurgeries: " + this.getSurgeries()
        + "\nSmoking Frequency: " + this.getSmokingFrequency()
        + "\nDrinking Frequency: "+ this.getDrinkingFrequency()
        + "\nDrug Use: " + this.getDrugsUseFrequency();
        
        return output;
    }
}

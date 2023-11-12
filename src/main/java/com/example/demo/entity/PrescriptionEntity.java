package com.example.demo.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "prescription")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PrescriptionEntity {
    
    @Id
    private String prescriptionId;

    private String generalHabits;

    private String medicineId;

}

package com.example.demo.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "medicine")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MedicineEntity {
    
    @Id
    private String medicineId;

    private String name;

    private String dosage;

    private String frequency;
    
}

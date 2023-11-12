package com.example.demo.entity;

import org.hibernate.mapping.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
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

    // @ManyToMany
    // @JoinColumn(name="medicine_ids")
    // private List<MedicineEntity> MedicineEntity;
}

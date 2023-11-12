package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.MedicineEntity;

public interface MedicineRepository extends JpaRepository<MedicineEntity,String>{
    List<MedicineEntity> findAllByMedicineId(String medicineId);
}
